package com.phonezilla.dareu.schermen.grouppackage.challenge;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
