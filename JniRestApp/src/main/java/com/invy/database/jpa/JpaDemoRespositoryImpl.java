/**
 * 
 */
package com.invy.database.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Item;
import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;
import com.invy.database.jpa.data.Optitemtemplate;
import com.invy.database.jpa.data.Owner;
import com.invy.database.jpa.data.Subkit;

/**
 * @author ema
 * 
 */
@Repository
public class JpaDemoRespositoryImpl implements DemoRepository {
	@PersistenceContext(unitName = "myEntity")
	private transient EntityManager entityManager;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JpaDemoRespositoryImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.invy.database.DemoRepository#getKitsByUserId(java.lang.String)
	 */
	@Override
	public List<Kit> searchKitsByUserId(String userId) {
		List<Kit> kits = entityManager
				.createNamedQuery("SEARCH_KITS_BY_USERID", Kit.class)
				.setParameter("userID", userId).getResultList();
		return kits;
	}

	@Override
	public List<Itemref> getAllItemrefs() {
		return entityManager
				.createNamedQuery("GET_ALL_ITEMREFS", Itemref.class)
				.getResultList();
	}

	@Override
	public List<Itemref> searchItemrefsByName(String name) {
		if (StringUtils.isBlank(name)) {
			name = "";
		} else {
			name = name.toUpperCase();
		}
		return entityManager
				.createNamedQuery("SEARCH_ITEMREFS_BY_NAME", Itemref.class)
				.setParameter("name", "%" + name + "%").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public <T> T addObject(T obj) {
		entityManager.persist(obj);
		return obj;
	}

	@Override
	public <T> T findById(Object obj, Class<T> clazz) {
		final T result = entityManager.find(clazz, obj);
		return result;
	}

	@Override
	public <T> void logObject(T obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Kittype> getAllKitTypes() {
		return entityManager.createNamedQuery(
				"GET_ALL_KITTYPES_WITH_SUBKITTYPES", Kittype.class)
				.getResultList();
	}

	@Override
	public Owner searchOwnerByUserId(String userId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("userID {}", userId);
		}
		Owner owner = entityManager
				.createNamedQuery("SEARCH_OWNER_LOCATION_BY_USERID",
						Owner.class).setParameter("userID", userId)
				.getSingleResult();
		return owner;
	}

	@Override
	public List<Owner> searchOwnersByUserName(String userName) {
		String delims = "[ ]+";
		String[] tokens = userName.split(delims);
		List<Owner> owners = new ArrayList<>();
		if (tokens.length > 1) {
			owners = entityManager
					.createNamedQuery(
							"SEARCH_OWNERS_LOCATIONS_BY_FIRSTLASTNAME",
							Owner.class).setParameter("firstName", tokens[0])
					.setParameter("lastName", tokens[1]).getResultList();
		} else if (tokens.length == 1) {
			owners = entityManager
					.createNamedQuery("SEARCH_OWNERS_LOCATIONS_BY_FIRSTNAME",
							Owner.class).setParameter("firstName", tokens[0])
					.getResultList();
		}
		return owners;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public void updateItemQuantity(int itemId, int newQuantity) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("itemId {}", itemId);
		}
		Item item = entityManager.find(Item.class, itemId);
		item.setUnitNum(newQuantity);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public <T> void removeObject(T obj) {
		entityManager.remove(obj);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public int removeAllItemsBySubkitId(Integer subkitId) {
		return entityManager.createNamedQuery("REMOVE_ALL_ITEMS_BY_SUBKITID")
				.setParameter("id", subkitId).executeUpdate();
	}

	@Override
	public List<Optitemtemplate> searchOptitemsBySubkitAndTemplate(
			int subkitTypeId, int templateId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subkitTypeId {}", subkitTypeId);
			LOGGER.debug("templateId {}", templateId);
		}
		List<Optitemtemplate> optItemsList = entityManager
				.createNamedQuery("SEARCH_OPTITEMS_BY_SUBKIT_AND_TEMPLATE",
						Optitemtemplate.class)
				.setParameter("templateId", templateId)
				.setParameter("subkitTypeId", subkitTypeId).getResultList();
		return optItemsList;
	}

	@Override
	public List<Item> searchItemsBySubkitId(int subkitId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subkitId {}", subkitId);
		}
		List<Item> itemsList = entityManager
				.createNamedQuery("SEARCH_ITEMS_BY_SUBKITID", Item.class)
				.setParameter("subkitId", subkitId).getResultList();
		return itemsList;
	}

	@Override
	public List<Subkit> searchSubkitByKitIdAndSubkitTypeId(int kitId,
			int subkitTypeId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("kitId {}", kitId);
			LOGGER.debug("subkitTypeId {}", subkitTypeId);
		}
		List<Subkit> subkitList = entityManager
				.createNamedQuery("SEARCH_SUBKIT_BY_KITID_AND_SUBKITTYPEID",
						Subkit.class).setParameter("kitId", kitId)
				.setParameter("subkitTypeId", subkitTypeId).getResultList();
		return subkitList;
	}

}
