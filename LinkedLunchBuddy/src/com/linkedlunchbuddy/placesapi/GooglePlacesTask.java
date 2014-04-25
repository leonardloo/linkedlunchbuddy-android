package com.linkedlunchbuddy.placesapi;

import java.util.ArrayList;

import android.os.AsyncTask;

public class GooglePlacesTask extends
		AsyncTask<String, Void, ArrayList<GoogleLocation>> {

	private double latitude;
	private double longitude;
	private double radius;

	public GooglePlacesTask(double latitude, double longitude, double radius) {
		System.out.println("google palces task constructed");
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}

	/*
	 * Example of an asynchronous task calling the GooglePlacesApi Interface
	 * Method to retrieve nearby restaurants - the list can then be used in a
	 * list view
	 */
	@Override
	protected ArrayList<GoogleLocation> doInBackground(String... params) {
		
		System.out.println("google palces task executed");

		ArrayList<GoogleLocation> list = GooglePlacesAPI.getRestaurants(
				this.latitude, this.longitude, this.radius);
		return list;
	}

}
