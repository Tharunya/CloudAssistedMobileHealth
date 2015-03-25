package com.ecruit.camh;

import java.util.ArrayList;
import java.util.List;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Patient;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;

public class NewPatient extends Activity {

	public static final String TAG = "com.ecruit.hospital";
	DbHandler db;
	Patient patient;
	List<String> docList;

	// Views
	EditText mPatName;
	RadioGroup mPatSex;
	EditText mPatAge;
	EditText mPatAddress;
	Spinner mPatBranch;
	Spinner mPatDoct;
	EditText mPatPhone;
	Button mClear;
	Button mAddPatient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
		// Show the Up button in the action bar.
		setupActionBar();
		initialize();

		docList = new ArrayList<String>();

		mPatBranch.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String branch;
				if (position > 0) {
					branch = parent.getItemAtPosition(position).toString();
					docList = db.getAllDocs(branch);
					if (docList.size() >= 1) {
						mPatDoct.setEnabled(true);
						populateDoctors();
					} else {
						mPatDoct.setEnabled(false);
					}

				} else {
					mPatDoct.setEnabled(false);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				mPatDoct.setEnabled(false);
			}
		});

		mClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearConts();
			}
		});

		mAddPatient.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					db.addPatient(patient);
					clearConts();
					Toast.makeText(getApplicationContext(),
							"Patient Added Successfully", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	protected void clearConts() {
		mPatName.setText("");
		mPatSex.clearCheck();
		mPatAge.setText("");
		mPatAddress.setText("");
		mPatBranch.setSelection(0);
		mPatDoct.setSelection(0);
		mPatDoct.setEnabled(false);
		mPatPhone.setText("");
		// Set Focus to name
		mPatName.requestFocus();
	}

	protected void populateDoctors() {
		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, docList);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		mPatDoct.setAdapter(dataAdapter);
	}

	private void initialize() {

		db = new DbHandler(getApplicationContext());
		patient = new Patient();

		// fire up Views
		mPatName = (EditText) findViewById(R.id.etxtName);
		mPatSex = (RadioGroup) findViewById(R.id.pat_Sex);
		mPatAge = (EditText) findViewById(R.id.etxtAge);
		mPatAddress = (EditText) findViewById(R.id.etxtAddress);
		mPatBranch = (Spinner) findViewById(R.id.patCatg);
		// mPatBranch.set
		mPatDoct = (Spinner) findViewById(R.id.patDoct);
		mPatPhone = (EditText) findViewById(R.id.etxtPhone);
		mClear = (Button) findViewById(R.id.btnClear);
		mAddPatient = (Button) findViewById(R.id.btnRegister);

		// Disable Doctor Spinner View
		mPatDoct.setEnabled(false);
	}

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected boolean validate() {
		boolean success = true;

		// Reset Errors
		mPatName.setError(null);
		mPatAge.setError(null);
		mPatAddress.setError(null);
		mPatPhone.setError(null);

		// Get Values from Views
		String mName = mPatName.getText().toString();
		int mGender = mPatSex.getCheckedRadioButtonId();
		String Gender = "";
		String mAge = mPatAge.getText().toString();
		int mAgee = 0;
		String mAddress = mPatAddress.getText().toString();
		int mBranch = mPatBranch.getSelectedItemPosition();
		String mBranchh = "";
		String mDoctt = "";
		String mPhone = mPatPhone.getText().toString();

		View focusView = null;

		// Check Name
		if (TextUtils.isEmpty(mName)) {
			focusView = mPatName;
			mPatName.setError("Patient name must be entered");
			success = false;
		}

		// Check Gender
		if (mGender == R.id.pat_Male) {
			Gender = "Male";
		} else if (mGender == R.id.pat_Female) {
			Gender = "Female";
		} else {
			focusView = mPatSex;
			success = false;
		}

		// Check Age
		if (TextUtils.isEmpty(mAge)) {
			mPatAge.setError("Can't be Empty");
			focusView = mPatAge;
			success = false;
		} else {
			mAgee = Integer.valueOf(mAge);
			if (mAgee < 1 || mAgee > 130) {
				mPatAge.setError("Impossible");
				focusView = mPatAge;
				success = false;
			}
		}

		// Check Address
		if (TextUtils.isEmpty(mAddress)) {
			mPatAddress.setError("Can't be Empty");
			focusView = mPatAddress;
			success = false;
		}

		// Check Branch
		if (mBranch > 10 || mBranch == 0) {
			focusView = mPatBranch;
			Toast.makeText(getApplicationContext(), "Select Branch",
					Toast.LENGTH_LONG).show();
			success = false;
		} else {
			mBranchh = mPatBranch.getSelectedItem().toString();
		}

		// Check Doctor
		if (mPatDoct.isEnabled()) {
			mDoctt = mPatDoct.getSelectedItem().toString();
			if (mPatDoct.getSelectedItemPosition() == 0) {
				Toast.makeText(getApplicationContext(), "Select Doctor",
						Toast.LENGTH_LONG).show();
				success = false;
				focusView = mPatDoct;
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Select Branch to Select Doctor", Toast.LENGTH_LONG).show();
			success = false;
			focusView = mPatDoct;
		}

		// Check phone No
		if (TextUtils.isEmpty(mPhone)) {
			mPatPhone.setError("Can't be empty");
			focusView = mPatPhone;
			success = false;
		} else if (mPhone.length() < 10 || mPhone.length() > 15) {
			mPatPhone.setError("Must be 10 digits");
			focusView = mPatPhone;
			success = false;
		}

		if (success) {
			patient = new Patient(mName, Gender, mAge, mAddress, mBranchh,
					mDoctt, mPhone);
		} else {
			if (focusView != null)
				focusView.requestFocus();
		}

		return success;
	}
}
