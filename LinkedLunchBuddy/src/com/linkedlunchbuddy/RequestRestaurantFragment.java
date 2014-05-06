package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.linkedlunchbuddy.places.GoogleLocationListAdapter;
import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.placesapi.GooglePlacesAPI;

public class RequestRestaurantFragment extends Fragment{

	public static final double DEFAULT_RADIUS = 1000;
	private Spinner cuisineSpinner;
	private String cuisine;
	private double lat;
	private double lon;
	boolean isFirstTime = true;
	
	public RequestRestaurantFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get location
		RequestActivity reqActivity = (RequestActivity) getActivity();
		lat = reqActivity.getLocationLat();
		lon = reqActivity.getLocationLon();

		View rootView = inflater.inflate(R.layout.fragment_requestrestaurant,
				container, false);
		cuisine = "All";
		// Spinner
		cuisineSpinner = (Spinner) rootView.findViewById(R.id.cuisineSpinner);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.cuisine_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		cuisineSpinner.setAdapter(adapter);
		cuisineSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Call URL and update listview
				cuisine  = parent.getItemAtPosition(position).toString();
				if (cuisine.equals("All")) {
					cuisine = "Restaurant";
				}
				RequestActivity reqActivity = (RequestActivity) RequestRestaurantFragment.this
						.getActivity();
				reqActivity.clearAllRestaurants();
				new SetupListTask(lat, lon).execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		// Only launch this when user is at this tab
			//new SetupListTask(lat, lon, false).execute();




		return rootView;

	}



	/*
	 * RequestTabFragment inherited methods
	 */

	class SetupListTask extends
	AsyncTask<Void, Void, ArrayList<GoogleLocation>> {
		private double latitude, longitude;
		private ProgressDialog progressDialog;
		
		public SetupListTask(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		@Override
		protected void onPreExecute() {
			if (!isFirstTime) {
				progressDialog = new ProgressDialog(RequestRestaurantFragment.this.getActivity());
				progressDialog.setMessage("Refreshing restaurants, please wait...");
				progressDialog.show();
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setCancelable(false);
			}
		}

		@Override
		protected ArrayList<GoogleLocation> doInBackground(Void... params) {

			ArrayList<GoogleLocation> googleLocations = new ArrayList<GoogleLocation>();

			googleLocations = GooglePlacesAPI.getRestaurants(this.latitude,
					this.longitude, DEFAULT_RADIUS, RequestRestaurantFragment.this.cuisine);

			return googleLocations;
		};

		@Override
		protected void onPostExecute(ArrayList<GoogleLocation> googleLocations) {
			if (!isFirstTime) {
				progressDialog.dismiss();
			}
			isFirstTime = false;
			GoogleLocationListAdapter adapter = new GoogleLocationListAdapter(
					getActivity(), R.layout.google_location_list_item,
					googleLocations);
	
			final ListView listView = (ListView) getActivity().findViewById(
					R.id.listNearbyPlaces);

			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					GoogleLocation location = (GoogleLocation) listView
							.getAdapter().getItem(position);
					location.toggleSelected();
					RequestActivity reqActivity = (RequestActivity) RequestRestaurantFragment.this
							.getActivity();
					reqActivity.modifySelectedRestaurant(location);
					if (location.isSelected()) {
						view.setActivated(true);
					} else {
						view.setActivated(false);
					}
				}

			});
		};
	}

}
