package it.polimi.swim.business.bean;

import java.math.BigInteger;
import java.security.SecureRandom;

import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
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
 * Session Bean implementation class AuthenticationController.
 */
@Stateless
public class AuthenticationController implements AuthenticationControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	SecureRandom random = new SecureRandom();

	/**
	 * Default constructor.
	 */
	public AuthenticationController() {

	}

	/**
	 * @see AuthenticationControllerRemote
	 */
	public UserType authenticateUser(String username, String password)
			throws AuthenticationFailedException {
		String queryString = "FROM User u WHERE username=:username";
		Query query = manager.createQuery(queryString);
		query.setParameter("username", username);

		User user;
		try {
			user = (User) query.getSingleResult();
		} catch (EntityNotFoundException enf) {
			throw new AuthenticationFailedException();
		} catch (NoResultException nre) {
			throw new AuthenticationFailedException();
		}

		if (!user.matchPassword(password)) {
			throw new AuthenticationFailedException();
		}

		if (user instanceof Administrator) {
			return UserType.ADMINISTRATOR;
		} else if (user instanceof Customer) {
			return UserType.CUSTOMER;
		} else {
			throw new ClassCastException(); // TODO: Solve this exception in a
											// better way
		}
	}

	/**
	 * @see AuthenticationControllerRemote
	 */
	public void createUser(String username, String password, String email,
			String name, String surname) throws EmailAlreadyTakenException,
			UsernameAlreadyTakenException {

		if (!isUsernameAvailable(username)) {
			throw new UsernameAlreadyTakenException();
		}

		if (!isEmailAvailable(email)) {
			throw new EmailAlreadyTakenException();
		}

		Customer c = new Customer(username, password, email, name, surname);
		manager.persist(c);
	}

	/**
	 * @see AuthenticationControllerRemote
	 */
	public void confirmUserEmailAddress(String username)
			throws UserNotFoundException {
		findCustomerByUsername(username).setEmailConfirmed();
	}

	/**
	 * @see AuthenticationControllerRemote
	 */
	public String resetUserPassword(String username)
			throws UserNotFoundException {
		Customer c = findCustomerByUsername(username);
		String newPassword = generateRandomString(10); // TODO: move 10 to
														// external config class
		c.setPassword(newPassword);
		return newPassword;
	}

	/**
	 * @see AuthenticationControllerRemote
	 */
	public void createAdministrator(String username, String password)
			throws UsernameAlreadyTakenException {
		if (!isUsernameAvailable(username)) {
			throw new UsernameAlreadyTakenException();
		}

		Administrator a = new Administrator(username, password);
		manager.persist(a);
	}

	/* Helpers */

	private boolean isEmailAvailable(String email) {
		Query q = manager.createQuery("FROM Customer c WHERE c.email=:email");
		q.setParameter("email", email);

		return q.getResultList().size() == 0;
	}

	private boolean isUsernameAvailable(String username) {
		Query q = manager
				.createQuery("FROM Customer c WHERE c.username=:username");
		q.setParameter("username", username);

		return q.getResultList().size() == 0;
	}

	private Customer findCustomerByUsername(String username)
			throws UserNotFoundException {
		try {
			return manager.find(Customer.class, username);
		} catch (NoResultException nre) {
			throw new UserNotFoundException();
		}
	}

	private String generateRandomString(int length) {
		return new BigInteger(length * 5, random).toString(32);
	}

}