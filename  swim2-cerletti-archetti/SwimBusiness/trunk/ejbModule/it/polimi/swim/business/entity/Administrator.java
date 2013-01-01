package it.polimi.swim.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Administrator is an entity which represents the administrator of system
 * SWIMv2, i.e. the figure responsible for the definition of the profession
 * reportable by users.
 */
@Entity
@Table(name = "administrator")
public class Administrator extends User {

	public Administrator() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param username
	 *            a String which contains the username associated to the
	 *            administrator.
	 * @param passwordHash
	 *            a String which contains an hash value associated to the
	 *            administrator password.
	 */
	public Administrator(String username, String password) {
		super(username, password);
	}
}
