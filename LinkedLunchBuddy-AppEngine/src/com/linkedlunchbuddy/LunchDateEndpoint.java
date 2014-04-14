package com.linkedlunchbuddy;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "lunchdateendpoint", namespace = @ApiNamespace(ownerDomain = "linkedlunchbuddy.com", ownerName = "linkedlunchbuddy.com", packagePath = ""))
public class LunchDateEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listLunchDate")
	public CollectionResponse<LunchDate> listLunchDate(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<LunchDate> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from LunchDate as LunchDate");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<LunchDate>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (LunchDate obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<LunchDate> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getLunchDate")
	public LunchDate getLunchDate(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		LunchDate lunchdate = null;
		try {
			lunchdate = mgr.find(LunchDate.class, id);
		} finally {
			mgr.close();
		}
		return lunchdate;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param lunchdate the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertLunchDate")
	public LunchDate insertLunchDate(LunchDate lunchdate) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsLunchDate(lunchdate)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(lunchdate);
		} finally {
			mgr.close();
		}
		return lunchdate;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param lunchdate the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateLunchDate")
	public LunchDate updateLunchDate(LunchDate lunchdate) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsLunchDate(lunchdate)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(lunchdate);
		} finally {
			mgr.close();
		}
		return lunchdate;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeLunchDate")
	public void removeLunchDate(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			LunchDate lunchdate = mgr.find(LunchDate.class, id);
			mgr.remove(lunchdate);
		} finally {
			mgr.close();
		}
	}

	private boolean containsLunchDate(LunchDate lunchdate) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			LunchDate item = mgr.find(LunchDate.class, lunchdate.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
