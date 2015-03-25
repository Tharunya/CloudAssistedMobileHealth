package com.ecruit.camh;

import java.util.ArrayList;
import java.util.List;

import com.ecruit.dbase.DbHandler;
import com.ecruit.disp.CustomBaseAdapter;
import com.ecruit.disp.RowItem;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Doctor extends Activity {

	public static final String TAG = "com.ecruit.hospital.Doctor";

	private DbHandler dbHandler;
	CustomBaseAdapter adapter;

	private List<RowItem> doc_patients = new ArrayList<RowItem>();
	RowItem rowItem;

	String docName;
	private TextView mWelcomeView;
	private ListView patList;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate, Doctor Activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor);
		// Show the Up button in the action bar.
		setupActionBar();

		dbHandler = new DbHandler(this);
		mWelcomeView = (TextView) findViewById(R.id.textView1);
		patList = (ListView) findViewById(R.id.listPatients);

		try {
			docName = getIntent().getExtras().getString("docName");
			mWelcomeView.setText("Welcome " + docName);
			displayData();
		} catch (NullPointerException ex) {
			Log.d(TAG, "Unable to get Doctor Name");
		}

		// click to update data

		patList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {

				Intent i = new Intent(getApplicationContext(),
						PatientInfo.class);
				rowItem = (RowItem) adapter.getItem(position);
				i.putExtra("docName", docName);
				i.putExtra("rowItem", rowItem);
				startActivity(i);
			}
		});

		/*
		 * patList.setOnItemLongClickListener(new OnItemLongClickListener() {
		 * 
		 * @Override public boolean onItemLongClick(AdapterView<?> arg0, View
		 * arg1, final int arg2, long arg3) {
		 * 
		 * build = new AlertDialog.Builder(Doctor.this);
		 * build.setTitle("Delete " + pattype.get(arg2) + " " + name.get(arg2));
		 * build.setMessage("Do you want to delete ?");
		 * build.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		 * {
		 * 
		 * public void onClick(DialogInterface dialog, int which) {
		 * 
		 * Toast.makeText( getApplicationContext(), pattype.get(arg2) + " " +
		 * name.get(arg2) + " is deleted.", 3000).show();
		 * 
		 * dbHandler.deleteUser(new Users(id.get(arg2)));
		 * 
		 * displayData(); dialog.cancel(); } });
		 * 
		 * build.setNegativeButton("No", new DialogInterface.OnClickListener() {
		 * 
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.cancel(); } }); AlertDialog alert = build.create();
		 * alert.show();
		 * 
		 * return true; } });
		 */
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
		getMenuInflater().inflate(R.menu.doctor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Log.d(TAG, "Home clicked, Doctor Activity");
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_search:
			// search action
			// userSearch();
			return true;
		case R.id.action_refresh:
			// refresh
			displayData();
			return true;
		case R.id.add_patients:
			Intent intent1 = new Intent(getApplicationContext(),
					NewPatient.class);
			intent1.putExtra("docName", docName);
					startActivity(intent1);
					return true;
		case R.id.add_staff:
			Intent intent2 = new Intent(getApplicationContext(),
					NewStaff.class);
			intent2.putExtra("docName", docName);
					startActivity(intent2);
					return true;
			
		case R.id.action_myinfo:
			// Edit Doctor Profile
			Intent intent = new Intent(getApplicationContext(),
					ProfileDoctor.class);
			intent.putExtra("docName", docName);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	private void displayData() {
		doc_patients = dbHandler.getDocPatients(docName);
		if (!doc_patients.isEmpty()) {
			adapter = new CustomBaseAdapter(this,
					doc_patients);
			patList.setAdapter(adapter);
		}
	}

}
