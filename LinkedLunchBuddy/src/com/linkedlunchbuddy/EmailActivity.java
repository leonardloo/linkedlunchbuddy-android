package com.linkedlunchbuddy;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends Activity {
	
	private static final String TAG = "EmailActivity";
	private UiLifecycleHelper uiHelper;
	private Session session = Session.getActiveSession();
	private DataHandler dataHandler;
	private EditText emailField;
	private String fbid;
	private String fbfirstname;
	private String fblastname;
	private String fbgender;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
		emailField = (EditText) findViewById(R.id.emailField);
		Button button = (Button) findViewById(R.id.emailEnterButton);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String emailString = emailField.getText().toString();
				if (emailString.length() < 4 || !emailString.substring(emailString.length()-3, emailString.length()).equals("edu")) {
					// Generate error Toast
					Toast.makeText(getBaseContext(), "Please provide a valid .edu email", Toast.LENGTH_SHORT).show();
				} else {
					dataHandler = new DataHandler(getBaseContext());
					dataHandler.open();
					dataHandler.insertUser(fbid, emailString, fbfirstname, fblastname, fbgender);
					Intent intent = new Intent(getApplicationContext(), BlurbActivity.class);
					dataHandler.close();
					startActivity(intent);

				}
			}
		});
	}
	
	/** Facebook session **/
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		// Harmless bug: Callback fires twice
		if (state.isOpened()) {
			Log.i(TAG, "User is logged in");
			new Request(
					session,
					"/me",
					null,
					HttpMethod.GET,
					new Request.Callback() {
						public void onCompleted(Response response) {
							fbid = (String) response.getGraphObject().getProperty("id");
							fbfirstname = (String) response.getGraphObject().getProperty("first_name");
							fblastname = (String) response.getGraphObject().getProperty("last_name");
							fbgender = (String) response.getGraphObject().getProperty("gender");
						}
					}
					).executeAsync();
	
		} else if (state.isClosed()) {
			Log.i(TAG, "User is logged out");
			// This will be unreachable but leave this here
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	}



	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		session.addCallback(callback);
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
