package it.polimi.swim.business.bean;

import javax.persistence.EntityManager;

import it.polimi.swim.business.exceptions.BadRequestException;

public class Helpers {

	public static <T> T getEntityChecked(EntityManager manager,
			Class<T> entityClass, Object key) throws BadRequestException {
		T entity = manager.find(entityClass, key);
		if (entity == null) {
			throw new BadRequestException();
		} else {
			return entity;
		}
	}

	public static Boolean isStringValid(String s) {
		return s != null && s.length() > 0;
	}
}