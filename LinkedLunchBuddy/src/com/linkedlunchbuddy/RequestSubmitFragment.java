package com.linkedlunchbuddy;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.linear_axle_547.requestcontroller.model.LunchDate;
import com.linkedlunchbuddy.places.GoogleLocationListAdapter;
import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.requestendpoint.model.JsonMap;
import com.linkedlunchbuddy.requestendpoint.model.Request;

public class RequestSubmitFragment extends Fragment {

	ProgressDialog progressDialog;
	private Request requestResponse;
	//	private LunchDate matchedLunchDate;
	private RequestActivity activity;
	private View rootView;
	private boolean isReadyToSubmit = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_requestsubmit, container,
				false);
		activity = (RequestActivity) getActivity();

		updateData();

		// Submit request button

		Button submitButton = (Button) rootView
				.findViewById(R.id.submitRequestButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Check if time is set correctly
				if (!isReadyToSubmit) {
					Toast.makeText(activity.getApplicationContext(), "Please go back and set a start time before end time.", Toast.LENGTH_LONG).show();
				} else {
					// Launch AsyncTask
					new createRequestTask(
							((RequestActivity) RequestSubmitFragment.this
									.getActivity()).getRequest()).execute();
					new FindMatchTask().execute();
				}
			}

		});


		return rootView;
	}

	/*
	 * RequestTabFragment inherited methods
	 */

	public void updateData() {
		if (activity != null) {
			// Set Request unix time
			long startUnixTime = unixTime(activity.getYear(), activity.getMonth(), activity.getDay(), activity.getStartHour(), activity.getStartMinute());
			long endUnixTime = unixTime(activity.getYear(), activity.getMonth(), activity.getDay(), activity.getEndHour(), activity.getEndMinute());
			if (startUnixTime > endUnixTime) {
				isReadyToSubmit = false;
			} else {
				isReadyToSubmit = true;
			}
			Date startTimeDate = new Date(startUnixTime * 1000L);
			Date endTimeDate = new Date(endUnixTime * 1000L);

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String startTimeString = df.format(startTimeDate);
			String endTimeString = df.format(endTimeDate);


			TextView startDateText = (TextView) rootView.findViewById(R.id.startDateInfo);
			TextView endDateText = (TextView) rootView.findViewById(R.id.endDateInfo);
			startDateText.setText("Start Date: " + startTimeString);
			endDateText.setText("End Date: " + endTimeString);
			Request request = activity.getRequest();
			request.setStartTime(startUnixTime);
			request.setEndTime(endUnixTime);

			List<GoogleLocation> restaurants = activity.getSelectedRestaurants();
			List<JsonMap> restaurantsInfo = new ArrayList<JsonMap>();
			List<GoogleLocation> displayedRestaurants = new ArrayList<GoogleLocation>();

			if (restaurants != null) {
				for (GoogleLocation restaurant : restaurants) {
					displayedRestaurants.add(restaurant);

					JsonMap info = new JsonMap();
					info.put("id", restaurant.getId());
					info.put("name", restaurant.getName());
					info.put("lat", "" + restaurant.getLat());
					info.put("lon", "" + restaurant.getLng());
					// Convert this map to JsonMap
					restaurantsInfo.add(info);
				}
			}
			ListView restaurantsList = (ListView) rootView.findViewById(R.id.restaurantsListView);
			GoogleLocationListAdapter adapter = new GoogleLocationListAdapter(
					getActivity(), R.layout.google_location_list_item,
					displayedRestaurants);
			restaurantsList.setAdapter(adapter);
			
			request.setRestaurantPreferences(restaurantsInfo);

			startDateText.invalidate();
			endDateText.invalidate();

		}
	}

	/**
	 * CREATE A REQUEST WITH THE REQUESTENDPOINT BUILDER
	 * 
	 * @author blotter
	 * @param Edu
	 *            Email of user creating the request
	 * @return created Request Object
	 */
	public class createRequestTask extends AsyncTask<Context, Void, Void> {

		private Request request;


		public createRequestTask(Request request) {
			this.request = request;
			System.out.println("request send off with time: "+ request.getStartTime()+"--"+request.getEndTime());
		}

		@Override
		protected void onPreExecute() {
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(activity);
				progressDialog.setMessage("Finding match, please wait...");
				progressDialog.show();
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setCancelable(false);
			}   
		}

		protected Void doInBackground(Context... contexts) {

			Request requestResponse = null;
			try {
				requestResponse = EndpointController.getRequestEndpoint()
						.insertRequest(this.request).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Sets requestResponse
			RequestSubmitFragment.this.requestResponse = requestResponse;
			return null;
		}
	}

	/**
	 * FIND A MATCH FOR A REQUEST
	 * 
	 */
	public class FindMatchTask extends AsyncTask<Context, Integer, LunchDate> {

		private Long requestId;

		public FindMatchTask() {
		}


		protected LunchDate doInBackground(Context... contexts) {
			LunchDate lunchDate = null;
			
			if (requestResponse != null) {
				requestId = Long.valueOf(requestResponse.getId());

				System.out.println("requestId is " + requestId);
				try {
					// Get match for the request
					lunchDate = EndpointController.getRequestController()
							.findMatch(requestId).execute();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return lunchDate;
		}

		@Override
		protected void onPostExecute (LunchDate lunchDate) {
			progressDialog.dismiss();
			String resultText = null;
			LunchDateStatus statusToBeStored = new LunchDateStatus();
			RequestActivity activity = (RequestActivity) RequestSubmitFragment.this.getActivity();
			List<Map<String, String>> restaurants = RequestSubmitFragment.this.
					convertGoogleLocationList(activity.getSelectedRestaurants());
			if (lunchDate == null) {
				// Pending in core data
				statusToBeStored = new LunchDateStatus(LunchDateStatus.STATUS_PENDING, 
						"", "", restaurants);
				resultText = "Sorry, we could not find a match at this time";
			} else {
				// Match in core data
				// TODO: Modify request on the backend to include user's name too
				statusToBeStored = new LunchDateStatus(LunchDateStatus.STATUS_MATCHED, 
						"name"/*lunchDate.getRequestB().getUser()*/, lunchDate.getRequestB().getUserId(), restaurants);				
				resultText = "Congratulations! You have a match!";
			}

			// Updates core data
			DataHandler dataHandler = new DataHandler(activity.getApplicationContext());
			dataHandler.open();
			dataHandler.updateLunchDateStatus(statusToBeStored.toJSON().toString());
			dataHandler.close();

			// Testing purposes
			int duration = Toast.LENGTH_SHORT;
			Context context = getActivity().getApplicationContext();
			Toast toast = Toast.makeText(context, resultText, duration);
			toast.show();

			// TODO: Move back to HomeActivity selectively after submitting
			// Request
			Intent intent = new Intent(RequestSubmitFragment.this.getActivity(), HomeActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Unix time conversion
	 */
	// Helper method to convert to UNIX time
	private long unixTime(int year, int month, int day, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() / 1000L);
	}

	// Helper method to convert List of GoogleLocation to List<Map<String, String>>
	private List<Map<String, String>> convertGoogleLocationList(List<GoogleLocation> locations) {
		List<Map<String, String>> convertedLocations = new ArrayList<Map<String, String>>();
		for (GoogleLocation location : locations) {
			Map<String, String> convertedLocation = new HashMap<String, String>();
			convertedLocation.put("lat", "" + location.getLat());
			convertedLocation.put("lng", "" + location.getLng());
			convertedLocation.put("name", location.getName());
			convertedLocations.add(convertedLocation);
		}
		return convertedLocations;
	}


}
