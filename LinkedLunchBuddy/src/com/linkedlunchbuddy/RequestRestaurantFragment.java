package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linkedlunchbuddy.places.GoogleLocationListAdapter;
import com.linkedlunchbuddy.placesapi.GoogleLocation;
import com.linkedlunchbuddy.placesapi.GooglePlacesAPI;

public class RequestRestaurantFragment extends Fragment{

	public static final double DEFAULT_RADIUS = 1000;

	public RequestRestaurantFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_requestrestaurant,
				container, false);
		try {
			// Set this location
			RequestActivity reqActivity = (RequestActivity) getActivity();
			double lat = reqActivity.getLocationLat();
			double lon = reqActivity.getLocationLon();
			new SetupListTask(lat, lon).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return rootView;

	}

	/*
	 * RequestTabFragment inherited methods
	 */

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
