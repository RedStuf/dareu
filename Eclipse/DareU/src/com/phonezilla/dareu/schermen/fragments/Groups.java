package com.phonezilla.dareu.schermen.fragments;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.MainActivity;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */

public class Groups extends Fragment {
    String tempid;
    static View view = null;
    private ArrayList<String> groups;
    ListView ListView;
    
    public Groups() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_groups, container, false);
        groups = new ArrayList<String>();
        Button button1 = (Button)view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		makeGroup();
        	}
        });
        /*ListView = (ListView) view.findViewById(R.id.listView1);
        

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, groups);
        
		ListView.setAdapter(adapter);
		ListView.setOnItemClickListener(this);*/
        getGroups();
        return view;
    }
    public void makeGroup()
    {
    	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Maak een groep");
        alert.setMessage("Voer een groepsnaam in");

//Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MainActivity.MAXLETTERS)});
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int whichButton) {
                int id = 1;
                String value = input.getText().toString();
                /* hier moet de groep aangemaakt worden in de database 
                 * vervolgens kan hij opgehaald worden 
                 * door middel van een refresh
                 */
                ParseObject groups = new ParseObject("Groups");
                groups.put("GroupName", value);
                                   
                // Save the post and return
                groups.saveInBackground(new SaveCallback () {
               
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
                
                getGroups();
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
    public void getGroups()
    {

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.grouplayout);
        if(layout != null)
        {
        	layout.removeAllViews();
        }
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Groups");
             
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
    	        	groups.add(group.getString("GroupName"));
    	        	addGroup(group.getString("GroupName"),group.getObjectId());
    	        	Log.d("groep",group.getString("GroupName")+" is toegevoegd en id = "+group.getObjectId());
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
    public void addGroup(String name,String groupid)
    {
        
        LinearLayout ll = new LinearLayout(getActivity());
        LinearLayout ll1 = new LinearLayout(getActivity());
        LinearLayout ll2 = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
        TextView t1 = new TextView(getActivity());
        TextView t2 = new TextView(getActivity());
        ImageView image = new ImageView(getActivity());
        int color = Color.argb(255, 40, 40, 40);   
        
        ll.setBackgroundColor(color);
        ll.setMinimumHeight(MainActivity.GROUPLAYOUTHEIGHT);
        ll.setWeightSum(1);

        ll2.setOrientation(LinearLayout.VERTICAL);
        
        t1.setText(name);
        t2.setText("description");
        
        image.setImageDrawable(image.getResources().getDrawable(R.drawable.stamp2));
        image.setAdjustViewBounds(true);
        image.setMaxHeight(MainActivity.GROUPLAYOUTHEIGHT);
        image.setMaxWidth(MainActivity.GROUPLAYOUTHEIGHT);
        ll1.setWeightSum(1);
        ll2.setWeightSum(2);
        ll1.addView(image);
        ll2.addView(t1);
        ll2.addView(t2);
        ll.addView(ll1);
        ll.addView(ll2);
        ll.setPadding(30, 0, 0, 0);
        params.setMargins(0, 0, 0, 5);
        ll.setLayoutParams(params);
        /*ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	
            	Intent intent = new Intent(getActivity(),GroupPage.class);
                Log.d("groupid",tempid+"");
                intent.putExtra("groupid",tempid);
                startActivity(intent);
                
            }
        });
        */
        ll.setOnClickListener(new KlikLuisteraar(groupid));
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.grouplayout);
        ll.setMinimumWidth(layout.getWidth());
        if(layout != null)
        	layout.addView(ll);
    }


   }


class KlikLuisteraar implements OnClickListener
{
	String groupid;
	public KlikLuisteraar(String groupid_)
	{
		this.groupid = groupid_;
	}

	@Override
	public void onClick(View v) {
		Context context = v.getContext();
		Intent intent = new Intent(context,GroupPage.class);
		Log.d("sda",groupid+"");
        intent.putExtra("groupid",groupid);
        context.startActivity(intent);
		
	}
	
}

