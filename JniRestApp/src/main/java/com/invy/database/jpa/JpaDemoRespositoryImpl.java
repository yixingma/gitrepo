/**
 * 
 */
package com.invy.database.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;
import com.invy.database.jpa.data.Owner;

/**
 * @author ema
 * 
 */
@Repository
public class JpaDemoRespositoryImpl implements DemoRepository {
	@PersistenceContext(unitName = "myEntity")
	private transient EntityManager entityManager;

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
					.createNamedQuery("SEARCH_OWNERS_LOCATIONS_BY_FIRSTLASTNAME",
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

}
