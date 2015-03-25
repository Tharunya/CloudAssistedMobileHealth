package com.ecruit.camh;

import java.util.ArrayList;
import java.util.List;

import com.ecruit.dbase.DbHandler;
import com.ecruit.disp.CustomBaseAdapter;
import com.ecruit.disp.RowItem;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LabTest extends Activity {
	
	private DbHandler dbHandler;
	CustomBaseAdapter adapter;

	private List<RowItem> doc_patients = new ArrayList<RowItem>();
	RowItem rowItem;
	private ListView patList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lab_test);
		// Show the Up button in the action bar.
		setupActionBar();
		
		dbHandler = new DbHandler(this);
		patList = (ListView) findViewById(R.id.listPatients);
		displayData();
		
		patList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				/*Intent i = new Intent(getApplicationContext(),
						PatientInfo.class);
				rowItem = (RowItem) adapter.getItem(position);
				i.putExtra("rowItem", rowItem);
				startActivity(i);*/
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lab_test, menu);
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
	
	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	private void displayData() {
		doc_patients = dbHandler.getAllPatients();
		if (!doc_patients.isEmpty()) {
			adapter = new CustomBaseAdapter(this,
					doc_patients);
			patList.setAdapter(adapter);
		}

	}
}
