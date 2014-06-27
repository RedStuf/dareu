package com.phonezilla.dareu.schermen.grouppackage.fragments;

import java.util.List;
import java.util.Timer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.MainActivity;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;
import com.phonezilla.dareu.schermen.grouppackage.challenge.ChallengeDetails;

public class Pending extends Fragment {

	private View view;
	Context context;
	LinearLayout layout;
	Timer timer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_pending, container, false);
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
		setHasOptionsMenu(true);
		return view;
	}
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu, inflater);
	
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.refresh:
			getChallenges();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void makeChallenge() {

		((GroupPage) getActivity()).makeChallenge();
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
					if (challengeList.size() > 0) {
						for (final ParseObject challenge : challengeList) {

							ParseQuery<ParseObject> query = ParseQuery
									.getQuery("Challenge_User");
							query.whereEqualTo("ChallengeID",
									challenge.getObjectId());
							query.whereEqualTo("Accepted", true);
							query.findInBackground(new FindCallback<ParseObject>() {

								@Override
								public void done(List<ParseObject> userList,
										ParseException e) {
									if (e == null && GroupPage.currentUsers.size() > (userList.size() / 2)) {
										addChallenge(challenge.getString("ChallengeName"),challenge.getString("Description"),challenge.getObjectId());

									}
								}
							});
						}
					}
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});
	}

	private void addChallenge(final String name,final String description,final String id) {

		LinearLayout ll = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView t1 = new TextView(context);
		t1.setTextSize(24);

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
				intent.putExtra("name", name);
				intent.putExtra("description", description);
				context.startActivity(intent);
			}
		});

		ll.setMinimumWidth(layout.getWidth());
		if (layout != null)
			layout.addView(ll);
	}

}
