package com.linkedlunchbuddy;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LunchDate {

	private static final long WAIT_TIME = 5 * 60 * 60;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Request requestA;

	private Request requestB;

	private Map<String, String> venue;

	private boolean isConfirmed;

	private TimeInterval matchedInterval;

	private Date expiryTime;

	public LunchDate(Request requestA, Request requestB, Map<String, String> venue,
			TimeInterval matched) {
		this.requestA = requestA;
		this.requestB = requestB;
		this.matchedInterval = matched;
		this.venue = venue;
		this.expiryTime = new Date(System.currentTimeMillis() + WAIT_TIME);
		this.isConfirmed = false;
	}

	// Getters & Setters

	public Request getRequestA() {
		return requestA;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public TimeInterval getMatchedInterval() {
		return matchedInterval;
	}

	public void setMatchedInterval(TimeInterval matchedInterval) {
		this.matchedInterval = matchedInterval;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public void setRequestA(Request requestA) {
		this.requestA = requestA;
	}

	public Request getRequestB() {
		return requestB;
	}

	public void setRequestB(Request requestB) {
		this.requestB = requestB;
	}

	public Map<String, String> getVenue() {
		return this.venue;
	}

	public void setVenue(Map<String, String> venue) {
		this.venue = venue;
	}

}
