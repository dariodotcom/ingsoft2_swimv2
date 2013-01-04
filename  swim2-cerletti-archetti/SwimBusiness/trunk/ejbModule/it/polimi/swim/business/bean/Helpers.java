package it.polimi.swim.business.bean;

import javax.persistence.EntityManager;

import it.polimi.swim.business.exceptions.BadRequestException;

/**
 * Helpers is a class that contains helper methods useful to the implementation.
 */
public class Helpers {

	/**
	 * This method provides the entire entity associated to the given key.
	 * 
	 * @param manager
	 *            an EntityManager object, i.e. an API used to create and remove
	 *            persistent entity instances, to find entities by their primary
	 *            key, and to query over entities.
	 * @param entityClass
	 *            the Class of the entity to be returned.
	 * @param key
	 *            the Object that represents the key of the given class.
	 * @return the instance of the given entityClass that corresponds to the
	 *         given key.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public static <T> T getEntityChecked(EntityManager manager,
			Class<T> entityClass, Object key) throws BadRequestException {
		T entity = manager.find(entityClass, key);
		if (entity == null) {
			throw new BadRequestException();
		} else {
			return entity;
		}
	}

	/**
	 * This method provides informations about the validity of a string.
	 * 
	 * @param s
	 *            the String we want to check the validity of.
	 * @return a Boolean value which is true if the string has been created and
	 *         it is not empty.
	 */
	public static boolean isStringValid(String s) {
		return s != null && s.length() > 0;
	}
}