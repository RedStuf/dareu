package com.phonezilla.dareu;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phonezilla.dareu.objects.User;
import com.phonezilla.dareu.schermen.MainActivity;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;


public class Beginscherm extends Activity {

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        Parse.initialize(this, "jO1gEQOmHYCbpI9S05t2v4jfgAhnWglBTx4Tma8m", "nylyg1NjpI5NcW4bOz74xNebQbEEDF9OctbTj5qI");
        getActionBar().hide();
        
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        
  	  // Run the query  
  	  query.findInBackground(new FindCallback<ParseUser>() {
  	 
  	    @Override
  	    public void done(List<ParseUser> userList,
  	        ParseException e) {
  	      if (e == null) {
  	        // If there are results, update the list of posts
  	        // and notify the adapter
  	        //groups.clear();
  	    	  Log.d("userListCount", ""+userList.size());
  	    	  for (ParseUser user : userList) {
  				GroupPage.ObjectItemData.add(new User(user.getObjectId().toString(),user.get("username").toString()));
  			}
  	 
  	        //((ArrayAdapter<String>)ListView.getAdapter()).notifyDataSetChanged();
  	      } else {
  	        Log.d("Post retrieval", "Error: " + e.getMessage());
  	      }
  	        //adapter.notifyDataSetChanged();;
  	    }            
  	  });
  	final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }, 1000);
            
        
        
    }
}
