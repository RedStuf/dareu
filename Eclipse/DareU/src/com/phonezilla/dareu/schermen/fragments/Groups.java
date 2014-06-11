package com.phonezilla.dareu.schermen.fragments;



import android.support.v4.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */

public class Groups extends Fragment implements ListView.OnItemClickListener {
    private String tempid;
    private Random r = new Random();
    private final int GROUPLAYOUTHEIGHT =100;
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
        /*ListView = (ListView) view.findViewById(R.id.listView1);
        

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, groups);
        
		ListView.setAdapter(adapter);
		ListView.setOnItemClickListener(this);*/
        addGroup();
        return view;
    }
    public void addGroup()
    {
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
    	        	groups.add(group.getString("GroupName"),group.getString("ObjectId"));
    	        	makeGroup(group.getString("GroupName"),group.getString("ObjectId"));
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
    public void makeGroup(String name,String id)
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
            	/*
            	 * 
            	 */
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

class KlikLuisteraar implements OnClickListener
{
	public KlikLuisteraar(int id)
	{
		
	}
	
}

