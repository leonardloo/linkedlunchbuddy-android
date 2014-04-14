package com.linkedlunchbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// Blurb is loaded the first time the user logs in
public class BlurbActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blurb);
		TextView blurb = (TextView) findViewById(R.id.blurbText);
		String blurbText = "At the push of a button, we can help you find a LunchBuddy. " +
				"We don't just match you up with anyone - " +
				"Your LunchBuddy will be someone we believe you will have fun with. " +
				"Hungry for food and a good conversation? Push Start! ";
		blurb.setText(blurbText);
		Button startHomeButton = (Button) findViewById(R.id.startHomeButton);
		startHomeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Go to Home Activity
				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
}
