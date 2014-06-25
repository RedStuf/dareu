package com.phonezilla.dareu.schermen.grouppackage.fragments;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

public class Accepted extends Fragment {

	private View view;
	private String groupid;
	private final int LAYOUTHEIGHT = 100;
	private final int DESCRIPTIONLENGTH = 250;
	Context context;
	LinearLayout layout;
	Timer timer;
	public Accepted() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		view = inflater.inflate(R.layout.fragment_accepted, container, false);
		context = getActivity();
		layout = (LinearLayout) view.findViewById(R.id.acceptedchallenges);
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
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Maak een Challenge");
		alert.setMessage("Voer een naam,description in");

		// Set an EditText view to get user input

		final EditText input1 = new EditText(context);
		input1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				MainActivity.MAXLETTERS) });
		final EditText input2 = new EditText(context);
		input2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				DESCRIPTIONLENGTH) });
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(input1);
		ll.addView(input2);
		alert.setView(ll);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				String value1 = input1.getText().toString();
				String value2 = input2.getText().toString();

				/*
				 * hier moet de groep aangemaakt worden in de database
				 * vervolgens kan hij opgehaald worden door middel van een
				 * refresh
				 */
				ParseObject challenges = new ParseObject("Challenges");
				challenges.put("ChallengeName", value1);
				challenges.put("Description", value2);
				challenges.put("GroupId", groupid);
				challenges.put("State", 0);
				challenges.put("Acceptees", 0);

				// Save the post and return
				challenges.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							getActivity().setResult(getActivity().RESULT_OK);
						} else {
							Toast.makeText(context.getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}

				});

				getChallenges();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

	public void getChallenges() {
		layout.removeAllViews();
		groupid = getActivity().getIntent().getExtras().get("groupid")
				.toString();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenges");
		query.whereEqualTo("GroupId", groupid);
		// Run the query
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> challengeList, ParseException e) {
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					// groups.clear();
					if (challengeList.size() > 0) {
						for (ParseObject challenge : challengeList) {
							Log.d("challenge groep id", groupid + " "
									+ challenge.getString("GroupId") + " x");
							addChallenge(challenge.getString("ChallengeName"),
									challenge.getString("Description"));
							Log.d("groep", challenge.getString("ChallengeName")
									+ " is toegevoegd");
						}
					}

					// ((ArrayAdapter<String>)ListView.getAdapter()).notifyDataSetChanged();
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
				Log.d("groep", Arrays.toString(challengeList.toArray())
						+ " is toegevoegd");
				// adapter.notifyDataSetChanged();;
			}
		});
	}

	private void addChallenge(String name, String description) {

		LinearLayout ll = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView t1 = new TextView(context);
		TextView t2 = new TextView(context);

		ll.setBackgroundResource(R.color.listbackground);
		ll.setMinimumHeight(MainActivity.GROUPLAYOUTHEIGHT);
		ll.setWeightSum(1);
		ll.setPadding(30, 0, 0, 0);
		params.setMargins(0, 0, 0, 5);
		ll.setLayoutParams(params);

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

		ll.setMinimumWidth(layout.getWidth());
		if (layout != null)
			layout.addView(ll);
	}

}
