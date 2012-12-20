package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.UserNotFoundException;
import it.polimi.swim.business.exceptions.UsernameAlreadyTakenException;

import javax.ejb.Remote;

@Remote
public interface AuthenticationControllerRemote {

	public UserType authenticateUser(String username, String passwordHash)
			throws UserNotFoundException, AuthenticationFailedException;

	public void createUser(String username, String password, String email,
			String name, String surname) throws UsernameAlreadyTakenException,
			EmailAlreadyTakenException;
}
