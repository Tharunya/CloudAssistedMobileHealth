package com.ecruit.camh;

import com.ecruit.dbase.DbHandler;
import com.ecruit.dbase.Patient;
import com.ecruit.disp.RowItem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentPatient extends Fragment {
	
	DbHandler db;
	Patient patient;

	TextView id, name, gender, age, address, phone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		db = new DbHandler(getActivity().getApplicationContext());

		View rootView = inflater.inflate(R.layout.fragment_patient_details,
				container, false);

		id = (TextView) rootView.findViewById(R.id.txt_pat_id);
		name = (TextView) rootView.findViewById(R.id.txt_pat_name);
		gender = (TextView) rootView.findViewById(R.id.txt_pat_gender);
		age = (TextView) rootView.findViewById(R.id.txt_pat_age);
		address = (TextView) rootView.findViewById(R.id.txt_pat_address);
		phone = (TextView) rootView.findViewById(R.id.txt_pat_phone);

		//RowItem rowItem = (RowItem) getArguments().getSerializable("rowItem");
		RowItem rowItem = (RowItem) getActivity().getIntent().getSerializableExtra("rowItem");		
		patient = db.getPatient(rowItem.getId(), rowItem.getName());

		try {
			id.setText(patient.getId());
			name.setText(patient.getName());
			gender.setText(patient.getGender());
			age.setText(patient.getAge());
			address.setText(patient.getAddress());
			phone.setText(patient.getPh_no());			
		} catch (Exception ex) {
			// Log.d("EXCEPTION",ex.getMessage());
		}

		return rootView;
	}
}
