package com.invy.database;

import java.util.List;

import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;
import com.invy.database.jpa.data.Owner;

public interface DemoRepository {
	List<Kit> searchKitsByUserId(String userId);

	List<Itemref> getAllItemrefs();

	List<Itemref> searchItemrefsByName(String name);

	List<Kittype> getAllKitTypes();
	
	Owner searchOwnerByUserId(String userId);
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
