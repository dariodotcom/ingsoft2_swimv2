package it.polimi.swim.business.bean;

import java.util.List;

import it.polimi.swim.business.bean.remote.AbilityControllerRemote;
import it.polimi.swim.business.entity.Ability;
import it.polimi.swim.business.entity.AbilityRequest;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class AbilityController
 */
@Stateless
public class AbilityController implements AbilityControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	public AbilityController() {
		// TODO Auto-generated constructor stub
	}

	public void addAbilityRequest(String authorUsr, String name,
			String description) throws BadRequestException,
			InvalidStateException {
		if (!checkString(description) || !checkString(name)
				|| !checkString(authorUsr)) {
			throw new BadRequestException();
		}

		Customer author = manager.find(Customer.class, authorUsr);

		if (author == null || !isAbilityNameAvailable(name)) {
			throw new BadRequestException();
		}
		
		AbilityRequest r = new AbilityRequest(author, name, description);
		manager.persist(r);
	}

	public void reviewAbilityRequest(String administratorUsr, int requestId,
			boolean response, String comment) throws BadRequestException,
			InvalidStateException {
		// TODO Auto-generated method stub

	}

	public void addNewAbility(String name, String decription)
			throws BadRequestException, InvalidStateException {
		// TODO Auto-generated method stub

	}

	public List<Ability> getAvailableAbilityList() {
		// TODO Auto-generated method stub
		return null;
	}

	/* Helpers */
	private boolean checkString(String input) {
		return input != null && input.length() > 0;
	}

	private Boolean isAbilityNameAvailable(String name) {
		return manager.find(Ability.class, name) == null;
	}
}
