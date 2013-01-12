package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Ability;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.helpers.SerializableImage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class UserProfileController.
 */
@Stateless
public class UserProfileController implements UserProfileControllerRemote {

	private final int IMAGE_SIZE = 100;
	private final int THUMB_SIZE = 30;

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public UserProfileController() {
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
				.createQuery("FROM Feedback f WHERE f.linkedRequest.sender.username=:username");

		q.setParameter("username", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getReceivedFeedbacks(String username) {
		Query q = manager
				.createQuery("FROM Feedback f WHERE f.linkedRequest.receiver.username=:username");

		q.setParameter("username", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public void updateCustomerDetails(String username,
			Map<String, Object> values) {

		Map<String, Method> setters = new HashMap<String, Method>();
		Class<Customer> custClass = Customer.class;

		Customer customer = manager.find(Customer.class, username);

		Class<?>[] stringParam = new Class<?>[] { String.class };
		Class<?>[] dateParam = new Class<?>[] { Date.class };

		/* Initialize setter list */
		try {
			setters.put("name", custClass.getMethod("setName", stringParam));
			setters.put("surname",
					custClass.getMethod("setSurname", stringParam));
			setters.put("birthdate",
					custClass.getMethod("setBirthDate", dateParam));
			setters.put("location",
					custClass.getMethod("setLocation", stringParam));
		} catch (Exception e) {
		}

		for (String field : setters.keySet()) {
			if (values.containsKey(field)) {
				Object value = values.get(field);
				Object[] params = new Object[] { value };
				try {
					setters.get(field).invoke(customer, params);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public void changePassword(String username, String password) {
		Customer c = manager.find(Customer.class, username);
		c.setPassword(password);
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public void changeEmail(String username, String email)
			throws EmailAlreadyTakenException {
		Customer c = manager.find(Customer.class, username);

		if (!isEmailAvailable(email)) {
			throw new EmailAlreadyTakenException();
		}

		c.setEmail(email);
	}

	/**
	 * @see UserProfileControllerRemote
	 */
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

	/**
	 * @see UserProfileControllerRemote
	 */
	public Boolean areFriends(String username1, String username2) {
		Query q = manager
				.createQuery("FROM Friendship f WHERE "
						+ "(f.sender.username=:firstUser AND f.receiver.username=:secondUser OR "
						+ "f.sender.username=:secondUser AND f.receiver.username=:firstUser) AND "
						+ "f.confirmed=true");

		q.setParameter("firstUser", username1);
		q.setParameter("secondUser", username2);

		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getAbilityList(String username) {
		Query q = manager
				.createQuery("SELECT a FROM Customer c, IN (c.declaredAbilities) a WHERE c.username=:username");
		q.setParameter("username", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public void addAbility(String username, String abilityName)
			throws BadRequestException, InvalidStateException {
		Customer c = Helpers
				.getEntityChecked(manager, Customer.class, username);
		Ability a = Helpers.getEntityChecked(manager, Ability.class,
				abilityName);

		if (c.getAbilityList().contains(a)) {
			throw new InvalidStateException();
		}

		c.addAbility(a);
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public void removeAbility(String username, String abilityName)
			throws BadRequestException, InvalidStateException {
		Customer c = Helpers
				.getEntityChecked(manager, Customer.class, username);
		Ability a = Helpers.getEntityChecked(manager, Ability.class,
				abilityName);

		if (!c.getAbilityList().contains(a)) {
			throw new InvalidStateException();
		}

		c.removeAbility(a);
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getReceivedActiveWorkRequest(String username) {
		Query q = manager
				.createQuery("FROM WorkRequest w WHERE w.receiver.username=:u "
						+ "AND NOT (w.senderCompleted=true AND w.receiverCompleted=true) "
						+ "AND (w.senderConfirmed IS NULL OR w.senderConfirmed=true) "
						+ "AND (w.receiverConfirmed IS NULL OR w.receiverConfirmed=true)");

		q.setParameter("u", username);
		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getSentActiveWorkRequest(String username) {
		Query q = manager
				.createQuery("FROM WorkRequest w WHERE w.sender.username=:u "
						+ "AND NOT (w.senderCompleted=true AND w.receiverCompleted=true) "
						+ "AND (w.senderConfirmed IS NULL OR w.senderConfirmed=true) "
						+ "AND (w.receiverConfirmed IS NULL OR w.receiverConfirmed=true)");
		q.setParameter("u", username);

		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getReceivedArchivedWorkRequest(String username) {
		Query q = manager
				.createQuery("FROM WorkRequest w WHERE w.receiver.username=:u AND "
						+ "(w.senderCompleted=true AND w.receiverCompleted=true OR "
						+ "w.senderConfirmed=false OR w.receiverConfirmed=false)");
		q.setParameter("u", username);

		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getSentArchivedWorkRequest(String username) {
		Query q = manager
				.createQuery("FROM WorkRequest w WHERE w.sender.username=:u "
						+ "AND (w.senderCompleted=true AND w.receiverCompleted=true OR "
						+ "w.senderConfirmed=false OR w.receiverConfirmed=false)");
		q.setParameter("u", username);

		return q.getResultList();
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public List<?> getSentAbilityRequest(String username) {
		Query q = manager
				.createQuery("From AbilityRequest r WHERE r.requestAuthor.username=:username");
		q.setParameter("username", username);
		return q.getResultList();
	}

	/* Helpers */

	private boolean isEmailAvailable(String email) {
		Query q = manager.createQuery("FROM Customer c WHERE c.email=:email");
		q.setParameter("email", email);

		return q.getResultList().size() == 0;
	}

	/**
	 * @see UserProfileControllerRemote
	 */
	public void changeCustomerPhoto(String username, SerializableImage img)
			throws BadRequestException {

		Image fullPhoto = img.getImage();

		/* Check image size */
		if (fullPhoto.getWidth(null) != IMAGE_SIZE
				|| fullPhoto.getHeight(null) != IMAGE_SIZE) {
			throw new BadRequestException();
		}

		Image thumbPhoto = fullPhoto.getScaledInstance(THUMB_SIZE, THUMB_SIZE,
				BufferedImage.SCALE_DEFAULT);

		Customer target = Helpers.getEntityChecked(manager, Customer.class,
				username);

		ByteArrayOutputStream full = new ByteArrayOutputStream();
		ByteArrayOutputStream thumb = new ByteArrayOutputStream();

		try {
			ImageIO.write(Helpers.toBufferedImage(fullPhoto), "png", full);
			ImageIO.write(Helpers.toBufferedImage(thumbPhoto), "png", thumb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		target.setCustomerPhoto(full.toByteArray());
		target.setCustomerThumbnail(thumb.toByteArray());
	}
}