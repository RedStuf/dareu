package com.phonezilla.dareu.schermen.grouppackage.fragments;

import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.phonezilla.dareu.R;

public class Accepted extends Fragment {

    private View view;
    private String groupid;
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
        Button button1 = (Button)view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		makeChallenge();
        	}
        });
        return view;
    }
    public void makeChallenge()
    {
    	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Maak een Challenge");
        alert.setMessage("Voer een naam,description in");

//Set an EditText view to get user input
       
        final EditText input1 = new EditText(getActivity());
        final EditText input2 = new EditText(getActivity());
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(input1);
        ll.addView(input2);
        alert.setView(ll);
      

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int whichButton) {
               
                String value1 = input1.getText().toString();
                String value2 = input2.getText().toString();
                
                /* hier moet de groep aangemaakt worden in de database 
                 * vervolgens kan hij opgehaald worden 
                 * door middel van een refresh
                 */
                ParseObject challenges = new ParseObject("Challenges");
                challenges.put("ChallengeName", value1);
                challenges.put("Description", value2);
                challenges.put("GroupId", groupid);
                
                                   
                // Save the post and return
                challenges.saveInBackground(new SaveCallback () {
               
                  @Override
                  public void done(ParseException e) {
                    if (e == null) {
                    	getActivity().setResult(getActivity().RESULT_OK);
                    } else {
                      Toast.makeText(getActivity().getApplicationContext(),
                      "Error saving: " + e.getMessage(),
                             Toast.LENGTH_SHORT)
                             .show();
                    }
                  }
               
                });
                

                getChallenges();   
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();    	
    }
    public void getChallenges()
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
  	        	groupid = getActivity().getIntent().getExtras().get("groupid").toString();
  	        	Log.d("challenge groep id",groupid + " "+ group.getString("GroupId")+" x");
  	        	if(group.getString("GroupId").equals(groupid))
  	        	{
  	        		addChallenge(group.getString("ChallengeName"),group.getString("Description"));
  	  	        	Log.d("groep",group.getString("ChallengeName")+" is toegevoegd");
  	        	}
  	        	
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

    private void addChallenge(String name,String description) {
            
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


