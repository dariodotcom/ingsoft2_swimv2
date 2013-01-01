package it.polimi.swim.business.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class UserProfileController.
 */
@Stateless
public class UserProfileController implements UserProfileControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public UserProfileController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public Customer getByUsername(String username) {
		return manager.find(Customer.class, username);
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getConfirmedFriendshipList(String username) {
		Query q = manager
				.createQuery("FROM Friendship f where (f.receiver.username=:username"
						+ " OR f.sender.username=:username) AND f.confirmed=true");
		q.setParameter("username", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getFriendshipRequest(String username) {
		Query q = manager
				.createQuery("FROM Friendship f WHERE f.receiver.username=:username AND f.confirmed=false");
		q.setParameter("username", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getSentFeedbacks(String username) {
		Query q = manager
				.createQuery("SELECT f FROM Feedback f JOIN (f.linkedRequest) r WHERE r.sender.username=:username");

		q.setParameter("username", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getReceivedFeedacks(String username) {
		Query q = manager
				.createQuery("SELECT f FROM Feedback f JOIN (f.linkedRequest) r WHERE r.receiver.username=:username");

		q.setParameter("username", username);
		return q.getResultList();
	}

	public void updateCustomerDetails(String username,
			Map<String, Object> values) {

		Map<String, Method> setters = new HashMap<String, Method>();
		Class<Customer> custClass = Customer.class;

		Customer c = manager.find(Customer.class, username);

		Class<?>[] paramList = new Class<?>[] { String.class };

		try {
			setters.put("name", custClass.getMethod("setName", paramList));
			setters.put("surname", custClass.getMethod("setSurname", paramList));
			setters.put("birthdate", custClass.getMethod("setBirthDate",
					new Class<?>[] { Date.class }));
			setters.put("location",
					custClass.getMethod("setLocation", paramList));

			for (String field : setters.keySet()) {
				if (values.containsKey(field)) {
					Object value = values.get(field);
					setters.get(field).invoke(c, new Object[] { value });
				}
			}

			// List of exceptions related to reflection. They shouldn't happen
			// since all methods name are hardcoded.
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void changePassword(String username, String password) {
		Customer c = manager.find(Customer.class, username);
		c.setPassword(password);
	}

	public void changeEmail(String username, String email)
			throws EmailAlreadyTakenException {
		Customer c = manager.find(Customer.class, username);

		if (!isEmailAvailable(email)) {
			throw new EmailAlreadyTakenException();
		}

		c.setEmail(email);
	}

	public Boolean canSendFriendshipRequest(String u1, String u2) {
		if (u1.equals(u2)) {
			return false;
		}

		Query q = manager
				.createQuery("FROM Friendship f WHERE "
						+ "f.sender.username=:firstUser AND f.receiver.username=:secondUser OR "
						+ "f.sender.username=:secondUser AND f.receiver.username=:firstUser");
		q.setParameter("firstUser", u1);
		q.setParameter("secondUser", u2);

		return q.getResultList().size() == 0;
	}

	public Boolean areFriends(String username1, String username2) {
		Query q = manager
				.createQuery("FROM Friendship f WHERE "
						+ "(f.sender.username=:firstUser AND f.receiver.username=:secondUser OR "
						+ "f.sender.username=:secondUser AND f.receiver.username=:firstUser) AND "
						+ "f.confirmed=true");
		
		q.setParameter("firstUser", username1);
		q.setParameter("secondUser", username2);
		
		try{
			q.getSingleResult();
			return true;
		}catch(NoResultException e){
			return false;
		}
	}

	/* Helpers */
	private boolean isEmailAvailable(String email) {
		Query q = manager.createQuery("FROM Customer c WHERE c.email=:email");
		q.setParameter("email", email);

		return q.getResultList().size() == 0;
	}

}