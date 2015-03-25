package com.ecruit.camh;

import java.util.List;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Users;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Start extends Activity {

	public static final String TAG = "Startx";// "com.ecruit.hospital.Start";

	DbHandler dbHandler;

	private EditText userName;
	private EditText password;
	private Button forgot;
	private Button login;
	private Spinner userCat;

	private Context ctx;
	Users user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate called");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		dbHandler = new DbHandler(getApplicationContext());
		user = new Users();
		ctx = this;
		userName = (EditText) findViewById(R.id.etxtUsername);
		password = (EditText) findViewById(R.id.etxtPassword);
		forgot = (Button) findViewById(R.id.btnForgot);
		login = (Button) findViewById(R.id.btnLogin);
		userCat = (Spinner) findViewById(R.id.userCat);

		login.setOnClickListener(clickButton);
		forgot.setOnClickListener(clickButton);
		
	}

	final OnClickListener clickButton = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			// login Button action
			case R.id.btnLogin:
				if (validate())
					loginAction();
				break;

			// Forgot button action
			case R.id.btnForgot:
				clearContents();
				break;
			}
		}
	};

	protected void loginAction() {

		Intent intent;

		String uname = userName.getText().toString();
		String pass = password.getText().toString();
		String ucat = userCat.getSelectedItem().toString();
		int position = userCat.getSelectedItemPosition();

		if (uname.equals("admin") && pass.equals("admin") && position == 4) {
			// Admin
			intent = new Intent(ctx, AdminPanel.class);
			startActivity(intent);
		} else {
			try {
				user = dbHandler.getUsers(uname, pass, ucat);
				System.out.println("User is "+user);
				if (user.getUser_cat().equalsIgnoreCase("doctor"))
					position = 1;
				else if (user.getUser_cat().equalsIgnoreCase("cashier"))
					position = 2;
				else if (user.getUser_cat().equalsIgnoreCase("labtest"))
					position = 3;
				else if (user.getUser_cat().equalsIgnoreCase("admin"))
					position = 4;
				else if(user.getUser_cat().equalsIgnoreCase("patient"))
					position = 5;
				else
					position = 6;
				switch (position) {
				case 1:
					// Doctor
					intent = new Intent(ctx, Doctor.class);
					intent.putExtra("docName", user.getName());
					startActivity(intent);
					break;
				case 2:
					// Cashier
					intent = new Intent(ctx, Cashier.class);
					startActivity(intent);
					break;
				case 3:
					// LabTech
					intent = new Intent(ctx, LabTest.class);
					startActivity(intent);
					break;
				case 4:
					// Admin
					intent = new Intent(ctx, AdminPanel.class);
					startActivity(intent);
					break;
				case 5:
					// Patient Info
					intent = new Intent(ctx, PatientPanel.class);
					startActivity(intent);
					break;
				default:
					showError();
					break;
				}
			} catch (CursorIndexOutOfBoundsException ex) {
				Log.d(TAG, ex.getMessage());
				showError();
			}
		}

	}

	private void showError() {
		String err = "Incorrect";
		// Setting animation for error
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		
		userName.setError(err);
		userName.requestFocus();
		userName.startAnimation(shake);
		
		password.setError(err);
		password.requestFocus();
		password.startAnimation(shake);
	}

	// Validating TextFields
	protected boolean validate() {
		boolean success = true;

		// Reset errors.
		userName.setError(null);
		password.setError(null);

		String mName = userName.getText().toString();
		String mPassword = password.getText().toString();

		View focusView = null;

		// Check for a name.
		if (TextUtils.isEmpty(mName)) {
			userName.setError(getString(R.string.error_field_required));
			focusView = userName;
			success = false;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			password.setError(getString(R.string.error_field_required));
			focusView = password;
			success = false;
		} else if (mPassword.length() < 4) {
			password.setError(getString(R.string.error_invalid_password));
			focusView = password;
			success = false;
		}

		// Check Category Selection
		if (userCat.getSelectedItemPosition() == 0) {
			focusView = userCat;
			success = false;
		}

		if (!success) {
			focusView.requestFocus();
		}

		return success;
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop called");
		clearContents();
		super.onStop();
	}

	// Clear all Values
	private void clearContents() {
		userName.setText("");
		password.setText("");
		userCat.setSelection(0);
		userName.requestFocus();
	}
}
