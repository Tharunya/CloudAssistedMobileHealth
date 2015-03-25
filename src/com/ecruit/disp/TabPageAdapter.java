package com.ecruit.disp;

import com.ecruit.camh.FragmentPatient;
import com.ecruit.camh.FragmentPatientPrescription;
import com.ecruit.camh.FragmentPatientTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPageAdapter extends FragmentPagerAdapter	 {
	
	public TabPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Fragment getItem(int index) {
		 switch (index) {
	        case 0:
	            // Patient Details fragment activity
	        	FragmentPatient fragmentPatient = new FragmentPatient();
	            return fragmentPatient;	            
	        case 1:
	            // Patient Prescription fragment activity
	        	FragmentPatientPrescription fragmentPatientPrescription = new FragmentPatientPrescription(); 
	            return fragmentPatientPrescription;
	        case 2:
	            // Patient Test fragment activity
	        	FragmentPatientTest fragmentPatientTest = new FragmentPatientTest(); 
	            return fragmentPatientTest;
	        }
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
        return 3;
	}

}
