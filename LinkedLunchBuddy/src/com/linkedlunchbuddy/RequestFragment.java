package com.linkedlunchbuddy;

import java.io.IOException;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.linkedlunchbuddy.messageEndpoint.MessageEndpoint;
import com.linkedlunchbuddy.userendpoint.Userendpoint;
import com.linkedlunchbuddy.userendpoint.model.User;

public class RequestFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_request, container,
				false);
		Button sendButton = (Button) rootView.findViewById(R.id.sendButton);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Execute the endpoints task
//				new EndpointsTask1().execute(getActivity()
//						.getApplicationContext());
				
				new EndpointsTask2().execute(getActivity()
						.getApplicationContext());

			}
		});

		return rootView;
	}

	// CREATE A USER WITH THE USERENDPOINT BUILDER
	public class EndpointsTask1 extends AsyncTask<Context, Integer, Long> {
		protected Long doInBackground(Context... contexts) {

			Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			Userendpoint endpoint = CloudEndpointUtils.updateBuilder(
					endpointBuilder).build();
			try {
				User user = new User().setEduEmail("ugh@seas.upenn.edu");
				User result = endpoint.insertUser(user).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}

	// RETRIEVE A USER WITH THE USERENDPOINT BUILDER
	public class EndpointsTask2 extends AsyncTask<Context, Integer, Long> {
		protected Long doInBackground(Context... contexts) {

			Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			Userendpoint endpoint = CloudEndpointUtils.updateBuilder(
					endpointBuilder).build();
			try {
				Long id = 5668600916475904L;
				User user = endpoint.getUser(id).execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}


	// um trying with messageeendpoints
	public class EndpointsTask3 extends AsyncTask<Context, Integer, Long> {
		protected Long doInBackground(Context... contexts) {

			MessageEndpoint.Builder endpointBuilder = new MessageEndpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			MessageEndpoint endpoint = CloudEndpointUtils.updateBuilder(
					endpointBuilder).build();
			try {
				endpoint.sendMessage("Hello!!!");

			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}

	// public class AddUserAsyncTask extends AsyncTask<String, Void, User>{
	// Context context;
	// private ProgressDialog pd;
	//
	// public AddUserAsyncTask(Context context) {
	// this.context = context;
	// }
	//
	// protected void onPreExecute(){
	// super.onPreExecute();
	// pd = new ProgressDialog(context);
	// pd.setMessage("Adding the User...");
	// pd.show();
	// }
	//
	// protected User doInBackground(String... params) {
	// User response = new User();
	// try {
	// Userendpoint.Builder builder = new
	// Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new
	// GsonFactory(), null);
	// Userendpoint service = builder.build();
	// User user = new User();
	//
	// user.setEduEmail("please!ta!");
	// HomeActivity activity = (HomeActivity)
	// RequestFragment.this.getActivity();
	// user.setName(activity.name);
	// // After getting user, store in core data
	// response = service.insertUser(user).execute();
	//
	//
	// System.out.println(response);
	// // Request part has to be worked on - which fields are necessary?
	// Request request = new Request();
	// List<String> prefs = new ArrayList<String>();
	// prefs.add("hey");
	// prefs.add("ho");
	//
	//
	// // Used to keyfactory
	//
	// //com.google.appengine.api.datastore.Key key =
	// KeyFactory.createKey("test", "edu");
	// //request.setKey(key);
	// // request.setLat(50.1);
	// // request.setLon(20.2);
	// request.setUser(response);
	// request.setRestaurantPreferences(prefs);
	// System.out.println(request);
	// Request requestResponse = new Request();
	// Requestendpoint.Builder requestBuilder = new
	// Requestendpoint.Builder(AndroidHttp.newCompatibleTransport(), new
	// GsonFactory(), null);
	// Requestendpoint reqService = requestBuilder.build();
	// requestResponse = reqService.insertRequest(request).execute();
	//
	//
	//
	//
	//
	// System.out.println(response);
	// System.out.println(requestResponse);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// Log.d("Could not Add User", e.getMessage(), e);
	// }
	// return response;
	// }
	//
	//
	// protected void onPostExecute(User user) {
	// //Clear the progress dialog and the fields
	// pd.dismiss();
	//
	// //Display success message to user
	// Toast.makeText(getView().getContext(), "User added succesfully",
	// Toast.LENGTH_SHORT).show();
	// }
	// }

}
