package com.linkedlunchbuddy;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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

	/*
	 * TODO: Set this to a valid project number. See
	 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
	 * information.
	 */
	protected static final String PROJECT_NUMBER = "925319909484";

	/**
	 * Register the device for GCM.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void register(Context mContext, String fbid_, String fbfirstname_, String fblastname_, String fbgender_, String email_) {
//		 GCMRegistrar.checkDevice(mContext);
//		 GCMRegistrar.checkManifest(mContext);
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
		/*
    sendNotificationIntent(
        context,
        "Registration with Google Cloud Messaging...FAILED!\n\n"
            + "A Google Cloud Messaging registration error occurred (errorid: "
            + errorId
            + "). "
            + "Do you have your project number ("
            + ("".equals(PROJECT_NUMBER) ? "<unset>"
                : PROJECT_NUMBER)
            + ")  set correctly, and do you have Google Cloud Messaging enabled for the "
            + "project?", true, true);
		 */
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
		System.out.println(message);
		// Parse message
		String name = message.split("partnerName=")[1].split(",")[0];
		String email = message.split("partnerEmail=")[1].split(",")[0];
		// Build time from long unix
		String timeInLong = message.split("time=")[1].split(",")[0];
		Date date = new Date(Long.parseLong(timeInLong));
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		String time = df.format(date);
		
		Map<String, String> restaurant = new HashMap<String, String>();
		restaurant.put("lat", "39");
		restaurant.put("lng","-75");
		restaurant.put("name","yo");
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(restaurant);
		dataHandler.updateLunchDateStatus(new LunchDateStatus(
					LunchDateStatus.STATUS_MATCHED, name, email, list).
					toJSON().toString());
		
		dataHandler.close();
		
		 Intent homeIntent = new Intent(this, HomeActivity.class);
		    PendingIntent pIntent = PendingIntent.getActivity(this, 0, homeIntent, 0);

		    // Build notification
		    // Actions are just fake
		    Notification notification = new Notification.Builder(this)
		        .setContentTitle("You have a LunchBuddy: " + name)
		        .setContentText("You have been matched with " + name + " at " + list.get(0)  + " at " + time)
		        .setContentIntent(pIntent).build();
		    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
							LunchDateStatus.STATUS_DEFAULT, "name", "email", restaurants)).
							toJSON().toString());
			System.out.println(new LunchDateStatus(
					LunchDateStatus.STATUS_DEFAULT, "name", "email", restaurants).
					toJSON().toString());
			dataHandler.close();
			// Insert to backend
			User createUser = new User().setEduEmail(email)
					.setName(fbfirstname + " " + fblastname)
					.setGender(fbgender).setFbId(fbid).setDeviceRegistrationId(registration);

			// Async task endpoint
			try {
				User createdUser = new CreateUserTask(createUser)
						.execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
				// Create user and insert user here



			}
		} catch (IOException e) {
			Log.e(GCMIntentService.class.getName(),
					"Exception received when attempting to register with server at "
							+ endpoint.getRootUrl(), e);
		}
		/*
      sendNotificationIntent(
          context,
          "1) Registration with Google Cloud Messaging...SUCCEEDED!\n\n"
              + "2) Registration with Endpoints Server...FAILED!\n\n"
              + "Unable to register your device with your Cloud Endpoints server running at "
              + endpoint.getRootUrl()
              + ". Either your Cloud Endpoints server is not deployed to App Engine, or "
              + "your settings need to be changed to run against a local instance "
              + "by setting LOCAL_ANDROID_RUN to 'true' in CloudEndpointUtils.java.",
          true, true);
      return;
    }

    sendNotificationIntent(
        context,
        "1) Registration with Google Cloud Messaging...SUCCEEDED!\n\n"
            + "2) Registration with Endpoints Server...SUCCEEDED!\n\n"
            + "Device registration with Cloud Endpoints Server running at  "
            + endpoint.getRootUrl()
            + " succeeded!\n\n"
            + "To send a message to this device, "
            + "open your browser and navigate to the sample application at "
            + getWebSampleUrl(endpoint.getRootUrl()), false, true);
		 */
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
				sendNotificationIntent(
						context,
						"1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n"
								+ "2) De-registration with Endpoints Server...FAILED!\n\n"
								+ "We were unable to de-register your device from your Cloud "
								+ "Endpoints server running at "
								+ endpoint.getRootUrl() + "."
								+ "See your Android log for more information.",
								true, true);
				return;
			}
		}

		sendNotificationIntent(
				context,
				"1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n"
						+ "2) De-registration with Endpoints Server...SUCCEEDED!\n\n"
						+ "Device de-registration with Cloud Endpoints server running at  "
						+ endpoint.getRootUrl() + " succeeded!", false, true);
	}

	/**
	 * Generate a notification intent and dispatch it to the RegisterActivity.
	 * This is how we get information from this service (non-UI) back to the
	 * activity.
	 * 
	 * For this to work, the 'android:launchMode="singleTop"' attribute needs to
	 * be set for the RegisterActivity in AndroidManifest.xml.
	 * 
	 * @param context
	 *            the application context
	 * @param message
	 *            the message to send
	 * @param isError
	 *            true if the message is an error-related message; false
	 *            otherwise
	 * @param isRegistrationMessage
	 *            true if this message is related to registration/unregistration
	 */
	private void sendNotificationIntent(Context context, String message,
			boolean isError, boolean isRegistrationMessage) {
		Intent notificationIntent = new Intent(context, RegisterActivity.class);
		notificationIntent.putExtra("gcmIntentServiceMessage", true);
		notificationIntent.putExtra("registrationMessage",
				isRegistrationMessage);
		notificationIntent.putExtra("error", isError);
		notificationIntent.putExtra("message", message);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(notificationIntent);
	}

	private String getWebSampleUrl(String endpointUrl) {
		// Not the most elegant solution; we'll improve this in the future
		if (CloudEndpointUtils.LOCAL_ANDROID_RUN) {
			return CloudEndpointUtils.LOCAL_APP_ENGINE_SERVER_URL
					+ "index.html";
		}
		return endpointUrl.replace("/_ah/api/", "/index.html");
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
