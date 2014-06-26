package com.phonezilla.dareu.schermen.grouppackage.challenge;


import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.phonezilla.dareu.Beginscherm;
import com.phonezilla.dareu.R;

public class ChallengeDetails extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.challenge_description);
		addButtons();
	}

	
	
	 public void uploadStatus(){
		 
	 }
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.challenge_description, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks  on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addButtons()
	{
		final LinearLayout ll = (LinearLayout)findViewById(R.id.acceptlayout);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge_User");
		query.whereEqualTo("UserID", Beginscherm.userid);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					if (userList.size() > 0) {
						
						ll.setVisibility(LinearLayout.INVISIBLE);
					}
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});
		ImageButton accept = (ImageButton)findViewById(R.id.accept);
		ImageButton decline = (ImageButton)findViewById(R.id.decline);
		String challengeid = getIntent().getExtras().getString("challengeid");
		accept.setOnClickListener(new myOnClickListener(true, challengeid,ll));
		decline.setOnClickListener(new myOnClickListener(false, challengeid,ll));
		
		
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_challenge_description, container, false);
			return rootView;
		}
	}

}
class myOnClickListener implements View.OnClickListener
{
	boolean accepted;
	String challengeid;
	View view;
	
	public myOnClickListener(boolean accepted, String challengeid, View view)
	{
		this.accepted = accepted;
		this.challengeid = challengeid;
		this.view = view;
	}
	@Override
	public void onClick(View v) {
		ParseObject challenge = new ParseObject("Challenge_User");
		challenge.put("ChallengeID", challengeid);
		challenge.put("UserID", Beginscherm.userid);
		challenge.put("Accepted", accepted);

		challenge.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					view.setVisibility(LinearLayout.INVISIBLE);
				} 
			}
		});
		
	}
	
}
