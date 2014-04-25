package com.linkedlunchbuddy.places;
import com.linkedlunchbuddy.*;
import com.linkedlunchbuddy.placesapi.*;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GoogleLocationListAdapter extends ArrayAdapter<GoogleLocation> {

	private Context context;
	private int layoutResourceId;
	private List<GoogleLocation> data;

	public GoogleLocationListAdapter(Context context, int resource,
			List<GoogleLocation> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View row = inflater.inflate(layoutResourceId, parent, false);
		TextView nameView = (TextView) row.findViewById(R.id.txtLocationName);

		GoogleLocation location = data.get(position);
		nameView.setText(location.getName());

		return row;
	}

}
