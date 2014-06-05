package com.phonezilla.dareu.schermen.fragments;



import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */

public class Groups extends Fragment {
    int tempid;
    private Random r = new Random();
    private final int GROUPLAYOUTHEIGHT =100;
    static View view = null;
    public Groups() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_groups, container, false);
        addGroup();
        return view;
    }
    public void addGroup()
    {
    	for(int i=0;i<15;i++)
        {
        	makeGroup("hoi"+i,1);
        }
    }
    public void makeGroup(String name,int id)
    {
        tempid = id;
        
        LinearLayout ll = new LinearLayout(getActivity());
        LinearLayout ll1 = new LinearLayout(getActivity());
        LinearLayout ll2 = new LinearLayout(getActivity());
        
        TextView t1 = new TextView(getActivity());
        TextView t2 = new TextView(getActivity());
        ImageView image = new ImageView(getActivity());
        int color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));   
        
        ll.setBackgroundColor(color);
        ll.setMinimumHeight(GROUPLAYOUTHEIGHT);
        ll.setWeightSum(1);

        ll2.setOrientation(LinearLayout.VERTICAL);
        
        t1.setText(name);
        t2.setText("description");
        
        image.setImageDrawable(image.getResources().getDrawable(R.drawable.stamp2));
        image.setAdjustViewBounds(true);
        image.setMaxHeight(GROUPLAYOUTHEIGHT);
        image.setMaxWidth(GROUPLAYOUTHEIGHT);
        ll1.setWeightSum(1);
        ll2.setWeightSum(2);
        ll1.addView(image);
        ll2.addView(t1);
        ll2.addView(t2);
        ll.addView(ll1);
        ll.addView(ll2);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GroupPage.class);
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

