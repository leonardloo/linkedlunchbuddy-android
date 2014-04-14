package com.linkedlunchbuddy;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.linkedlunchbuddy.userendpoint.Userendpoint;
import com.linkedlunchbuddy.userendpoint.model.User;

/**
 *  Class that accesses the User endpoints and provides methods for accessing and updating user
 *  information
 */
public class UserController {
	
	private static User lastUser;

	public static User getLastUser() {
		return lastUser;
	}
	
	// CreateUserTask class called by createUser(...). Takes in parameters of user
	public static class CreateUserTask extends AsyncTask<Context, Integer, Long> {
		
		private String eduEmail = "";
		private String name = "";
		private String fbId = "";
		private String gender = "";
		
		private CreateUserTask(String eduEmail, String name, String fbId, String gender) {
			this.eduEmail = eduEmail;
			this.name = name;
			this.fbId = fbId;
			this.gender = gender;
		}
		
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
				User user = new User();
				user.setEncodedKey(eduEmail);
				user.setName(name);
				user.setFbId(fbId);
				user.setGender(gender);
				User result = endpoint.insertUser(user).execute();
				lastUser = result;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}
	
	// GetUserById class that gets called by getUserById(String eduEmail). returns User.
	public static class GetUserByIdTask extends AsyncTask<Context, Integer, Long> {
		
		private String eduEmail = "";
		private User user;
		
		private GetUserByIdTask(String eduEmail) {
			this.eduEmail = eduEmail;
		}
	
		
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
				User user = endpoint.getUser(eduEmail).execute();
				lastUser = user;
				System.out.println("last user set");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}
	
	// create
	public static User createUser(Context context, String eduEmail, String name, String fbId, String gender) {
		CreateUserTask createUserTask = new CreateUserTask(eduEmail, name, fbId, gender);
		createUserTask.execute(context);
		return lastUser; // TODO: change this eventually
	}
	
	// getById
	public static User getUserById(Context context, String eduEmail) {
		GetUserByIdTask getUserByIdTask = new GetUserByIdTask(eduEmail);
		getUserByIdTask.execute(context);
		return lastUser;
	}
	
	// update
}
