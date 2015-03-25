package com.ecruit.camh;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DispalyAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> usertype;

	/**
	 * @param mContext
	 * @param id
	 * @param name
	 * @param userCat
	 */
	public DispalyAdapter(Context mContext, ArrayList<String> id,
			ArrayList<String> name, ArrayList<String> usertype) {
		this.mContext = mContext;
		this.id = id;
		this.name = name;
		this.usertype = usertype;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (convertView == null) {
			layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) convertView.findViewById(R.id.txt_id);
			mHolder.txt_name = (TextView) convertView
					.findViewById(R.id.txt_name);
			mHolder.txt_usertype = (TextView) convertView
					.findViewById(R.id.txt_usertype);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}
		mHolder.txt_id.setText(id.get(position));
		mHolder.txt_name.setText(name.get(position));
		mHolder.txt_usertype.setText(usertype.get(position));

		return convertView;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_name;
		TextView txt_usertype;
	}

}
