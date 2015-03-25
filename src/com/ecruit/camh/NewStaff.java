package com.ecruit.camh;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Users;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class NewStaff extends Activity {

	public static final String TAG = "com.ecruit.hospital.Register";

	DbHandler dbHandler;

	private Context ctx;
	private Button mRegister;
	private Button mClear;
	private EditText mNameView;
	private EditText mUserNameView;
	private EditText mPasswordView;
	private EditText mCPasswordView;
	private Spinner mUserCatSpinView;

	private Users users;
	private boolean isUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// Show the Up button in the action bar.
		setupActionBar();

		ctx = this;
		dbHandler = new DbHandler(getApplicationContext());
		users = new Users();

		mRegister = (Button) findViewById(R.id.btnRegister);
		mClear = (Button) findViewById(R.id.btnClear);
		mNameView = (EditText) findViewById(R.id.etxtName);
		mUserNameView = (EditText) findViewById(R.id.etxtUser);
		mPasswordView = (EditText) findViewById(R.id.etxtPass);
		mCPasswordView = (EditText) findViewById(R.id.etxtCPass);
		mUserCatSpinView = (Spinner) findViewById(R.id.userCatg);
		mUserCatSpinView.setVisibility(View.GONE);
		isUpdate = getIntent().getExtras().getBoolean("update");
		if (isUpdate) {
			mRegister.setText("Update");

			int position = 0;
			users = dbHandler
					.getUser(getIntent().getExtras().getString("name"));

			mNameView.setText(users.getName());
			mUserNameView.setText(users.getUser_name());
			mPasswordView.setText(users.getPassword());
			mCPasswordView.setText(users.getPassword());

			/*if (users.getUser_cat().equalsIgnoreCase("doctor"))
				position = 1;
			else if (users.getUser_cat().equalsIgnoreCase("cashier"))
				position = 2;
			else if (users.getUser_cat().equalsIgnoreCase("labtech"))
				position = 3;
			else if (users.getUser_cat().equalsIgnoreCase("admin"))
				position = 4;*/

			mUserCatSpinView.setSelection(3);
		}

		// mUserCatSpin.setOnItemSelectedListener(this);
		mRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					if (isUpdate) {
						dbHandler.updateUser(users);
						Toast.makeText(ctx, "User Updated Successfully",
								Toast.LENGTH_LONG).show();
						mRegister.setText("Register");
					} else {
						dbHandler.addUser(users);
						Toast.makeText(ctx, "User Created Successfully",
								Toast.LENGTH_LONG).show();
					}
					clearConts();
				}
			}
		});
		mClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearConts();
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
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

		// Reset errors.
		mNameView.setError(null);
		mUserNameView.setError(null);
		mPasswordView.setError(null);
		mCPasswordView.setError(null);

		String mName = mNameView.getText().toString();
		String mUser = mUserNameView.getText().toString();
		String mPassword = mPasswordView.getText().toString();
		String mCPassword = mCPasswordView.getText().toString();
//		String mUserCat = mUserCatSpinView.getSelectedItem().toString();

		View focusView = null;

		// Check for a name.
		if (TextUtils.isEmpty(mName)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			success = false;
		}

		// Check for a name.
		if (TextUtils.isEmpty(mUser)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			success = false;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			success = false;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			success = false;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mCPassword)) {
			mCPasswordView.setError(getString(R.string.error_field_required));
			focusView = mCPasswordView;
			success = false;
		} else if (!mCPassword.equals(mPassword)) {
			mCPasswordView.setError(getString(R.string.error_do_not_match));
			focusView = mCPasswordView;
			success = false;
		}

		/*// Check user category
		if (mUserCatSpinView.getSelectedItemPosition() == 0) {
			Toast.makeText(ctx, "Select Category", Toast.LENGTH_LONG).show();
			focusView = mUserCatSpinView;
			success = false;
			Log.d(TAG, "User Category not selected");
		}*/

		if (!success) {
			focusView.requestFocus();
			success = false;
		} else {
			users.setName(mName);
			users.setUser_name(mUser);
			users.setPassword(mPassword);
			users.setUser_cat("LabTest");
			success = true;
		}

		return success;
	}

	protected void clearConts() {
		mNameView.setText("");
		mUserNameView.setText("");
		mPasswordView.setText("");
		mCPasswordView.setText("");
		mUserCatSpinView.setSelection(0);

		mNameView.requestFocus();
	}
}