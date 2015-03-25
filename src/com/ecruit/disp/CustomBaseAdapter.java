package com.ecruit.disp;

import java.util.List;

import com.ecruit.camh.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

	Context context;
	List<RowItem> rowItems;

	/**
	 * @param context
	 * @param rowItems
	 */
	public CustomBaseAdapter(Context context, List<RowItem> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
	}

	@Override
	public int getCount() {		
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflator = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflator.inflate(R.layout.patlist, null);
			holder = new ViewHolder();
			holder.id = (TextView) convertView.findViewById(R.id.txt_id);
			holder.name = (TextView) convertView.findViewById(R.id.txt_name);
			holder.gender = (TextView) convertView
					.findViewById(R.id.txt_gender);
			holder.age = (TextView) convertView.findViewById(R.id.txt_age);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		RowItem item = (RowItem) getItem(position);
		holder.id.setText(item.getId());
		holder.name.setText(item.getName());
		holder.gender.setText(item.getGender());
		holder.age.setText(item.getAge());

		return convertView;
	}

	private class ViewHolder {
		TextView id;
		TextView name;
		TextView gender;
		TextView age;
	}

}
