package com.linkedlunchbuddy;

import com.linkedlunchbuddy.EMF;

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

@Api(name = "requestendpoint", namespace = @ApiNamespace(ownerDomain = "linkedlunchbuddy.com", ownerName = "linkedlunchbuddy.com", packagePath = ""))
public class RequestEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRequest")
	public CollectionResponse<Request> listRequest(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Request> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Request as Request");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Request>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Request obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Request> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getRequest")
	public Request getRequest(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Request request = null;
		try {
			request = mgr.find(Request.class, id);
		} finally {
			mgr.close();
		}
		return request;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param request the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertRequest")
	public Request insertRequest(Request request) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsRequest(request)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(request);
		} finally {
			mgr.close();
		}
		return request;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param request the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateRequest")
	public Request updateRequest(Request request) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsRequest(request)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(request);
		} finally {
			mgr.close();
		}
		return request;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeRequest")
	public void removeRequest(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Request request = mgr.find(Request.class, id);
			mgr.remove(request);
		} finally {
			mgr.close();
		}
	}

	private boolean containsRequest(Request request) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if (request.getId() == null)
				return false;
			Request item = mgr.find(Request.class, request.getId());
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
