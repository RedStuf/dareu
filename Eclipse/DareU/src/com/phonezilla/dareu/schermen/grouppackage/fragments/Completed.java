package com.phonezilla.dareu.schermen.grouppackage.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonezilla.dareu.R;

public class Completed extends Fragment {


    public Completed() {
        // Required empty public constructor
    }

    public void makeChallenge(View v)
    {
    	
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed, container, false);
    }


}
