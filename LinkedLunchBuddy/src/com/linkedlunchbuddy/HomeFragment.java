package com.linkedlunchbuddy;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	// Google Map
	private GoogleMap googleMap;
	private LatLng currentLocation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		try {
			// Loading map
			initializeMap();

			LocationManager locationManager = (LocationManager) getActivity()
					.getSystemService(Context.LOCATION_SERVICE);

			// latitude and longitude

			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.NO_REQUIREMENT);
			String provider = locationManager.getBestProvider(criteria, true);

			android.location.Location location = locationManager
					.getLastKnownLocation(provider);

			// set current location
			currentLocation = new LatLng(location.getLatitude(),
					location.getLongitude());

			// create marker
			MarkerOptions marker = new MarkerOptions()
					.position(currentLocation).title("You are here!");

			// adding marker
			googleMap.addMarker(marker);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(currentLocation).zoom(12).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rootView;
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initializeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
