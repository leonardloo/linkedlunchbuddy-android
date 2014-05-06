package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LunchDateStatus {
	
	public static final String STATUS_PENDING = "Lunchdate status pending - check back for updates";
	public static final String STATUS_MATCHED = "Lunchdate found - Congratulations!";
	public static final String STATUS_REQUESTEXPIRED = "Lunchdate not found - request has expired";
	public static final String STATUS_DEFAULT = "Request not yet sent in";
	

	private String status;
	private String partner;
	private String partnerEmail;
	private List<Map<String, String>> restaurants;
	private String time;
	private String submittedTime;

	public LunchDateStatus() { };
	
	public LunchDateStatus(String status, String partner, String partnerEmail, List<Map<String, String>> restaurants, String time, String submittedTime) {
		this.status = status;
		this.partner = partner;
		this.partnerEmail = partnerEmail;
		this.restaurants = restaurants;
		this.time = time;
		this.submittedTime = submittedTime;
	}
	
	public LunchDateStatus(JSONObject jsonObject) {
		try {
			status = jsonObject.getString("status");
			partner = jsonObject.getString("partnerName");
			partnerEmail = jsonObject.getString("partnerEmail");
			JSONArray restaurantsArray = new JSONArray(jsonObject.getString("restaurants"));
//			System.out.println(restaurantsString);
			restaurants = new ArrayList<Map<String, String>>();
			for (int i = 0; i < restaurantsArray.length(); i++) {
				JSONObject restaurant = restaurantsArray.getJSONObject(i);
				Map<String, String> restaurantMap = new HashMap<String, String>();
				restaurantMap.put("lat", restaurant.getString("lat"));
				restaurantMap.put("lng", restaurant.getString("lng"));
				restaurantMap.put("name", restaurant.getString("name"));
				restaurants.add(restaurantMap);
			}
			time = jsonObject.getString("time");
			submittedTime = jsonObject.getString("submittedTime");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("status", status);
			result.put("partnerName", partner);
			result.put("partnerEmail", partnerEmail);
			// Convert restaurants to JSONArray of JSONObjects
			List<JSONObject> convertedRestaurants = new ArrayList<JSONObject>();
			for (Map<String, String> restaurant : restaurants) {
				JSONObject convertedRestaurant = new JSONObject(restaurant);
				convertedRestaurants.add(convertedRestaurant);
			}
			JSONArray restaurantsArray = new JSONArray(convertedRestaurants);
			result.put("restaurants", restaurantsArray);
			result.put("time", time);
			result.put("submittedTime", submittedTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getPartnerEmail() {
		return partnerEmail;
	}
	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}
	public List<Map<String, String>> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(List<Map<String, String>> restaurants) {
		this.restaurants = restaurants;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(String submittedTime) {
		this.submittedTime = submittedTime;
	}

}
