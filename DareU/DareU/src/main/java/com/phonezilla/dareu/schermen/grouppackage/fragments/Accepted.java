package com.phonezilla.dareu.schermen.grouppackage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.Group;

public class Accepted extends Fragment {

    View view;

    public Accepted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_accepted, container, false);
        addChallenge();
        return view;
    }

    private void addChallenge() {
        int id = getActivity().getIntent().getExtras().getInt("groupid");
        if (id == 1) {
            Button button = new Button(getActivity());
            button.setText("mooie challenge");
            button.setMinimumHeight(150);
            button.setMinimumWidth(150);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Group.class);
                    intent.putExtra("groupid", 2);
                    startActivity(intent);
                }
            });
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.acceptedchallenges);

            Log.d("layout", layout + "");
            if (layout != null) {
                layout.addView(button);
            }
        }
    }
}


