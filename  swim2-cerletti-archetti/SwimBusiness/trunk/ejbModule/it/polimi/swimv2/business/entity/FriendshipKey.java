package it.polimi.swimv2.business.entity;

import java.io.Serializable;

public class FriendshipKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3232943609888359602L;

	/* Getters and setters */

	private Customer sender, receiver;

	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Customer getReceiver() {
		return receiver;
	}

	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	@Override
	public boolean equals(Object arg) {
		if (!(arg instanceof FriendshipKey)) {
			return false;
		}

		FriendshipKey key = (FriendshipKey) arg;

		return isSameFriendship(this, key);
	}

	private boolean isSameFriendship(FriendshipKey key1, FriendshipKey key2) {
		return (key1.sender.equals(key2.sender) && key1.receiver
				.equals(key2.receiver))
				|| (key1.sender.equals(key2.receiver) && key1.receiver
						.equals(key2.sender));

	}

	@Override
	public int hashCode() {
		int s = sender.hashCode(), r = receiver.hashCode();

		return (s + r) * (s * r);
	}
}
