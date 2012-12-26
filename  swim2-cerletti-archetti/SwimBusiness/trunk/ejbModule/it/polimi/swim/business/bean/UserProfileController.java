package it.polimi.swim.business.bean;

import java.util.List;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Friendship;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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

	public List<Customer> getFriendList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> getFriendshipRequest(String username) {
		Query q = manager
				.createQuery("FROM Friendship f WHERE f.receiver.username=:username AND f.confirmed=false");
		q.setParameter("username", username);
		return q.getResultList();
	}
	
	public List<?>	getSentFeedbacks(String username){
		Query q = manager.createQuery("SELECT f FROM Feedback f JOIN (f.linkedRequest) r WHERE r.sender.username=:username");
		q.setParameter("username", username);
		return q.getResultList();
	}
	
	public List<?> getReceivedFeedacks(String username){
		Query q = manager.createQuery("SELECT f FROM Feedback f JOIN (f.linkedRequest) r WHERE r.receiver.username=:username");
		q.setParameter("username", username);
		return q.getResultList();
	}

	public List<Friendship> getFriendshipRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer getByUsername(String username) {
		return manager.find(Customer.class, username);
	}
}
