package it.polimi.swim.business.bean;

import it.polimi.swim.business.entity.Administrator;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.User;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.UserNotFoundException;
import it.polimi.swim.business.exceptions.UsernameAlreadyTakenException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class AuthenticationControl
 */
@Stateless
public class AuthenticationControl implements AuthenticationControlRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public AuthenticationControl() {
		// TODO Auto-generated constructor stub
	}

	public UserType authenticateUser(String username, String passwordHash)
			throws UserNotFoundException, AuthenticationFailedException {
		String queryString = "FROM user u WHERE username=:username";
		Query query = manager.createQuery(queryString);
		query.setParameter("username", username);
		
		User user;
		try{
			user = (User) query.getSingleResult();
		}catch(EntityNotFoundException enf){
			throw new UserNotFoundException();
		}catch(NoResultException nre){
			throw new UserNotFoundException();
		}
		
		String correctPasswordHash = (String) user.getPasswordHash();
		if(!passwordHash.equals(correctPasswordHash)){
			throw new AuthenticationFailedException();
		}
		
		if(user instanceof Administrator){
			return UserType.ADMINISTRATOR;
		} else if(user instanceof Customer){
			return UserType.CUSTOMER;
		} else {
			throw new ClassCastException(); //TODO: solve this in a better way
		}
	}

	public void createUser(String username, String password, String email,
			String name, String surname) throws UsernameAlreadyTakenException,
			EmailAlreadyTakenException {
		
		Customer c = new Customer(username, password, email, name, surname);
		
	}

}
