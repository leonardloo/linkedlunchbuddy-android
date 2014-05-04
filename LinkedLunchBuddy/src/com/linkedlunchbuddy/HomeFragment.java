package com.linkedlunchbuddy;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment {

	// TODO: After end time of LunchDate, status will revert back to STATUS_DEFAULT 
	//- add an additional field, endtime unix string in LunchDateStatus
	// TODO: After 30min of PENDING_REQUEST, push notification is sent and status will change to STATUS_REQUESTEXPIRED
	// TODO: When push notification is sent any time before 30min, status will change to STATUS_MATCH

	// Google Map
	private GoogleMap googleMap;
	private String gender = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		TextView statusText = (TextView) rootView.findViewById(R.id.statusText);
		// Loads different view based on LunchDateStatus in core data
		DataHandler dataHandler = new DataHandler(getActivity());
		dataHandler.open();
		Cursor cursor = dataHandler.allUsers();
		cursor.moveToFirst();
		gender = cursor.getString(4);
		try {

			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			JSONObject lunchDateObject = new JSONObject(cursor.getString(5));
			LunchDateStatus lunchDateStatus = new LunchDateStatus(lunchDateObject);
			String status = lunchDateStatus.getStatus();

			// Match is found!
			if (status.equals(LunchDateStatus.STATUS_MATCHED)) {
				// The set restaurant will be the first index in the restaurant list
				Map<String, String> restaurantAllocated = lunchDateStatus.getRestaurants().get(0);
				statusText.setText("You've gotten a LunchDate!\n\n" +
						// TODO: To replace with lunch buddy's name after backend is modified
						//"Your LunchBuddy is " + lunchDateStatus.getPartner() + ".\n" + 
						"You may contact your LunchBuddy at " + lunchDateStatus.getPartnerEmail() + ".\n" + 
						"Your restaurant is: " + restaurantAllocated.get("name") + ".");
				LatLng restaurantLocation = new LatLng(Double.parseDouble(restaurantAllocated.get("lat")), 
						Double.parseDouble(restaurantAllocated.get("lng")));
				MarkerOptions marker = new MarkerOptions().position(restaurantLocation)
						.title("LunchDate at " + restaurantAllocated.get("name") + " with " + lunchDateStatus.getPartner())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_starmarker));
				googleMap.addMarker(marker);
				adjustCameraAtPointAndZoom(restaurantLocation, 14);

			} 

			// Request pending
			else if (status.equals(LunchDateStatus.STATUS_PENDING)) {

				statusText.setText("Your Lunchdate request is pending, " +
						"and you will hear back from us in the next 30 minutes!\n\n" +
						"Below are the restaurants you selected, and we will allocate you one of them!");
				// Adds markers of all the restaurants
				List<Map<String, String>> restaurants = lunchDateStatus.getRestaurants();
				double maxLat = -Double.MAX_VALUE;
				double maxLng = -Double.MAX_VALUE;
				double minLat = Double.MAX_VALUE;
				double minLng = Double.MAX_VALUE;

				for (Map<String, String> restaurant : restaurants) {
					Double lat = Double.parseDouble(restaurant.get("lat"));
					Double lng = Double.parseDouble(restaurant.get("lng"));
					if (lat > maxLat) {
						maxLat = lat;
					} 
					if (lat < minLat) {
						minLat = lat;
					}
					if (lng > maxLng) {
						maxLng = lng;
					}
					if (lng < minLng) {
						minLng = lng;
					}
					LatLng restaurantLocation = new LatLng(lat, lng);
					MarkerOptions marker = new MarkerOptions().position(restaurantLocation)
							.title(restaurant.get("name")).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurantmarker));
					googleMap.addMarker(marker);
				}
				// Zoom according to bounding box
				LatLng southWestPoint = new LatLng(minLat, minLng);
				LatLng northEastPoint = new LatLng(maxLat, maxLng);
				// If single point, zoom differently
				if (minLat == maxLat && minLng == maxLng) {
					adjustCameraAtPointAndZoom(southWestPoint, 14);
				} else {
					googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds
							(new LatLngBounds(southWestPoint, northEastPoint), 300, 500, 30));
				}

			} 

			// Either no match found or request not yet sent
			else if (status.equals(LunchDateStatus.STATUS_REQUESTEXPIRED) || 
					status.equals(LunchDateStatus.STATUS_DEFAULT)) {

				// Sets different textviews for both
				if (status.equals(LunchDateStatus.STATUS_REQUESTEXPIRED)) {
					statusText.setText("We apologize for not being able to find you a suitable LunchDate.\n\n" +
							"Please request your LunchDate again.");
				} else if (status.equals(LunchDateStatus.STATUS_DEFAULT)) {
					statusText.setText("You may tap on the map to select your desired lunch area. " +
							"A pin has already been dropped at where you are. " +
							"When you're ready, click " +
							"on the pin and proceed to request your LunchDate!");
				}
				// Sets the same mapview for both
				googleMap.setInfoWindowAdapter(new RequestStartWindowAdapter());
				googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker marker) {
						Intent intent = new Intent(getActivity(), RequestActivity.class);
						intent.putExtra("FROM GOOGLE MAPS", true);
						intent.putExtra("LATITUDE", marker.getPosition().latitude);
						intent.putExtra("LONGITUDE", marker.getPosition().longitude);
						startActivity(intent);		
					}

				});
				// Allow user to move marker
				googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

					@Override
					public void onMapClick(LatLng point) {
						googleMap.clear();
						if (gender.equals("female")) {
							googleMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_femalemarker)));
						} else {
							googleMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_malemarker)));
						}
						adjustCameraAtPointAndZoom(point, 14);
					}
				});
				// Get current location, store, and add a marker
				setCurrentLocation();

				// Set status back to default
				dataHandler.updateLunchDateStatus(LunchDateStatus.STATUS_DEFAULT);

			} 

			// Error with a null status - this is unreachable
			else {
				System.out.println("Status is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



		cursor.close();
		dataHandler.close();
		return rootView;
	}

	private void setCurrentLocation() {
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);

		// Set current location
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		String provider = locationManager.getBestProvider(criteria, true);

		android.location.Location location = locationManager
				.getLastKnownLocation(provider);

		LatLng selectedLocation = new LatLng(39.953229300000000000, -75.194119099999970000);
		//TODO: Uncomment this on an actual phone
		//						new LatLng(location.getLatitude(),
		//						location.getLongitude());

		// create marker
		MarkerOptions marker;
		if (gender.equals("female")) {
			marker = new MarkerOptions().position(selectedLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_femalemarker));
		} else {
			marker = new MarkerOptions().position(selectedLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_malemarker));

		}

		// adding marker
		googleMap.addMarker(marker);

		adjustCameraAtPointAndZoom(selectedLocation, 13);

	}

	private void adjustCameraAtPointAndZoom(LatLng location, int zoomLevel) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(location).zoom(zoomLevel).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	private class RequestStartWindowAdapter implements InfoWindowAdapter {

		private View view;
		HomeActivity activity = (HomeActivity)HomeFragment.this.getActivity();

		public RequestStartWindowAdapter() {
			view = activity.getLayoutInflater().inflate(R.layout.request_start_window,
					null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			return view;
		}

		@Override
		public View getInfoWindow(final Marker marker) {
			return null;
		}
	}

}
