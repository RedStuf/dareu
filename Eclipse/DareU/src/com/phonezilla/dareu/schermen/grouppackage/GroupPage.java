package com.phonezilla.dareu.schermen.grouppackage;

import java.util.Arrays;
import java.util.List;

import Objects.User;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.fragments.Accepted;
import com.phonezilla.dareu.schermen.grouppackage.fragments.Completed;
import com.phonezilla.dareu.schermen.grouppackage.fragments.Pending;


public class GroupPage extends FragmentActivity implements TabListener {

    ActionBar actionbar;
    ViewPager pager;
    AlertDialog alertDialog;
    public GroupPage()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new Adapter(getSupportFragmentManager()));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        actionbar=getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1= actionbar.newTab();
        tab1.setText("Accepted");
        tab1.setTabListener(this);
        ActionBar.Tab tab2= actionbar.newTab();
        tab2.setText("Pending");
        tab2.setTabListener(this);
        ActionBar.Tab tab3= actionbar.newTab();
        tab3.setText("Completed");
        tab3.setTabListener(this);

        actionbar.addTab(tab1);
        actionbar.addTab(tab2);
        actionbar.addTab(tab3);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.challengemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.adduser:
                showUserScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showUserScreen()
    {
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        
    	  // Run the query  
    	  query.findInBackground(new FindCallback<ParseObject>() {
    	 
    	    @Override
    	    public void done(List<ParseObject> userList,
    	        ParseException e) {
    	      if (e == null) {
    	        // If there are results, update the list of posts
    	        // and notify the adapter
    	        //groups.clear();
      	  	
	    			for (ParseObject user : userList) {
	        	
	    				addUser(user.get("username").toString());
	    			}
    	 
    	        //((ArrayAdapter<String>)ListView.getAdapter()).notifyDataSetChanged();
    	      } else {
    	        Log.d("Post retrieval", "Error: " + e.getMessage());
    	      }
    	    }            
    	  });
    	
    }
    
    public void addUser(String userid)
    {
    	String groupid = getIntent().getExtras().getString("groupid").toString();
    	ParseObject usergroup = new ParseObject("User_Groups");
    	usergroup.put("UserID", userid);
    	usergroup.put("GroupID", groupid);
    	usergroup.put("Admin", false);
    	
    	usergroup.saveInBackground(new SaveCallback () {
            
            @Override
            public void done(ParseException e) {
              if (e == null) {
              	setResult(RESULT_OK);
              } else {
                Toast.makeText(getApplicationContext(),
                "Error saving: " + e.getMessage(),
                       Toast.LENGTH_SHORT)
                       .show();
              }
            }
         
          });
    	
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}

class Adapter extends FragmentPagerAdapter {


    public Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0)
        {
            fragment = new Accepted();
        }
        if(position == 1)
        {
            fragment = new Pending();
        }
        if(position == 2)
        {
            fragment = new Completed();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
