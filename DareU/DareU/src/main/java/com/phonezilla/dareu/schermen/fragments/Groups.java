package com.phonezilla.dareu.schermen.fragments;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.Group;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class Groups extends Fragment {

    static View view = null;
    public Groups() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_groups, container, false);
        addGroup("hoi");
        return view;
    }
    public void addGroup(String name)
    {

        Button button = new Button(getActivity());
        button.setText(name);
        button.setMinimumHeight(150);
        button.setMinimumWidth(150);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Group.class);
                startActivity(intent);
            }
        });
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.grouplayout);
        if(layout != null)
            layout.addView(button);
    }
   }

