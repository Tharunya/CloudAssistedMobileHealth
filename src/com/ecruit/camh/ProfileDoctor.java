package com.ecruit.camh;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Doctors;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ProfileDoctor extends Activity {
	public static final String TAG = "com.ecruit.hospital.ProfileDoctor";

	// Required Objects
	DbHandler db;
	Doctors doctor;
	String docName;

	// Layout Contents
	TextView mDocNameView;
	EditText mDocPhoneView;
	EditText mDocEmailView;
	EditText mDocAddressView;
	Spinner mDocBranch;
	Button mClear;
	Button update;

	boolean isUpdate = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// getting doctor's name from Start activity
		docName = getIntent().getExtras().getString("docName");

		Log.d(TAG, "On Create method fired up " + docName);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_doctor);
		// Show the Up button in the action bar.
		setupActionBar();
		// Initializing all objects and variables
		initialize();
		mClear.setOnClickListener(buttonClick);
		update.setOnClickListener(buttonClick);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed, Doc Profile Activity");
		super.onBackPressed();
		// Sending doctor name to Parent Activity
		Intent intent = NavUtils.getParentActivityIntent(ProfileDoctor.this);
		intent.putExtra("docName", docName);
		NavUtils.navigateUpTo(this, intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Log.d(TAG, "Home clicked, Doctor Info Activity");
			Intent upIntent = NavUtils
					.getParentActivityIntent(ProfileDoctor.this);
			upIntent.putExtra("docName", docName);
			NavUtils.navigateUpTo(this, upIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initialize() {
		// fire up Database object
		db = new DbHandler(getApplicationContext());
		doctor = new Doctors();
		doctor = db.getDoctor(docName);

		// fire up layout elements
		mDocNameView = (TextView) findViewById(R.id.doc_Name);
		mDocNameView.setText(docName);
		mDocPhoneView = (EditText) findViewById(R.id.doc_phone);
		mDocEmailView = (EditText) findViewById(R.id.doc_email);
		mDocAddressView = (EditText) findViewById(R.id.doc_address);
		mDocBranch = (Spinner) findViewById(R.id.doc_branch);
		mClear = (Button) findViewById(R.id.clearCon);
		update = (Button) findViewById(R.id.update);

		if (doctor.getDoc_name().equals(docName)) {
			isUpdate = true;
			mDocNameView.setText(doctor.getDoc_name());
			mDocBranch.setSelection(getIndex(mDocBranch, doctor.getDoc_branch()
					.trim()));
			mDocPhoneView.setText(doctor.getDoc_phone());
			mDocEmailView.setText(doctor.getDoc_email());
			mDocAddressView.setText(doctor.getDoc_address());
		} else {
			isUpdate = false;
		}
	}

	private int getIndex(Spinner spinner, String value) {
		int index = 0;
		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
				index = i;
			}
		}
		return index;
	}

	private OnClickListener buttonClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			// login Button action
			case R.id.update:
				if (validate() && isUpdate) {
					db.updateDoctor(doctor);
					Toast.makeText(getApplicationContext(),
							"Details updated successfully", Toast.LENGTH_LONG)
							.show();
				} else if (validate() && !isUpdate) {
					db.addDoctor(doctor);
					Toast.makeText(getApplicationContext(),
							"Details Added successfully", Toast.LENGTH_LONG)
							.show();
				}
				break;

			// Forgot button action
			case R.id.clearCon:
				clearContents();
				break;
			}
		}
	};

	protected void clearContents() {
		mDocBranch.setSelection(0);
		mDocPhoneView.setText("");
		mDocEmailView.setText("");
		mDocAddressView.setText("");
	}

	protected boolean validate() {

		boolean success = true;

		// Reset Errors
		mDocPhoneView.setError(null);
		mDocEmailView.setError(null);
		mDocAddressView.setError(null);

		// Get Values from Views
		int position = mDocBranch.getSelectedItemPosition();
		String branch = mDocBranch.getSelectedItem().toString();
		String phone = mDocPhoneView.getText().toString();
		String email = mDocEmailView.getText().toString();
		String address = mDocAddressView.getText().toString();

		View focusView = null;

		// Check Branch
		if (position == 0) {
			Toast.makeText(getApplicationContext(), "select Branch",
					Toast.LENGTH_LONG).show();
			success = false;
		}

		// Check phone No
		if (TextUtils.isEmpty(phone)) {
			mDocPhoneView.setError("Can't be empty");
			focusView = mDocPhoneView;
			success = false;
		} else if (phone.length() < 10) {
			mDocPhoneView.setError("Must be 10 digits");
			focusView = mDocPhoneView;
			success = false;
		}

		// Check Email
		if (TextUtils.isEmpty(email)) {
			mDocEmailView.setError("Can't be empty");
			focusView = mDocEmailView;
			success = false;
		} else if (!email.contains("@")) {
			mDocEmailView.setError("not a valid email");
			focusView = mDocEmailView;
			success = false;
		}

		// Check Address
		if (TextUtils.isEmpty(address)) {
			mDocAddressView.setError("Can't be empty");
			focusView = mDocAddressView;
			success = false;
		}

		// Requesting focus for error display
		if (!success) {
			if (focusView != null)
				focusView.requestFocus();
			success = false;
		} else {
			doctor = new Doctors(docName, branch, phone, email, address);
		}

		return success;
	}
}
