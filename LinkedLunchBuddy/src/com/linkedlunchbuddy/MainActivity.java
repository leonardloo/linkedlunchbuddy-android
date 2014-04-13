package com.linkedlunchbuddy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends FragmentActivity {
	
	private MainFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Start up RegisterActivity right away
		// Temporarily bypass RegisterActivity
//		Intent intent = new Intent(this, RegisterActivity.class);
//		startActivity(intent);
//		 Since this is just a wrapper to start the main activity,
//		 finish it after launching RegisterActivity
//		finish();
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	
	}

}
