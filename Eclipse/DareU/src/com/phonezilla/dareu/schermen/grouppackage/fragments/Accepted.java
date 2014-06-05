package com.phonezilla.dareu.schermen.grouppackage.fragments;

import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

public class Accepted extends Fragment {

    View view;
    private Random r = new Random();
    private final int LAYOUTHEIGHT = 100;

    public Accepted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_accepted, container, false);
        addChallenge("challenge name");
        addChallenge("EET EEN AAP");
        addChallenge("Spring van een gebouw");
        return view;
    }

    private void addChallenge(String name) {
        int id = getActivity().getIntent().getExtras().getInt("groupid");
        if (id == 1) {
            
            LinearLayout ll = new LinearLayout(getActivity());
            
            TextView t1 = new TextView(getActivity());
            TextView t2 = new TextView(getActivity());
            int color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));   
            
            ll.setBackgroundColor(color);
            ll.setMinimumHeight(LAYOUTHEIGHT);
            ll.setWeightSum(1);

            ll.setOrientation(LinearLayout.VERTICAL);
            
            t1.setText(name);
            t2.setText("description");
            ll.addView(t1);
            ll.addView(t2);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.acceptedchallenges);
            ll.setMinimumWidth(layout.getWidth());
            if(layout != null)
                layout.addView(ll);
        }
    }
}


