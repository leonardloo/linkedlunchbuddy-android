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
import com.linkedlunchbuddy.requestendpoint.Requestendpoint;
import com.linkedlunchbuddy.requestendpoint.model.Request;
import com.linkedlunchbuddy.userendpoint.Userendpoint;
import com.linkedlunchbuddy.userendpoint.model.User;

public class RequestFragment extends Fragment {
	
	private DataHandler dataHandler = new DataHandler(getActivity().getBaseContext());
	private String userEmail = "NAH";

	// TODO: make singletons
	private Requestendpoint requestEndpoint;
	private Userendpoint userEndpoint;

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
				//TODO: User parameter needs to come from core data
				new getRequestFromUserTask(new User()).execute(getActivity()
						.getApplicationContext());

			}
		});

		// Instantiate request endpoints and user endpoints
		Requestendpoint.Builder requestEndpointBuilder = new Requestendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		this.requestEndpoint = CloudEndpointUtils.updateBuilder(
				requestEndpointBuilder).build();

		Userendpoint.Builder userEndpointBuilder = new Userendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		this.userEndpoint = CloudEndpointUtils.updateBuilder(
				userEndpointBuilder).build();

		return rootView;
	}

	/**
	 * RETRIEVE A USER WITH THE USERENDPOINT BUILDER
	 * 
	 * @author blotter
	 * @param User
	 *            Edu Email
	 * @return User Object
	 */
	public class getUserTask extends AsyncTask<Context, Void, User> {

		private String eduEmail;

		public getUserTask(String userEmail) {

			this.eduEmail = userEmail;
		}

		protected User doInBackground(Context... contexts) {

			User user = null;
			try {
				user = userEndpoint.getUser(this.eduEmail).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return user;
		}
	}

	/**
	 * UPDATE A USER WITH THE USERENDPOINT BUILDER
	 * 
	 * @author blotter
	 * @param User
	 *            Object to be persisted in updated form
	 * @return Updated User Object
	 */
	public class updateUserTask extends AsyncTask<Context, Void, User> {

		private User updateUser;

		public updateUserTask(User user) {
			this.updateUser = user;
		}

		protected User doInBackground(Context... contexts) {

			User updatedUser = null;
			try {
				// TODO: retrieve user from coredata
				updatedUser = userEndpoint.updateUser(this.updateUser)
						.execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return updatedUser;
		}
	}

	/**
	 * DELETE A USER WITH THE USERENDPOINT BUILDER
	 * 
	 * @author blotter
	 * @param Edu
	 *            Email of user to be deleted
	 */
	public class deleteUserTask extends AsyncTask<Context, Integer, Long> {

		private String eduEmail;

		public deleteUserTask(String userEmail) {
			this.eduEmail = userEmail;
		}

		protected Long doInBackground(Context... contexts) {

			try {
				userEndpoint.removeUser(this.eduEmail).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}

	/**
	 * CREATE A REQUEST WITH THE REQUESTENDPOINT BUILDER
	 * 
	 * @author blotter
	 * @param Edu
	 *            Email of user creating the request
	 * @return created Request Object
	 */
	public class createRequestTask extends AsyncTask<Context, Void, Request> {

		private String eduEmail;

		public createRequestTask(String userEmail) {
			this.eduEmail = userEmail;
		}

		protected Request doInBackground(Context... contexts) {

			Request request = null;
			try {
				request = requestEndpoint.insertRequest(
						new Request().setUserId(this.eduEmail)).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return request;
		}
	}

	/**
	 * RETRIEVE A USER FROM A REQUEST
	 * 
	 * @author blotter
	 * @param Request
	 * @return User object belonging to request
	 */
	public class getUserFromRequestTask extends
			AsyncTask<Context, Integer, User> {

		private Request request;

		public getUserFromRequestTask(Request request) {
			this.request = request;
		}

		protected User doInBackground(Context... contexts) {

			User user = null;
			try {
				String userId = request.getUserId();
				user = userEndpoint.getUser(userId).execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return user;
		}
	}

	/**
	 * RETRIEVE A REQUEST FROM A USER
	 * 
	 * @author blotter
	 * @param User
	 *            object, whose associated request should be retrieved
	 */
	public class getRequestFromUserTask extends
			AsyncTask<Context, Integer, Request> {

		private User user;

		public getRequestFromUserTask(User user) {
			this.user = user;
		}

		protected Request doInBackground(Context... contexts) {

			Request request = null;
			try {

				Long requestId = user.getRequestId();
				request = requestEndpoint.getRequest(requestId).execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return request;
		}
	}

}
