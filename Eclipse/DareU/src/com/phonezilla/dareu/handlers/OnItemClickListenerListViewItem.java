package com.phonezilla.dareu.handlers;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
 
/*
 * Here you can control what to do next when the user selects an item
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {
 

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 
        Context context = view.getContext();
        TextView textViewItem = ((TextView) view.findViewById(R.id.textViewItem));
        String userid = textViewItem.getTag().toString();
         
        ((GroupPage) context).addUser(userid);
    }
     
}