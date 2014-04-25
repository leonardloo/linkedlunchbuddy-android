package com.linkedlunchbuddy;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.linear_axle_547.requestcontroller.model.LunchDate;
import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.requestendpoint.model.Request;

public class RequestSubmitFragment extends RequestTabFragment {

	Request requestResponse;
	LunchDate matchedLunchDate;
	TextView startDateText;
	Date startTimeDate;
	Date endTimeDate;

	TextView endDateText;
	RequestActivity activity;
	View rootView;

	public RequestSubmitFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_requestsubmit, container,
				false);
		activity = (RequestActivity) getActivity();
		// Get date and display
		startDateText = (TextView) rootView.findViewById(R.id.startDateInfo);
		endDateText = (TextView) rootView.findViewById(R.id.endDateInfo);

		updateData();

		// Submit request button

		Button submitButton = (Button) rootView
				.findViewById(R.id.submitRequestButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Launch AsyncTask
				try {
					requestResponse = new createRequestTask(
							((RequestActivity) RequestSubmitFragment.this
									.getActivity()).getRequest()).execute()
							.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Match

				// Long requestId = Long.valueOf(5113880120393728L);
				Long requestId = Long.valueOf(requestResponse.getId());
				try {
					LunchDate result = new FindMatchTask(requestId).execute(
							getActivity().getApplicationContext()).get();

					String resultText = null;
					System.out.println(result);
					if (result == null) {
						resultText = "Sorry, we could not find a match at this time";
					} else {
						String matchBuddy = result.getRequestB().getUserId();

						resultText = "Congratulations! You have a match with "
								+ matchBuddy + "!";

					}
					int duration = Toast.LENGTH_SHORT;
					Context context = getActivity().getApplicationContext();
					Toast toast = Toast.makeText(context, resultText, duration);
					toast.show();

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

				// Throw a Toast of your match

				// TODO: Move back to HomeActivity selectively after submitting
				// Request
				Intent intent = new Intent(getActivity(), HomeActivity.class);

				startActivity(intent);
			}

		});

		// requestResponse.getId();

		return rootView;
	}

	/*
	 * RequestTabFragment inherited methods
	 */

	public void updateData() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Long startTime = activity.getRequest().getStartTime();
		Long endTime = activity.getRequest().getEndTime();

		if (startTime == null || endTime == null) {
			startTime = 0L;
			endTime = 0L;
		}

		startTimeDate = new Date(startTime * 1000L);
		endTimeDate = new Date(endTime * 1000L);
		String startTimeString = df.format(startTimeDate);
		String endTimeString = df.format(endTimeDate);
		startDateText.setText("Start Date: " + startTimeString);
		endDateText.setText("End Date: " + endTimeString);

		// // Get restaurants and display
		TextView restaurantText = (TextView) rootView
				.findViewById(R.id.listOfRestaurantsInfo);
		List<GoogleLocation> restaurants = activity.getSelectedRestaurants();
		if(restaurants != null){
			for (GoogleLocation restaurant : restaurants) {
				restaurantText.setText(restaurantText.getText() + "\n"
						+ restaurant.getName());
			}
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
	public class createRequestTask extends AsyncTask<Context, Void, Request> {

		private Request request;

		public createRequestTask(Request request) {
			this.request = request;
		}

		protected Request doInBackground(Context... contexts) {

			Request requestResponse = null;
			try {
				requestResponse = EndpointController.getRequestEndpoint()
						.insertRequest(this.request).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return requestResponse;
		}
	}

	/**
	 * FIND A MATCH FOR A REQUEST
	 * 
	 */
	public class FindMatchTask extends AsyncTask<Context, Integer, LunchDate> {

		private Long requestId;

		public FindMatchTask(Long requestId) {
			this.requestId = requestId;
		}

		protected LunchDate doInBackground(Context... contexts) {

			LunchDate lunchDate = null;
			try {

				// Get match for the request
				lunchDate = EndpointController.getRequestController()
						.findMatch(requestId).execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return lunchDate;
		}
	}

}
