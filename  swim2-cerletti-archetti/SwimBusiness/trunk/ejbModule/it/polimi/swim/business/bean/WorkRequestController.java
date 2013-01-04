package it.polimi.swim.business.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
import it.polimi.swim.business.entity.Ability;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Message;
import it.polimi.swim.business.entity.WorkRequest;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class WorkRequestController.
 */
@Stateless
public class WorkRequestController implements WorkRequestControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public WorkRequestController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws InvalidStateException
	 * @see WorkRequestControllerRemote
	 */
	public int createWorkRequest(Map<String, Object> properties)
			throws BadRequestException, InvalidStateException {
		String senderUsr = (String) properties.get("sender");
		String receiverUsr = (String) properties.get("receiver");

		Customer sender = getCustomer(senderUsr), receiver = getCustomer(receiverUsr);

		// Check receiver abilities
		List<Ability> receiverAbilities = receiver.getAbilityList();
		Ability a = Helpers.getEntityChecked(manager, Ability.class,
				properties.get("selectedAbility"));

		if (receiverAbilities.size() == 0 || !receiverAbilities.contains(a)) {
			throw new InvalidStateException();
		}

		WorkRequest w = new WorkRequest(sender, receiver);
		w.setDescription((String) properties.get("description"));
		w.setRequiredAbility(a);
		w.setStartDate((Date) properties.get("start"));
		w.setEndDate((Date) properties.get("end"));
		w.setLocation((String) properties.get("location"));

		manager.persist(w);
		return w.getId();
	}

	/**
	 * @throws InvalidStateException
	 * @see WorkRequestControllerRemote
	 */
	public void respondToWorkRequest(String responseAuthorUsr,
			Boolean responseDescriptor, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException,
			InvalidStateException {

		Customer author = getCustomer(responseAuthorUsr);
		WorkRequest request = getRequest(workRequestId);

		if (author.equals(request.getSender())) {

			if (request.isConfirmedBySender() != null) {
				throw new InvalidStateException();
			} else {
				request.setSenderConfirmed(responseDescriptor);
			}

		} else if (author.equals(request.getReceiver())) {

			if (request.isConfirmedByReceiver() != null) {
				throw new InvalidStateException();
			} else {
				request.setReceiverConfirmed(responseDescriptor);
			}

		} else {
			throw new UnauthorizedRequestException();
		}
	}

	/**
	 * @throws InvalidStateException
	 * @see WorkRequestControllerRemote
	 */
	public void markRequestAsCompleted(String responseAuthorUsr,
			int workRequestId) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException {
		System.out.println("lol");
		Customer author = getCustomer(responseAuthorUsr);
		WorkRequest request = getRequest(workRequestId);

		if (author.equals(request.getSender())) {
			if (request.getSenderCompleted()) {
				throw new InvalidStateException();
			} else {
				request.setSenderCompleted();
			}
		} else if (author.equals(request.getReceiver())) {
			if (request.getReceiverCompleted()) {
				throw new InvalidStateException();
			} else {
				request.setReceiverCompleted();
			}
		} else
			throw new UnauthorizedRequestException();
	}

	/**
	 * @see WorkRequestControllerRemote
	 */
	public void sendMessage(String messageAuthorUsr, String text,
			int workRequestId) throws UnauthorizedRequestException,
			BadRequestException {

		Customer author = getCustomer(messageAuthorUsr);
		WorkRequest request = getRequest(workRequestId);

		Customer sender = request.getSender(), receiver = request.getReceiver();

		if (!isTextValid(text)) {
			throw new BadRequestException();
		}

		if (!(author.equals(sender) || author.equals(receiver))) {
			throw new UnauthorizedRequestException();
		}

		Message m = new Message(request, author, text);
		manager.persist(m);
	}

	/* Helpers */
	private Customer getCustomer(String username) throws BadRequestException {
		Customer customer = manager.find(Customer.class, username);
		if (customer == null) {
			throw new BadRequestException();
		} else {
			return customer;
		}
	}

	private WorkRequest getRequest(int id) throws BadRequestException {
		WorkRequest request = manager.find(WorkRequest.class, id);
		if (request == null) {
			throw new BadRequestException();
		} else {
			return request;
		}
	}

	private Boolean isTextValid(String text) {
		return text != null && text.length() >= 0;
	}

	public WorkRequest getById(int requestId) throws BadRequestException {
		return Helpers.getEntityChecked(manager, WorkRequest.class, requestId);
	}

	public List<?> getMessageList(int reqId) throws BadRequestException {
		Query q = manager
				.createQuery("SELECT m FROM WorkRequest w, IN (w.relatedMessages) m WHERE w.id=:id ORDER BY m.sentDate");
		q.setParameter("id", reqId);
		return q.getResultList();
	}
}