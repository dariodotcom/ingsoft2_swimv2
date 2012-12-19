package it.polimi.swim.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrator")
public class Administrator extends User {
	public Administrator(String username, String passwordHash){
		super(username, passwordHash);
	}
}
