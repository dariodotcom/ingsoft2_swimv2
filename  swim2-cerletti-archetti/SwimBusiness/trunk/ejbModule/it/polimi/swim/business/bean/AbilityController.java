package it.polimi.swim.business.bean;

import java.util.List;

import it.polimi.swim.business.bean.remote.AbilityControllerRemote;
import it.polimi.swim.business.entity.Ability;
import it.polimi.swim.business.entity.AbilityRequest;
import it.polimi.swim.business.entity.Administrator;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class AbilityController.
 */
@Stateless
public class AbilityController implements AbilityControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Class constructor.
	 */
	public AbilityController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see AbilityControllerRemote
	 */
	public void addAbilityRequest(String authorUsr, String name,
			String description) throws BadRequestException,
			InvalidStateException {
		if (!isValidString(description) || !isValidString(name)
				|| !isValidString(authorUsr)) {
			throw new BadRequestException();
		}

		Customer author = getEntity(manager, Customer.class, authorUsr);

		if (author == null || !isAbilityNameAvailable(name)) {
			throw new BadRequestException();
		}

		AbilityRequest r = new AbilityRequest(author, name, description);
		manager.persist(r);
	}

	/**
	 * @see AbilityControllerRemote
	 */
	public void reviewAbilityRequest(String administratorUsr, int requestId,
			Boolean response, String comment) throws BadRequestException,
			InvalidStateException {

		Administrator admin = manager.find(Administrator.class,
				administratorUsr);
		AbilityRequest req = manager.find(AbilityRequest.class, requestId);

		if (!isValidString(comment) || response == null || admin == null
				|| req == null) {
			throw new BadRequestException();
		}

		if (response == true) {
			// Create new ability
			Ability ab = new Ability(req.getAbilityName(),
					req.getAbilityDescription(), admin);
			manager.persist(ab);
		}

		req.setApproved(response);
		req.setReview(comment);
	}

	/**
	 * @see AbilityControllerRemote
	 */
	public void addNewAbility(String administratorUsr, String name,
			String description) throws BadRequestException,
			InvalidStateException {

		if (!isValidString(name) || !isValidString(description)) {
			throw new BadRequestException();
		}

		Administrator admin = getEntity(manager, Administrator.class,
				administratorUsr);

		if (!isAbilityNameAvailable(name)) {
			throw new InvalidStateException();
		}

		Ability ab = new Ability(name, description, admin);
		manager.persist(ab);

	}

	/**
	 * @see AbilityControllerRemote
	 */
	public List<?> getAvailableAbilityList() {
		Query q = manager.createQuery("FROM Ability a");
		return q.getResultList();
	}

	public List<?> getAvailableAbilityList(String match) {
		Query q = manager
				.createQuery("FROM Ability a WHERE a.name LIKE :match");
		q.setParameter("match", "%" + match + "%");
		return q.getResultList();
	}

	/* Helpers */

	private boolean isValidString(String input) {
		return input != null && input.length() > 0;
	}

	private Boolean isAbilityNameAvailable(String name) {
		Boolean noAbilityWithName = manager.find(Ability.class, name) == null;
		Query q = manager
				.createQuery("FROM AbilityRequest r WHERE r.abilityName=:name");
		q.setParameter("name", name);
		Boolean noRequestWithName = q.getResultList().size() == 0;

		return noAbilityWithName && noRequestWithName;
	}

	private <T> T getEntity(EntityManager manager, Class<T> entityClass,
			Object key) throws BadRequestException {
		T entity = manager.find(entityClass, key);
		if (entity == null) {
			throw new BadRequestException();
		} else {
			return entity;
		}
	}

	public List<?> getAbilityRequestList() {
		Query q = manager
				.createQuery("FROM AbilityRequest r WHERE r.approved=null");
		return q.getResultList();
	}
}
