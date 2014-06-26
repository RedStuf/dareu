package com.phonezilla.dareu.schermen.grouppackage.fragments;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
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
import com.phonezilla.dareu.schermen.MainActivity;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;
import com.phonezilla.dareu.schermen.grouppackage.challenge.ChallengeDetails;

public class Accepted extends Fragment {

	private View view;
	Context context;
	LinearLayout layout;
	Timer timer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_accepted, container, false);
		context = getActivity();
		layout = (LinearLayout) view.findViewById(R.id.challenges);
		Button button1 = (Button) view.findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				makeChallenge();
			}
		});

		getChallenges();
		// elke 5 sec check voor nieuwe groepen
		/*timer = new Timer();
		timer.schedule(new TimerTask() {
		    public void run() {
		    	getChallenges(); 
		     }
		  }, 5000);*/
		return view;
	}
	
	public void makeChallenge() {
		
		((GroupPage)getActivity()).makeChallenge();
		getChallenges();
	}

	public void getChallenges() {
		layout.removeAllViews();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenges");
		query.whereEqualTo("GroupId", ((GroupPage) getActivity()).groupid);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> challengeList, ParseException e) {
				if (e == null) {
						for (final ParseObject challenge : challengeList) {

							ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge_User");
							query.whereEqualTo("ChallengeID",
									challenge.getObjectId());
							query.whereEqualTo("Accepted", true);
							query.findInBackground(new FindCallback<ParseObject>() {

								@Override
								public void done(List<ParseObject> userList,
										ParseException e) {
									if (e == null && GroupPage.currentUsers.size() <= (userList.size() / 2)) {
										addChallenge(challenge.getString("ChallengeName"),challenge.getObjectId());

									}
								}
							});
						
					}
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});
	}

	private void addChallenge(String name,final String id) {

		LinearLayout ll = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView t1 = new TextView(context);

		ll.setBackgroundResource(R.color.listbackground);
		ll.setMinimumHeight(MainActivity.GROUPLAYOUTHEIGHT);
		ll.setWeightSum(1);
		ll.setPadding(30, 0, 0, 0);
		params.setMargins(0, 0, 0, 5);
		ll.setLayoutParams(params);

		ll.setOrientation(LinearLayout.VERTICAL);

		t1.setText(name);
		ll.addView(t1);
		ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context,ChallengeDetails.class);
				intent.putExtra("challengeid", id);
				context.startActivity(intent);
			}
		});

		ll.setMinimumWidth(layout.getWidth());
		if (layout != null)
			layout.addView(ll);
	}

}
