package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
import it.polimi.swim.business.entity.Administrator;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.EmailValidationRequest;
import it.polimi.swim.business.entity.PasswordResetRequest;
import it.polimi.swim.business.entity.User;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.InvalidStateException;
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

	private static final int NEW_PASSWORD_LENGTH = 8;
	@PersistenceContext(unitName = "swim")
	EntityManager manager;

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
		} else {
			return UserType.CUSTOMER;
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
	public void createAdministrator(String username, String password)
			throws UsernameAlreadyTakenException {
		if (!isUsernameAvailable(username)) {
			throw new UsernameAlreadyTakenException();
		}

		Administrator a = new Administrator(username, password);
		manager.persist(a);
	}

	public String createEmailValidationRequest(String authorUsr)
			throws BadRequestException, InvalidStateException {
		Customer author = Helpers.getEntityChecked(manager, Customer.class,
				authorUsr);

		if (author.isEmailConfirmed() || !canStartValidation(author)) {
			throw new InvalidStateException();
		}

		EmailValidationRequest req = new EmailValidationRequest(author);
		manager.persist(req);

		return req.getKey();
	}

	public void validateCustomerEmail(String emailValidationKey)
			throws BadRequestException, InvalidStateException {
		Query q = manager
				.createQuery("From EmailValidationRequest r WHERE r.reqKey=:key");
		q.setParameter("key", emailValidationKey);

		EmailValidationRequest req;

		try {
			req = (EmailValidationRequest) q.getSingleResult();
		} catch (NoResultException e) {
			throw new BadRequestException();
		}

		Customer c = req.getAuthor();

		if (c.isEmailConfirmed()) {
			throw new InvalidStateException();
		}

		c.setEmailConfirmed();
		manager.remove(req);

		return;
	}

	public String createPasswordResetRequest(String authorUsr)
			throws BadRequestException {
		Customer author = Helpers.getEntityChecked(manager, Customer.class,
				authorUsr);

		PasswordResetRequest req = new PasswordResetRequest(author);
		manager.persist(req);
		
		return req.getId();
	}

	public String resetCustomerPassword(String reqId)
			throws BadRequestException {
		PasswordResetRequest req = Helpers.getEntityChecked(manager,
				PasswordResetRequest.class, reqId);

		Customer c = req.getAuthor();
		String password = Helpers.generateRandomString(NEW_PASSWORD_LENGTH);

		c.setPassword(password);
		manager.remove(req);
		return password;
	}

	public String getPasswordResetEmail(String reqId)
			throws BadRequestException {
		PasswordResetRequest req = Helpers.getEntityChecked(manager,
				PasswordResetRequest.class, reqId);

		return req.getAuthor().getEmail();
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

	private boolean canStartValidation(Customer a) {
		Query q = manager
				.createQuery("FROM EmailValidationRequest r WHERE r.author=:author");
		q.setParameter("author", a);

		return q.getResultList().size() == 0;
	}
}