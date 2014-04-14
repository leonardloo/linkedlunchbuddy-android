package com.linkedlunchbuddy.placesapi;

import org.json.JSONException;
import org.json.JSONObject;

public class GoogleLocation {

	private String address, id, name;
	private double lat, lng, rating;
	private String[] types;
	private boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public boolean toggleSelected() {
		this.selected = !this.selected;
		return this.selected;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public GoogleLocation(JSONObject jObject) {
		try {
			address = jObject.getString("vicinity");
			id = jObject.getString("id");
			JSONObject geometry = jObject.getJSONObject("geometry");
			JSONObject location = geometry.getJSONObject("location");
			lat = location.getDouble("lat");
			lng = location.getDouble("lng");
			name = jObject.getString("name");
		} catch (JSONException e) {
			return;

		}
	}

}
