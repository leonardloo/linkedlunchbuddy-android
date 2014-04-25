package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.linkedlunchbuddy.places.GoogleLocationListAdapter;
import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.placesapi.GooglePlacesAPI;

public class RequestRestaurantFragment extends Fragment {

	public static final double DEFAULT_RADIUS = 1000;

	private ArrayList<GoogleLocation> locations;
	private TextView header;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_requestrestaurant,
				container, false);
		RequestActivity activity = (RequestActivity) getActivity();
		// Get Location
		LocationManager locationManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		String provider = locationManager.getBestProvider(criteria, true);
		android.location.Location location = locationManager
				.getLastKnownLocation(provider);

		try {
			// Set this location
			RequestActivity reqActivity = (RequestActivity) getActivity();
			double lat = reqActivity.getLocationLat();
			double lon = reqActivity.getLocationLon();
			// 39.95, -75.17
			this.locations= new SetupListTask(lat, lon).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		Button confirmButton = (Button) rootView
				.findViewById(R.id.doneWithRequestRestaurantButton);
		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				List<GoogleLocation> selectedRestaurants = new ArrayList<GoogleLocation>();
				for (GoogleLocation loc : locations) {
					if (loc.isSelected()) {
						selectedRestaurants.add(loc);
					}
				}
				// SelectedRestaurants only contains the GooglePlace objects
				// selected by user
				RequestActivity activity = (RequestActivity) RequestRestaurantFragment.this.getActivity();
				activity.setSelectedRestaurants(selectedRestaurants);
				
				// Move over to RequestSubmitFragment
				/*
				FragmentManager fragmentManager = RequestRestaurantFragment.this.getActivity().getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.request_frame_container, new RequestSubmitFragment(), "Request Submit");
				fragmentTransaction.commit();
				*/
			}
		});
		return rootView;

	}

	class SetupListTask extends
			AsyncTask<Void, Void, ArrayList<GoogleLocation>> {
		private double latitude, longitude;

		public SetupListTask(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		@Override
		protected ArrayList<GoogleLocation> doInBackground(Void... params) {

			ArrayList<GoogleLocation> googleLocations = new ArrayList<GoogleLocation>();

			googleLocations = GooglePlacesAPI.getRestaurants(this.latitude,
					this.longitude, DEFAULT_RADIUS);

			return googleLocations;
		};

		@Override
		protected void onPostExecute(ArrayList<GoogleLocation> googleLocations) {

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
					boolean selectedState = location.toggleSelected();
					// TODO: Toggle listview item view to set and not set booleans
					view.setActivated(true);
					// Assemble List of Restaurant IDs
					// TODO: Figure out how to highlight listView cell
					// background upon selection
					//view.setBackgroundColor(getResources().getColor(R.color.gray));
					
					/*parent.getChildAt(position).setBackgroundColor(
							getResources().getColor(R.color.gray));*/
				}

			});
		};
	}

}
