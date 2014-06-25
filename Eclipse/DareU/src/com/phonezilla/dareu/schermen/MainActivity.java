package com.phonezilla.dareu.schermen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
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
import com.phonezilla.dareu.Beginscherm;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

public class MainActivity extends FragmentActivity {

	public static List<ParseObject> groups = new ArrayList<ParseObject>();
	public static final int GROUPLAYOUTHEIGHT = 100;
	public static final int MAXLETTERS = 20;
	String tempid;
	private ArrayList<String> groupsOfUser;
	ListView ListView;
	Context context;
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		groupsOfUser = new ArrayList<String>();
		Button button1 = (Button) findViewById(R.id.button1);
		context = this;
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				makeGroup();
			}
		});
		getGroups();
		/*
		timer = new Timer();
		timer.schedule(new TimerTask() {
		    public void run() {
		    	getGroups(); 
		     }
		  }, 5000);*/
	}

	public void makeGroup() {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Maak een groep");
		alert.setMessage("Voer een groepsnaam in");

		// Set an EditText view to get user input
		final EditText input = new EditText(context);
		input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				MainActivity.MAXLETTERS) });
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				/*
				 * hier moet de groep aangemaakt worden in de database
				 * vervolgens kan hij opgehaald worden door middel van een
				 * refresh
				 */
				final ParseObject group = new ParseObject("Groups");
				group.put("GroupName", value);

				// Save the post and return
				group.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							tempid = group.getObjectId();
							final ParseObject usergroup = new ParseObject(
									"User_Groups");
							usergroup.put("GroupID", tempid);
							usergroup.put("UserID", Beginscherm.userid);
							usergroup.put("Admin", true);

							// Save the post and return
							usergroup.saveInBackground(new SaveCallback() {

								@Override
								public void done(ParseException e) {
									if (e == null) {
										setResult(RESULT_OK);
									} else {
										Toast.makeText(
												context.getApplicationContext(),
												"Error saving: "
														+ e.getMessage(),
												Toast.LENGTH_SHORT).show();
									}
								}
							});
							setResult(RESULT_OK);
						} else {
							Toast.makeText(context.getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				getGroups();
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

	public void getGroups() {

		LinearLayout layout = (LinearLayout) findViewById(R.id.grouplayout);
		if (layout != null) {
			layout.removeAllViews();
		}
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User_Groups");
		query.whereEqualTo("UserID", Beginscherm.userid);

		// Run the query
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> groupList, ParseException e) {
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					// groups.clear();

					for (ParseObject group : groupList) {
						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("Groups");
						query.whereEqualTo("objectId", group.get("GroupID")
								.toString());
						// Run the query
						query.findInBackground(new FindCallback<ParseObject>() {

							@Override
							public void done(List<ParseObject> groupList,
									ParseException e) {
								if (e == null) {
									// If there are results, update the list of
									// posts
									// and notify the adapter
									// groups.clear();

									for (ParseObject group : groupList) {
										groupsOfUser.add(group
												.getString("GroupName"));
										addGroup(group.getString("GroupName"),
												group.getObjectId());
										Log.d("groep",
												group.getString("GroupName")
														+ " is toegevoegd en id = "
														+ group.getObjectId());
									}

									// ((ArrayAdapter<String>)ListView.getAdapter()).notifyDataSetChanged();
								} else {
									Log.d("Post retrieval",
											"Error: " + e.getMessage());
								}
								Log.d("groep",
										Arrays.toString(groupList.toArray())
												+ " is toegevoegd");
								// adapter.notifyDataSetChanged();;
							}
						});
					}

					// ((ArrayAdapter<String>)ListView.getAdapter()).notifyDataSetChanged();
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
				Log.d("groep", Arrays.toString(groupList.toArray())
						+ " is toegevoegd");
				// adapter.notifyDataSetChanged();;
			}
		});

	}

	public void addGroup(String name, String groupid) {

		LinearLayout ll = new LinearLayout(context);
		LinearLayout ll1 = new LinearLayout(context);
		LinearLayout ll2 = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView t1 = new TextView(context);
		TextView t2 = new TextView(context);
		ImageView image = new ImageView(context);

		ll.setBackgroundResource(R.color.listbackground);
		ll.setMinimumHeight(MainActivity.GROUPLAYOUTHEIGHT);
		ll.setWeightSum(1);
		ll.setPadding(30, 0, 0, 0);
		params.setMargins(0, 0, 0, 5);
		ll.setLayoutParams(params);

		ll2.setOrientation(LinearLayout.VERTICAL);
		t1.setTextColor(Color.WHITE);
		t1.setText(name);
		t2.setText("description");

		image.setImageDrawable(image.getResources().getDrawable(
				R.drawable.stamp2));
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

		ll.setOnClickListener(new KlikLuisteraar(groupid));
		LinearLayout layout = (LinearLayout) findViewById(R.id.grouplayout);
		ll.setMinimumWidth(layout.getWidth());
		if (layout != null)
			layout.addView(ll);
	}

}

class KlikLuisteraar implements OnClickListener {
	String groupid;

	public KlikLuisteraar(String groupid_) {
		this.groupid = groupid_;
	}

	@Override
	public void onClick(View v) {
		Context context = v.getContext();
		Intent intent = new Intent(context, GroupPage.class);
		Log.d("sda", groupid + "");
		intent.putExtra("groupid", groupid);
		context.startActivity(intent);

	}

}
