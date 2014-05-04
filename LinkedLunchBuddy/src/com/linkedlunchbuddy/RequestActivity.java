package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.Calendar;
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

	// Request information maintained during here as user builds request
	private Request currentRequest;
	private double locationLat;
	private double locationLon;
	private int year = -1;
	private int month = -1;
	private int day = -1;
	private int startHour = -1;
	private int startMinute = -1;
	private int endHour = -1;
	private int endMinute = -1;

	private List<GoogleLocation> selectedRestaurants;

	RequestPagerAdapter requestPagerAdapter;
	ViewPager viewPager;
	RequestTimeFragment timeFragment = new RequestTimeFragment();
	RequestRestaurantFragment restaurantFragment = new RequestRestaurantFragment();
	RequestSubmitFragment submitFragment = new RequestSubmitFragment();

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState,
				"RequestTimeFragment", timeFragment);
		getSupportFragmentManager().putFragment(outState,
				"RequestRestaurantFragment", restaurantFragment);
		getSupportFragmentManager().putFragment(outState,
				"RequestSubmitFragment", submitFragment);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);

		if (savedInstanceState != null) {
			timeFragment = (RequestTimeFragment) getSupportFragmentManager()
					.getFragment(savedInstanceState, "RequestTimeFragment");
			restaurantFragment = (RequestRestaurantFragment) getSupportFragmentManager()
					.getFragment(savedInstanceState,
							"RequestRestaurantFragment");
			submitFragment = (RequestSubmitFragment) getSupportFragmentManager()
					.getFragment(savedInstanceState, "RequestSubmitFragment");

		} else {
			timeFragment = new RequestTimeFragment();
			restaurantFragment = new RequestRestaurantFragment();
			submitFragment = new RequestSubmitFragment();
		}

		// Initialization of Request fields
		currentRequest = new Request();
		currentRequest.setRestaurantPreferences(new ArrayList<String>());
		// Set user
		dataHandler = new DataHandler(getBaseContext());
		dataHandler.open();
		Cursor cursor = dataHandler.allUsers();
		cursor.moveToFirst();
		currentRequest.setUserId(cursor.getString(1));
		cursor.close();
		dataHandler.close();
		// Initialize restaurants
		this.selectedRestaurants = new ArrayList<GoogleLocation>();

		Bundle extras = getIntent().getExtras();
		// When entered from google maps
		if (extras.getBoolean("FROM GOOGLE MAPS")) {
			locationLat = extras.getDouble("LATITUDE");
			currentRequest.setLat(locationLat);
			locationLon = extras.getDouble("LONGITUDE");
			currentRequest.setLon(locationLon);
		}

		// Set swipe view pagers
		requestPagerAdapter = new RequestPagerAdapter(
				getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.requestPager);
		viewPager.setAdapter(requestPagerAdapter);
		viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// Update data has to happen here
						if (position == 2) {
							RequestSubmitFragment submitFragment = (RequestSubmitFragment) requestPagerAdapter
									.getItem(position);
							submitFragment.updateData();
						}
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
				requestPagerAdapter.notifyDataSetChanged();
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

		actionBar.addTab(actionBar.newTab().setText("Time")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Restaurants")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Submit")
				.setTabListener(tabListener));
	}

	public class RequestPagerAdapter extends FragmentPagerAdapter {
		public RequestPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = null;
			// Don't create new fragments!
			switch (i) {
			case 0:
				fragment = timeFragment;
				break;
			case 1:
				fragment = restaurantFragment;
				break;
			case 2:
				fragment = submitFragment;
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

	/*
	 * Getters & Setters of RequestActivity fields
	 */

	public double getLocationLat() {
		return locationLat;
	}

	public double getLocationLon() {
		return locationLon;
	}

	public void setTime(long startTime, long endTime) {
		System.out.println("setTime called in requestactivity");
		this.currentRequest.setStartTime(startTime);
		this.currentRequest.setEndTime(endTime);
	}

	public List<GoogleLocation> getSelectedRestaurants() {
		return this.selectedRestaurants;
	}

	public Request getRequest() {
		return this.currentRequest;
	}

	public void modifySelectedRestaurant(GoogleLocation selectedRestaurant) {
		if (selectedRestaurant.isSelected()) {
			// Add it
			this.selectedRestaurants.add(selectedRestaurant);

		} else {
			// Remove it
			this.selectedRestaurants.remove(selectedRestaurant);
		}
		System.out.println(this.selectedRestaurants.size());
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	


}
