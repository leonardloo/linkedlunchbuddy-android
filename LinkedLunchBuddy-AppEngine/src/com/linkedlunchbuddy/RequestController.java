package com.linkedlunchbuddy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Api(name = "requestcontroller", version = "v1", description = "our endpoint for matching requests")
public class RequestController {

	private static final String API_KEY = "AIzaSyBzZz7QzXWYVKSsn4TAeaFUXg4XE0RCJ_Y";

	private static final DeviceInfoEndpoint deviceInfoEndpoint = new DeviceInfoEndpoint();
	private static final MessageEndpoint messageEndpoint = new MessageEndpoint();

	// given a request from a client application, determine a possible LunchDate
	// matching this request with other requests in the request pool
	@ApiMethod(name = "listRequests", httpMethod = HttpMethod.GET)
	public Collection<Request> listRequests() {
		Collection<Request> requestPool = new ArrayList<Request>();
		// iterate over request pool and find best match
		CollectionResponse<Request> listResp = listRequest("", null);
		requestPool = listResp.getItems();

		return requestPool;
	}

	// given a request from a client application, determine a possible LunchDate
	// matching this request with other requests in the request pool
	@ApiMethod(name = "findMatch", httpMethod = HttpMethod.GET)
	public LunchDate matchRequest(@Named("requestId") Long requestId) {
		LunchDate result = null;
		// iterate over request pool and find best match
		CollectionResponse<Request> listResp = listRequest("", null);
		Collection<Request> requestPool = listResp.getItems();

		EntityManager mgr = getEntityManager();

		Request request = null;
		try {
			request = mgr.find(Request.class, requestId);
		} finally {
			mgr.close();
		}

		result = MatchingAlgorithm.findMatch(request, requestPool);
		if (result != null) {
			notifyUserB(result);
		}
		return result;
	}

	/*
	 * Helper Methods to interface with datastore
	 */

	private static void notifyUserB(LunchDate date) {
		EntityManager mgr = getEntityManager();
		User userB = mgr.find(User.class, date.getRequestB().getUserId());
		User userA = mgr.find(User.class, date.getRequestA().getUserId());
		DeviceInfo userBInfo = new DeviceInfo();

		CollectionResponse<DeviceInfo> response = deviceInfoEndpoint
				.listDeviceInfo(null, 10);
		for (DeviceInfo deviceInfo : response.getItems()) {
			if (deviceInfo.getDeviceRegistrationID().equals(
					userB.getDeviceRegistrationId())) {
				userBInfo = deviceInfo;
			}
		}

		// create a MessageData entity with a timestamp of when it was
		// received, and persist it
		// TODO: change to actual restaurant name
		Message message = new Message.Builder()
				.addData("partnerName", userA.getName())
				.addData("partnerEmail", userA.getEduEmail())
				.addData("restaurant", date.getVenue())
				.addData(
						"time",
						String.valueOf(date.getMatchedInterval().getStartTime()))
				.build();

		// persist message if desired TODO: check if this is necessary
		/*
		 * MessageData messageObj = new MessageData();
		 * messageObj.setMessage(message.toString());
		 * messageObj.setTimestamp(System.currentTimeMillis()); try {
		 * mgr.persist(messageObj); } finally { mgr.close(); }
		 */

		try {
			Sender sender = new Sender(API_KEY);
			// messageEndpoint.sendMessage(message.toString());
			MessageEndpoint.doSendViaGcm(message.toString(), sender, userBInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// accessor method for persistence manager
	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRequest")
	private CollectionResponse<Request> listRequest(
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

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (Request obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Request> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

}
