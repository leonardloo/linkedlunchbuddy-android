package com.linkedlunchbuddy;


import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class RequestTimeFragment extends Fragment {

	private Calendar cal = Calendar.getInstance();
	private DateFormat dt = new DateFormat();
	private TimePickerDialog.OnTimeSetListener stpd, etpd;
	private DatePickerDialog.OnDateSetListener dpd;
	private TextView startTimeLabel, endTimeLabel, dateLabel;
	private int year = -1;
	private int month = -1;
	private int day = -1;
	private int startHour = -1;
	private int startMinute = -1;
	private int endHour = -1;
	private int endMinute = -1;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_requesttime, container,
				false);
		startTimeLabel = (TextView) rootView.findViewById(R.id.startTimeLabel);
		endTimeLabel = (TextView) rootView.findViewById(R.id.endTimeLabel);
		dateLabel = (TextView) rootView.findViewById(R.id.dateLabel);
		
		Button dateButton = (Button) rootView.findViewById(R.id.dateButton);
		dateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(RequestTimeFragment.this.getActivity(),dpd,
						cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		
		Button startTimeButton = (Button) rootView.findViewById(R.id.startTimeButton);
		startTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(RequestTimeFragment.this.getActivity(),stpd,
						cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),
						false).show();
			}
		});
		
		Button endTimeButton = (Button) rootView.findViewById(R.id.endTimeButton);
		endTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(RequestTimeFragment.this.getActivity(),etpd,
						cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),
						false).show();
			}
		});

		dpd = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int month,
					int day) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH, day);

				dateLabel.setText("Date: " + new StringBuilder().append(year+"/")
						.append(month+"/").append(day));
				RequestTimeFragment.this.year = year;
				RequestTimeFragment.this.month = month;
				RequestTimeFragment.this.day = day;
			}
		};

		stpd = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int hour, int minute) {
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, minute);
				if (minute < 10) {
				startTimeLabel.setText("Start Time: " + new StringBuilder().append(hour+":0")
						.append(minute));
				} else {
					startTimeLabel.setText("Start Time: " + new StringBuilder().append(hour+":")
							.append(minute));
				}
				RequestTimeFragment.this.startHour = hour;
				RequestTimeFragment.this.startMinute = minute;
			}
		};
		
		etpd = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int hour, int minute) {
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, minute);
				if (minute < 10) {
				endTimeLabel.setText("End Time: " + new StringBuilder().append(hour+":0")
						.append(minute));
				} else {
					endTimeLabel.setText("End Time: " + new StringBuilder().append(hour+":")
							.append(minute));
				}
				RequestTimeFragment.this.endHour = hour;
				RequestTimeFragment.this.endMinute = minute;
			}
		};

		// Check for all fields to be initiated
		Button doneButton = (Button) rootView.findViewById(R.id.doneWithRequestTimeButton);
		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println(RequestTimeFragment.this.startHour);
				System.out.println(RequestTimeFragment.this.startMinute);
				System.out.println(RequestTimeFragment.this.endHour);
				System.out.println(RequestTimeFragment.this.endMinute);
				if (RequestTimeFragment.this.year == -1 ||
						RequestTimeFragment.this.month == -1 ||
						RequestTimeFragment.this.day == -1 ||
						RequestTimeFragment.this.startHour == -1 ||
						RequestTimeFragment.this.startMinute == -1 || 
						RequestTimeFragment.this.endHour == -1 ||
						RequestTimeFragment.this.endMinute == -1) {
					Toast.makeText(getActivity().getApplicationContext(), "Please choose date, start time and end time", Toast.LENGTH_SHORT).show();
				} else {
					// Convert to UNIX time and set it in Activity
					RequestActivity activity = (RequestActivity) getActivity();

					long unixStartTime = unixTime(RequestTimeFragment.this.year, 
							RequestTimeFragment.this.month,
							RequestTimeFragment.this.day,
							RequestTimeFragment.this.startHour,
							RequestTimeFragment.this.startMinute);
					long unixEndTime = unixTime(RequestTimeFragment.this.year, 
							RequestTimeFragment.this.month,
							RequestTimeFragment.this.day,
							RequestTimeFragment.this.endHour,
							RequestTimeFragment.this.endMinute);
					activity.setTime(unixStartTime, unixEndTime);
					// Move over to RequestRestaurantFragment
					FragmentManager fragmentManager = RequestTimeFragment.this.getActivity().getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.request_frame_container, new RequestRestaurantFragment(), "Request Restaurant");
					fragmentTransaction.commit();
				}
			}
		});


		return rootView;
	}

	// Helper method to convert to UNIX time
	private long unixTime(int year, int month, int day, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis()/1000L);
	}

}
