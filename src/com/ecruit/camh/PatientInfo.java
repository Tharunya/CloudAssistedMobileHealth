package com.ecruit.camh;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Patient;
import com.ecruit.disp.RowItem;
import com.ecruit.disp.TabPageAdapter;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;

public class PatientInfo extends FragmentActivity implements
		ActionBar.TabListener {
	
	DbHandler db;

	private ViewPager viewPager;
	private TabPageAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Patient Info", "Prescription", "Test Report" };

	public static final String TAG = "com.ecruit.hospital.PatientInfo";

	String docName;
	RowItem rowItem;
	Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info);

		// getting doctor's name from Start activity
		//db.getUser(name)
		docName = getIntent().getExtras().getString("docName");
		rowItem = (RowItem) getIntent().getSerializableExtra("rowItem");
		
		db = new DbHandler(getApplicationContext());

		// Initialization
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mAdapter = new TabPageAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);

		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);
		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Log.d(TAG, "Home clicked, PatientInfo Activity");
			Intent upIntent = NavUtils
					.getParentActivityIntent(PatientInfo.this);
			upIntent.putExtra("docName", docName);
			NavUtils.navigateUpTo(this, upIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed, PatientInfo Activity");
		super.onBackPressed();
		// Sending doctor name to Parent Activity
		Intent intent = NavUtils.getParentActivityIntent(PatientInfo.this);
		intent.putExtra("docName", docName);
		NavUtils.navigateUpTo(this, intent);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
