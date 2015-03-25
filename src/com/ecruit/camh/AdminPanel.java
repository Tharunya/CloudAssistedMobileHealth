package com.ecruit.camh;

import java.util.ArrayList;
import java.util.List;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Users;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class AdminPanel extends Activity {

	public static final String TAG = "com.ecruit.hospital.AdminPanel";

	private DbHandler dbHandler;

	private ArrayList<String> id = new ArrayList<String>();
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> usertype = new ArrayList<String>();
	private List<Users> all_users = new ArrayList<Users>();

	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_panel);
		// Show the Up button in the action bar.
		setupActionBar();

		dbHandler = new DbHandler(this);
		userList = (ListView) findViewById(R.id.lvUsers);
		// click to update data
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent i = new Intent(getApplicationContext(), Register.class);
				i.putExtra("ID", id.get(arg2));
				i.putExtra("name", name.get(arg2));
				i.putExtra("usertype", usertype.get(arg2));
				i.putExtra("update", true);
				startActivity(i);
			}
		});

		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(AdminPanel.this);
				build.setTitle("Delete " + usertype.get(arg2) + " "
						+ name.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Toast.makeText(
										getApplicationContext(),
										usertype.get(arg2) + " "
												+ name.get(arg2)
												+ " is deleted.", 3000).show();

								dbHandler.deleteUser(new Users(id.get(arg2)));

								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
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
		getMenuInflater().inflate(R.menu.admin_panel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
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
		case R.id.action_adduser:
			// Adding New User
			Intent intent = new Intent(getApplicationContext(), Register.class);
			intent.putExtra("update", false);
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

		id.clear();
		name.clear();
		usertype.clear();

		all_users = dbHandler.getAllUsers();

		if (!all_users.isEmpty()) {
			for (Users user : all_users) {
				id.add(user.getUser_id());
				name.add(user.getName());
				usertype.add(user.getUser_cat());
				Log.d(TAG, user.getUser_id() + " " + user.getName() + " "
						+ user.getUser_cat());
			}

			DispalyAdapter disadpt = new DispalyAdapter(AdminPanel.this, id,
					name, usertype);
			userList.setAdapter(disadpt);
		}
	}

}
