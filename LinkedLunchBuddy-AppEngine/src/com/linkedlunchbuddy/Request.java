package com.linkedlunchbuddy;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private long startTime;
	private long endTime;
	private String userId;
	private String userName;
	private List<String> restaurantPreferences;
	private double lat;
	private double lon;

	// dummy constructor
	public Request() {
	}

	public Request(long startTime, long endTime, String userId, String userName,
			double lat, double lon) {
		if (startTime >= endTime) {
			throw new IllegalArgumentException();
		}

		this.startTime = startTime;
		this.endTime = endTime;
		this.userId = userId;
		this.userName = userName;
		this.lat = lat;
		this.lon = lon;
		
	}

	// Getters & Setters

	public Long getId() {
		return this.id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUser(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRestaurantPreferences() {
		return restaurantPreferences;
	}

	public void setRestaurantPreferences(List<String> restaurantPreferences) {
		this.restaurantPreferences = restaurantPreferences;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}