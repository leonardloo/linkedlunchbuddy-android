package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.requestendpoint.model.Request;

public class RequestActivity extends FragmentActivity {
	
	private DataHandler dataHandler;
	
	// Immediately launches RequestTimeFragment
	private RequestTimeFragment requestTimeFragment;
	private Request currentRequest;
	
	private List<GoogleLocation> selectedRestaurants;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		
		// Initialization of Request fields
		currentRequest = new Request();
		// Set user
		dataHandler = new DataHandler(getBaseContext());
		dataHandler.open();
		Cursor cursor = dataHandler.allUsers();
		cursor.moveToFirst();
		currentRequest.setUserId(cursor.getString(1));
		cursor.close();
		dataHandler.close();
		// Set lat lon fields
		// TODO: Add lat lon of current location of an actual Android phone
		currentRequest.setLat(39.952472);
		currentRequest.setLon(-75.196679);
		
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        requestTimeFragment = new RequestTimeFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(R.id.request_frame_container, requestTimeFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        requestTimeFragment = (RequestTimeFragment) getSupportFragmentManager()
	        .findFragmentById(R.id.request_frame_container);
	    }
	}
	
	public void setTime(long startTime, long endTime) {
		this.currentRequest.setStartTime(startTime);
		this.currentRequest.setEndTime(endTime);
	}

	public List<GoogleLocation> getSelectedRestaurants() {
		return selectedRestaurants;
	}
	
	public Request getRequest() {
		return this.currentRequest;
	}

	public void setSelectedRestaurants(List<GoogleLocation> selectedRestaurants) {
		this.selectedRestaurants = selectedRestaurants;
		
		List<String> selectionsForRequest = new ArrayList<String>();
		for (GoogleLocation location : selectedRestaurants) {
			selectionsForRequest.add(location.getId());
		}
		this.currentRequest.setRestaurantPreferences(selectionsForRequest);
		
		
	}
	
	
	


}
