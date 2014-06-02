package com.phonezilla.dareu.schermen.fragments;



import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.Group;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class Groups extends Fragment {
    int tempid;
    static View view = null;
    public Groups() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_groups, container, false);
        for(int i=0;i<15;i++)
        {
        	addGroup("hoi"+i,1);
        }
        return view;
    }
    public void addGroup(String name,int id)
    {
        tempid = id;
        LinearLayout ll = new LinearLayout(getActivity());
        TextView t1 = new TextView(getActivity());
        TextView t2 = new TextView(getActivity());
        ImageView image = new ImageView(getActivity());
        ll.setWeightSum(1);
        Random r = new Random();
        int color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));   
        ll.setBackgroundColor(color);
        ll.setMinimumHeight(100);
        t1.setText(name);
        t2.setText("description");
        ll.addView(image);
        ll.addView(t1);
        ll.addView(t2);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Group.class);
                Log.d("groupid",tempid+"");
                intent.putExtra("groupid",tempid);
                startActivity(intent);
            }
        });
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.grouplayout);
        ll.setMinimumWidth(layout.getWidth());
        if(layout != null)
            layout.addView(ll);
    }
   }

