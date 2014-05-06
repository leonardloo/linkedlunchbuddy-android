package com.linkedlunchbuddy;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linkedlunchbuddy.userendpoint.model.User;

public class ProfileNameFragment extends DialogFragment {

	private DataHandler dataHandler;
	private EditText firstName;
	private EditText lastName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profilename, container, false);

		firstName = (EditText) rootView.findViewById(R.id.firstNameField);
		lastName = (EditText) rootView.findViewById(R.id.lastNameField);
		// Set text to be core data
		dataHandler = new DataHandler(getActivity().getBaseContext());
		dataHandler.open();
		Cursor cursor = dataHandler.allUsers();
		cursor.moveToFirst();
		firstName.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
		lastName.setText(cursor.getString(3), TextView.BufferType.EDITABLE);
		cursor.close();
		dataHandler.close();
		Button saveButton = (Button) rootView.findViewById(R.id.saveNameButton);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Alert dialog
				new AlertDialog.Builder(getActivity())
				.setTitle("Update name")
				.setMessage("Are you sure you want to change your name?")
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue with update
						dataHandler = new DataHandler(getActivity().getBaseContext());
						dataHandler.open();
						dataHandler.changeName(firstName.getText().toString(), 
								lastName.getText().toString());
						Cursor cursor = dataHandler.allUsers();
						cursor.moveToFirst();
						String userEmail = cursor.getString(1);
						dataHandler.close();
						// retrieve user from backend
						User updateUser = null;
						try {
							updateUser = new getUserTask(userEmail).execute().get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}

						// Update user
						updateUser.setName(firstName.getText().toString() + " " + 
								lastName.getText().toString());
						new updateUserTask(updateUser).execute();

						FragmentManager fragmentManager = getFragmentManager();
						FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
						fragmentTransaction.replace(R.id.home_frame_container, 
								new ProfileFragment(), "Profile Name");
						fragmentTransaction.commit();
					}
				})
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// do nothing
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();

			}
		});

		Button cancelButton = (Button) rootView.findViewById(R.id.cancelNameButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.home_frame_container, 
						new ProfileFragment(), "Profile Name");
				fragmentTransaction.commit();
			}
		});

		return rootView;
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

				updatedUser = EndpointController.getUserEndpoint().updateUser(this.updateUser)
						.execute();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return updatedUser;
		}
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
				user = EndpointController.getUserEndpoint().getUser(this.eduEmail).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return user;
		}
	}
}
