package com.phonezilla.dareu.schermen.grouppackage.fragments;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.phonezilla.dareu.R;

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
        getChallenges();
        return view;
    }
    private void getChallenges()
    {
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenges");
        
  	  // Run the query  
  	  query.findInBackground(new FindCallback<ParseObject>() {
  	 
  	    @Override
  	    public void done(List<ParseObject> groupList,
  	        ParseException e) {
  	      if (e == null) {
  	        // If there are results, update the list of posts
  	        // and notify the adapter
  	        //groups.clear();
	        	
  	        for (ParseObject group : groupList) {
  	        	Log.d("groupiddas", getActivity().getIntent().getExtras().containsKey("groupid")+"");
  	        	addChallenge(group.getString("ChallengeName"),group.getString("Description"),getActivity().getIntent().getExtras().get("groupid").toString());
  	        	Log.d("groep",group.getString("GroupName")+" is toegevoegd");
  	        }
  	 
  	        //((ArrayAdapter<String>)ListView.getAdapter()).notifyDataSetChanged();
  	      } else {
  	        Log.d("Post retrieval", "Error: " + e.getMessage());
  	      }
	        	Log.d("groep",Arrays.toString(groupList.toArray())+" is toegevoegd");
  	        //adapter.notifyDataSetChanged();;
  	    }            
  	  });
    }

    private void addChallenge(String name,String description,String id) {
            
            LinearLayout ll = new LinearLayout(getActivity());
            
            TextView t1 = new TextView(getActivity());
            TextView t2 = new TextView(getActivity());
            int color = Color.argb(255, 40, 40, 40);   
            
            ll.setBackgroundColor(color);
            ll.setMinimumHeight(LAYOUTHEIGHT);
            ll.setWeightSum(1);

            ll.setOrientation(LinearLayout.VERTICAL);
            
            t1.setText(name);
            t2.setText(description);
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


