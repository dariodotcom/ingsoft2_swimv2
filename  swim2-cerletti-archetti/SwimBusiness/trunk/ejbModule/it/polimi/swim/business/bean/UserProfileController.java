package it.polimi.swim.business.bean;

import java.util.List;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Friendship;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class UserProfileController
 */
@Stateless
public class UserProfileController implements UserProfileControllerRemote {

    /**
     * Default constructor. 
     */
    public UserProfileController() {
        // TODO Auto-generated constructor stub
    }

	public List<Customer> getFriendList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Friendship> getFriendshipRequest() {
		// TODO Auto-generated method stub
		return null;
	}
  
}
