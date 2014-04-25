package com.linkedlunchbuddy;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment {

	// Google Map
	private GoogleMap googleMap;
	private LatLng selectedLocation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		try {
			// Loading map
			initializeMap();

			// Get current location, store, and add a marker
			setCurrentLocation();

			// Allow user to move marker
			googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

				@Override
				public void onMapClick(LatLng point) {
					selectedLocation = point;
					googleMap.clear();
					googleMap.addMarker(new MarkerOptions().position(point));
					adjustCamera();
				}
			});
			

			
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
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
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

		selectedLocation = new LatLng(location.getLatitude(),
				location.getLongitude());

		// create marker
		MarkerOptions marker = new MarkerOptions().position(selectedLocation)
				.title("You are here!");

		// adding marker
		googleMap.addMarker(marker);
		
		adjustCamera();

	}

	private void adjustCamera() {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(selectedLocation).zoom(12).build();

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
