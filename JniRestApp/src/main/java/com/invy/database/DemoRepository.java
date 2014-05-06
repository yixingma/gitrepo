package com.invy.database;

import java.util.List;

import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;

public interface DemoRepository {
	List<Kit> getKitsByUserId(String userId);

	List<Itemref> getAllItemrefs();

	List<Itemref> searchItemrefsByName(String name);

	List<Kittype> getAllKitType();
	/**
	 * addObject
	 * 
	 * @param T
	 *            obj
	 */
	<T> T addObject(final T obj);

	/**
	 * find
	 * 
	 * @param Object
	 *            obj, final Class<T> clazz
	 */
	<T> T findById(final Object obj, final Class<T> clazz);

	/**
	 * Log object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param obj
	 *            the obj
	 */
	<T> void logObject(T obj);
}
