package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.entity.Ability;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AbilityControllerRemote {
	public void addAbilityRequest(String author, String name, String description)
			throws BadRequestException, InvalidStateException;

	public void reviewAbilityRequest(String administratorUsr, int requestId,
			boolean response, String comment) throws BadRequestException,
			InvalidStateException;

	public void addNewAbility(String name, String decription) throws BadRequestException,
			InvalidStateException;

	public List<Ability> getAvailableAbilityList();
}
