package com.linkedlunchbuddy;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.linkedlunchbuddy.deviceinfoendpoint.Deviceinfoendpoint;
import com.linkedlunchbuddy.deviceinfoendpoint.model.DeviceInfo;
import com.linkedlunchbuddy.userendpoint.model.User;

/**
 * This class is started up as a service of the Android application. It listens
 * for Google Cloud Messaging (GCM) messages directed to this device.
 * 
 * When the device is successfully registered for GCM, a message is sent to the
 * App Engine backend via Cloud Endpoints, indicating that it wants to receive
 * broadcast messages from the it.
 * 
 * Before registering for GCM, you have to create a project in Google's Cloud
 * Console (https://code.google.com/apis/console). In this project, you'll have
 * to enable the "Google Cloud Messaging for Android" Service.
 * 
 * Once you have set up a project and enabled GCM, you'll have to set the
 * PROJECT_NUMBER field to the project number mentioned in the "Overview" page.
 * 
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 */
public class GCMIntentService extends GCMBaseIntentService {
	final Deviceinfoendpoint endpoint;
	static String fbid;
	static String fbfirstname;
	static String fblastname;
	static String fbgender;
	static String email;

	protected static final String PROJECT_NUMBER = "925319909484";

	/**
	 * Register the device for GCM.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void register(Context mContext, String fbid_, String fbfirstname_, String fblastname_, String fbgender_, String email_) {
		fbid = fbid_;
		fbfirstname = fbfirstname_;
		fblastname = fblastname_;
		fbgender = fbgender_;
		email = email_;
		GCMRegistrar.register(mContext, PROJECT_NUMBER);
	}



	/**
	 * Unregister the device from the GCM service.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void unregister(Context mContext) {
		GCMRegistrar.unregister(mContext);
	}

	public GCMIntentService() {
		super(PROJECT_NUMBER);
		Deviceinfoendpoint.Builder endpointBuilder = new Deviceinfoendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}

	/**
	 * Called on registration error. This is called in the context of a Service
	 * - no dialog or UI.
	 * 
	 * @param context
	 *            the Context
	 * @param errorId
	 *            an error message
	 */
	@Override
	public void onError(Context context, String errorId) {
	}

	/**
	 * Called when a cloud message has been received.
	 */
	@Override
	public void onMessage(Context context, Intent intent) {
		// Update Core Data and call HomeFragment
		DataHandler dataHandler = new DataHandler(getApplicationContext());
		dataHandler.open();
		String message = intent.getStringExtra("message");
		// Parse message
		String name = message.split("partnerName=")[1].split(",")[0];
		String email = message.split("partnerEmail=")[1].split(",")[0];
		String timeInLong = message.split("time=")[1].split(Pattern.quote("}"))[0];
		// Store time in long
		String time = "" + (Long.parseLong(timeInLong) * 1000);
		String restaurantName = message.split("name\":\"")[1].split("\"")[0];
		String restaurantLat = message.split("lat\":\"")[1].split("\"")[0];
		String restaurantLon = message.split("lon\":\"")[1].split("\"")[0];
		Map<String, String> restaurant = new HashMap<String, String>();
		restaurant.put("lat", restaurantLat);
		restaurant.put("lng", restaurantLon);
		restaurant.put("name", restaurantName);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(restaurant);
		dataHandler.updateLunchDateStatus(new LunchDateStatus(
				LunchDateStatus.STATUS_MATCHED, name, email, list, time, "").
				toJSON().toString());

		dataHandler.close();

		Intent homeIntent = new Intent(this, MainActivity.class);
		homeIntent.putExtra("fromPushNotification", true);
		PendingIntent pIntent = PendingIntent.getActivity
				(this, 0, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Build notification
		Notification notification = new Notification.Builder(this)
		.setContentTitle("You have a LunchBuddy: " + name)
		.setContentText("You have been matched with " + 
				name + " at " + list.get(0)  + " at " + time)
				.setSmallIcon(R.drawable.ic_starmarker)
				.setContentIntent(pIntent).build();
		NotificationManager notificationManager = 
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, notification);

	}


	/**
	 * Called back when a registration token has been received from the Google
	 * Cloud Messaging service.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	public void onRegistered(Context context, String registration) {
		boolean alreadyRegisteredWithEndpointServer = false;
		try {
			// Insert core data
			DataHandler dataHandler = new DataHandler(getBaseContext());
			dataHandler.open();
			List<Map<String, String>> restaurants = new ArrayList<Map<String, String>>();
			Map<String, String> restaurant = new HashMap<String, String>();
			restaurant.put("lat", "0");
			restaurant.put("lng", "0");
			restaurant.put("name", "dummy");

			dataHandler.insertUser(fbid, email, fbfirstname,
					fblastname, fbgender, (new LunchDateStatus(
							LunchDateStatus.STATUS_DEFAULT, "name", "email", 
							restaurants, "100", "100")).
							toJSON().toString());
			dataHandler.close();
			// Insert to backend
			User createUser = new User().setEduEmail(email)
					.setName(fbfirstname + " " + fblastname)
					.setGender(fbgender).setFbId(fbid).setDeviceRegistrationId(registration);

			// Async task endpoint
			new CreateUserTask(createUser).execute();

			/*
			 * Using cloud endpoints, see if the device has already been
			 * registered with the backend
			 */
			DeviceInfo existingInfo = endpoint.getDeviceInfo(registration)
					.execute();

			if (existingInfo != null && registration.equals(existingInfo.getDeviceRegistrationID())) {
				alreadyRegisteredWithEndpointServer = true;
			}
		} catch (IOException e) {
			// Ignore
		}

		try {
			if (!alreadyRegisteredWithEndpointServer) {
				/*
				 * We are not registered as yet. Send an endpoint message
				 * containing the GCM registration id and some of the device's
				 * product information over to the backend. Then, we'll be
				 * registered.
				 */
				DeviceInfo deviceInfo = new DeviceInfo();
				endpoint.insertDeviceInfo(
						deviceInfo
						.setDeviceRegistrationID(registration)
						.setTimestamp(System.currentTimeMillis())
						.setDeviceInformation(
								URLEncoder
								.encode(android.os.Build.MANUFACTURER
										+ " "
										+ android.os.Build.PRODUCT,
										"UTF-8"))).execute();

			}
		} catch (IOException e) {
			Log.e(GCMIntentService.class.getName(),
					"Exception received when attempting to register with server at "
							+ endpoint.getRootUrl(), e);
		}
	}

	/**
	 * Called back when the Google Cloud Messaging service has unregistered the
	 * device.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

		if (registrationId != null && registrationId.length() > 0) {

			try {
				endpoint.removeDeviceInfo(registrationId).execute();
			} catch (IOException e) {
				Log.e(GCMIntentService.class.getName(),
						"Exception received when attempting to unregister with server at "
								+ endpoint.getRootUrl(), e);

				return;
			}
		}


	}


	/**
	 * CREATE A USER WITH THE USERENDPOINT BUILDER
	 * 
	 * @author blotter
	 * @param User
	 *            Object to be persisted in updated form
	 * @return Updated User Object
	 */
	public class CreateUserTask extends AsyncTask<Context, Void, User> {

		private User createUser;

		public CreateUserTask(User user) {
			this.createUser = user;
		}

		protected User doInBackground(Context... contexts) {

			User createdUser = null;
			try {

				createdUser = EndpointController.getUserEndpoint()
						.insertUser(this.createUser).execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return createdUser;
		}
	}

}
