package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Message;
import it.polimi.swim.business.entity.WorkRequest;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	public int createWorkRequest(String senderUsr, String receiverUsr)
			throws BadRequestException {
		Customer sender = getCustomer(senderUsr), receiver = getCustomer(receiverUsr);

		// Check receiver abilities

		WorkRequest w = new WorkRequest(sender, receiver);
		manager.persist(w);

		return w.getId();
	}

	public void respondToWorkRequest(String responseAuthorUsr,
			Boolean responseDescriptor, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException {

		Customer author = getCustomer(responseAuthorUsr);
		WorkRequest request = getRequest(workRequestId);

		if (author.equals(request.getSender())) {

			if (request.getSenderConfirmed() != null) {
				throw new BadRequestException();
			} else {
				request.setSenderConfirmed(responseDescriptor);
			}

		} else if (author.equals(request.getReceiver())) {

			if (request.getReceiverConfirmed() != null) {
				throw new BadRequestException();
			} else {
				request.setReceiverConfirmed(responseDescriptor);
			}

		} else {
			throw new UnauthorizedRequestException();
		}
	}

	public void markRequestAsCompleted(String responseAuthorUsr,
			int workRequestId) throws UnauthorizedRequestException,
			BadRequestException {

		Customer author = getCustomer(responseAuthorUsr);
		WorkRequest request = getRequest(workRequestId);

		if (author.equals(request.getSender())) {

			if (request.getSenderCompleted() != null) {
				throw new BadRequestException();
			} else {
				request.setSenderCompleted();
			}
		} else if (author.equals(request.getReceiver())) {

			if (request.getReceiverCompleted() != null) {
				throw new BadRequestException();
			} else {
				request.setReceiverCompleted();
			}
		}
	}

	public void sendMessage(String messageAuthorUsr, String text,
			int workRequestId) throws UnauthorizedRequestException,
			BadRequestException {

		Customer author = getCustomer(messageAuthorUsr);
		WorkRequest request = getRequest(workRequestId);

		Customer sender = request.getSender(), receiver = request.getReceiver();

		if(!isTextValid(text)){
			throw new BadRequestException();
		}
		
		if (!(author.equals(sender) || author.equals(receiver))) {
			throw new UnauthorizedRequestException();
		}

		Message m = new Message(request, sender, text);
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
}