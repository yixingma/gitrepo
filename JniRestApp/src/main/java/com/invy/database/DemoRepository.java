package com.invy.database;

import java.util.List;

import com.invy.database.jpa.data.Item;
import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;
import com.invy.database.jpa.data.Optitemtemplate;
import com.invy.database.jpa.data.Owner;
import com.invy.database.jpa.data.Subkit;

public interface DemoRepository {
	List<Kit> searchKitsByUserId(String userId);

	List<Itemref> getAllItemrefs();

	List<Itemref> searchItemrefsByName(String name);

	List<Kittype> getAllKitTypes();
	
	List<Optitemtemplate> searchOptitemsBySubkitAndTemplate(int subkitTypeId,int templateId);
	
	List<Item> searchItemsBySubkitId(int subkitId);
	
	Owner searchOwnerByUserId(String userId);
	
	void updateItemQuantity(int itemId,int newQuantity);
	
	List<Owner> searchOwnersByUserName(String userName);
	
	List<Subkit> searchSubkitByKitIdAndSubkitTypeId(int kitId, int subkitTypeId);
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
	/**
     * remove Data.
     * 
     * @param <T> the generic type
     * @param obj the obj
     */
    <T> void removeObject(final T obj);

	/**
	 * remove all items based on subkitId.
	 * 
	 * @param Integer the subkitId
	 */
    int removeAllItemsBySubkitId(Integer subkitId);
}
