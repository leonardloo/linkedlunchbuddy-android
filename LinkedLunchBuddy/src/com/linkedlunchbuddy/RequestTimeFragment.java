package com.linkedlunchbuddy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class RequestTimeFragment extends Fragment {

	private Calendar cal = Calendar.getInstance();
	private TimePickerDialog.OnTimeSetListener stpd, etpd;
	private DatePickerDialog.OnDateSetListener dpd;
	private TextView startTimeLabel, endTimeLabel, dateLabel;

	private RequestActivity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Load the theme
		View rootView = inflater.inflate(R.layout.fragment_requesttime,
				container, false);

		activity = (RequestActivity) getActivity();

		startTimeLabel = (TextView) rootView.findViewById(R.id.startTimeLabel);
		endTimeLabel = (TextView) rootView.findViewById(R.id.endTimeLabel);
		dateLabel = (TextView) rootView.findViewById(R.id.dateLabel);

		int year = activity.getYear();
		int month = activity.getMonth();
		int day = activity.getDay();
		int startHour = activity.getStartHour();
		int startMinute = activity.getStartMinute();
		int endHour = activity.getEndHour();
		int endMinute = activity.getEndMinute();
		// If not set yet
		if (year == -1 && startHour == -1 && endHour == -1) {
			// Pre-fill in labels
			DateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
			Date date = new Date();
			dateLabel.setText("Date: " + dfDate.format(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			activity.setYear(cal.get(Calendar.YEAR));
			activity.setMonth(cal.get(Calendar.MONTH));
			activity.setDay(cal.get(Calendar.DAY_OF_MONTH));
			DateFormat dfTime = new SimpleDateFormat("HH:mm", Locale.US);
			startTimeLabel.setText("Start Time: " + dfTime.format(date));
			activity.setStartHour(cal.get(Calendar.HOUR_OF_DAY));
			activity.setStartMinute(cal.get(Calendar.MINUTE));
			date.setTime(date.getTime() + 1000 * 60 * 60);
			cal.setTime(date);
			activity.setEndHour(cal.get(Calendar.HOUR_OF_DAY));
			activity.setEndMinute(cal.get(Calendar.MINUTE));
			endTimeLabel.setText("End Time: " + dfTime.format(date));
			// Update activity

		}

		if (startHour != -1 && startMinute != -1) {
			if (startMinute < 10) {
				startTimeLabel.setText("Start Time: "
						+ new StringBuilder().append(startHour + ":0").append(
								startMinute));
			} else {
				startTimeLabel.setText("Start Time: "
						+ new StringBuilder().append(startHour + ":").append(
								startMinute));
			}
		}
		if (endHour != -1 && endMinute != -1) {
			if (endMinute < 10) {
				endTimeLabel.setText("End Time: "
						+ new StringBuilder().append(endHour + ":0").append(
								endMinute));
			} else {
				endTimeLabel.setText("End Time: "
						+ new StringBuilder().append(endHour + ":").append(
								endMinute));
			}
		}

		if (year != -1 && month != -1 && day != -1) {
			dateLabel.setText("Date: "
					+ new StringBuilder().append(year + "/")
							.append(month + "/").append(day));
		}

		Button dateButton = (Button) rootView.findViewById(R.id.dateButton);
		dateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(RequestTimeFragment.this.getActivity(),
						dpd, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		Button startTimeButton = (Button) rootView
				.findViewById(R.id.startTimeButton);
		startTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(RequestTimeFragment.this.getActivity(),
						stpd, cal.get(Calendar.HOUR_OF_DAY), cal
								.get(Calendar.MINUTE), false).show();
			}
		});

		Button endTimeButton = (Button) rootView
				.findViewById(R.id.endTimeButton);
		endTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(RequestTimeFragment.this.getActivity(),
						etpd, cal.get(Calendar.HOUR_OF_DAY), cal
								.get(Calendar.MINUTE), false).show();
			}
		});

		dpd = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH, day);

				dateLabel.setText("Date: "
						+ new StringBuilder().append(year + "/")
								.append(month + "/").append(day));

				activity.setYear(year);
				activity.setMonth(month);
				activity.setDay(day);
			}
		};

		stpd = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int hour, int minute) {
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, minute);
				if (minute < 10) {
					startTimeLabel.setText("Start Time: "
							+ new StringBuilder().append(hour + ":0").append(
									minute));
				} else {
					startTimeLabel.setText("Start Time: "
							+ new StringBuilder().append(hour + ":").append(
									minute));
				}

				activity.setStartHour(hour);
				activity.setStartMinute(minute);
			}
		};

		etpd = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int hour, int minute) {
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, minute);
				if (minute < 10) {
					endTimeLabel.setText("End Time: "
							+ new StringBuilder().append(hour + ":0").append(
									minute));
				} else {
					endTimeLabel.setText("End Time: "
							+ new StringBuilder().append(hour + ":").append(
									minute));
				}

				activity.setEndHour(hour);
				activity.setEndMinute(minute);
			}
		};

		return rootView;
	}
	

}
