package com.phonezilla.dareu.schermen.grouppackage.challenge;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
=======
import android.provider.MediaStore;
>>>>>>> origin/master
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ImageButton;
import android.widget.LinearLayout;
=======
import android.widget.TextView;
>>>>>>> origin/master

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.phonezilla.dareu.Beginscherm;
import com.phonezilla.dareu.R;

public class ChallengeDetails extends Activity {
	
	public DialogInterface.OnClickListener mDialogListener;
	public int groupSize;
	public int acceptanceCounter;
	public double minAcceptance = 0.5;
	
	protected TextView challengeTitle;
	protected TextView challengeDescription;
	protected TextView challengeScore;
	
	
			 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.challenge_description);
		addButtons();
		
		/*
		challengeTitle = (TextView) findViewById(R.id.challengeTitleView);
		challengeDescription = (TextView)findViewById(R.id.challengeDescriptionView);
		challengeScore = (TextView) findViewById(R.id.scoreView);
		*/
		
			
		 DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {
			 public static final int TAKE_PHOTO_REQUEST = 0;
			 public static final int TAKE_VIDEO_REQUEST = 1;
			 public static final int PICK_PHOTO_REQUEST = 2;
			 public static final int PICK_VIDEO_REQUEST = 3;
			 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case 0: //Take Picture
					Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
					break;
					
				case 1: //Take video
					break;
					
				case 2: //choose picture
					break;
				
				case 3: //choose video
					break;
					
			}
		};
		
		};
	
			
		
		
		
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
		
		switch (id){
		case R.id.action_camera:
			AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeDetails.this);
			builder.setItems(R.array.camera_choices, mDialogListener);
			AlertDialog dialog = builder.create();
			dialog.show();
			
			break;
			
		case R.id.action_settings: 
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
