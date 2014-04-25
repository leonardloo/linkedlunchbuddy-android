package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.requestendpoint.model.Request;

public class RequestActivity extends FragmentActivity {
	
	private DataHandler dataHandler;
	
	// Immediately launches RequestTimeFragment
	private RequestTimeFragment requestTimeFragment;
	private Request currentRequest;
	private double locationLat;
	private double locationLon;
	
	private List<GoogleLocation> selectedRestaurants;
	
    RequestPagerAdapter requestPagerAdapter;
    ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Bundle extras = getIntent().getExtras();
		// When entered from google maps
		if (extras.getBoolean("FROM GOOGLE MAPS")) {
			locationLat = extras.getDouble("LATITUDE");
			locationLon = extras.getDouble("LONGITUDE");
		}
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		
		// Set swipe view pagers
		requestPagerAdapter =
                new RequestPagerAdapter(
                        getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.requestPager);
        viewPager.setAdapter(requestPagerAdapter);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        
        // Actions bars for swipes
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
	
				viewPager.setCurrentItem(tab.getPosition());
				
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
        };

            actionBar.addTab(actionBar.newTab().setText("Time").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("Restaurants").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("Submit").setTabListener(tabListener));
		
		
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
		// TODO: Upda lat lon of current location of an actual Android phone
		currentRequest.setLat(39.952472);
		currentRequest.setLon(-75.196679);
		
		/*
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
	    */
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

	public double getLocationLat() {
		return locationLat;
	}


	public double getLocationLon() {
		return locationLon;
	}

	public class RequestPagerAdapter extends FragmentPagerAdapter {
	    public RequestPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public Fragment getItem(int i) {
	    	Fragment fragment = null;
	    	switch (i) {
	    	case 0 : 
	    		fragment = new RequestTimeFragment();
	    		break;
	    	case 1:
	    		fragment = new RequestRestaurantFragment();
	    		break;
	    	case 2:
	    		fragment = new RequestSubmitFragment();
	    		break;
	    	default:
	    		break;
	    	}
	        return fragment;
	    }

	    @Override
	    public int getCount() {
	        return 3;
	    }

	}


	
	


}
