package com.phonezilla.dareu.handlers;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.objects.Collection;

public class ArrayAdapterItem extends ArrayAdapter<Collection> {

	Context mContext;
	int layoutResourceId;
	ArrayList<Collection> data = null;

	public ArrayAdapterItem(Context mContext, int layoutResourceId,
			ArrayList<Collection> data) {

		super(mContext, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		Collection objectItem = data.get(position);

		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.textViewItem);
		textViewItem.setText(objectItem.itemName);
		textViewItem.setTag(objectItem.itemId);

		return convertView;

	}

}