package com.linkedlunchbuddy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class ProfileFragment extends ListFragment {

	private DataHandler dataHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
		// Define other fields too
		String[] listItems = {"Email", "Name", "Gender"};
		boolean[] listImages = {false, true, false};
		String emailValue = "";
		String nameValue = "";
		String genderValue = "";
		dataHandler = new DataHandler(getActivity().getBaseContext());
		dataHandler.open();
		Cursor cursor = dataHandler.allUsers();
		cursor.moveToFirst();
		emailValue = cursor.getString(1);
		nameValue = cursor.getString(2) + " " + cursor.getString(3);
		genderValue = cursor.getString(4);


		String[] listItemValue = {emailValue, nameValue, genderValue};
		dataHandler.close();
		setListAdapter(new ProfileListViewAdapter(getActivity(), R.layout.fragment_profile, R.id.profileTitle, R.id.image1, listItems, listItemValue, listImages ));


		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Link to the respective fragments
		if (position == 1) {
			v.setBackgroundColor(getResources().getColor(android.R.color.tab_indicator_text));
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.home_frame_container, new ProfileNameFragment(), "Profile Name");
				fragmentTransaction.commit();

		}
	}



}
