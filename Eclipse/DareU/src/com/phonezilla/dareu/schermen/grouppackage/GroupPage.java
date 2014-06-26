package com.phonezilla.dareu.schermen.grouppackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.phonezilla.dareu.Beginscherm;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.handlers.ArrayAdapterItem;
import com.phonezilla.dareu.handlers.OnItemClickListenerListViewItem;
import com.phonezilla.dareu.objects.Collection;
import com.phonezilla.dareu.objects.User;
import com.phonezilla.dareu.schermen.MainActivity;
import com.phonezilla.dareu.schermen.grouppackage.fragments.Accepted;
import com.phonezilla.dareu.schermen.grouppackage.fragments.Completed;
import com.phonezilla.dareu.schermen.grouppackage.fragments.Pending;

public class GroupPage extends FragmentActivity implements TabListener {

	public static ArrayList<Collection> users = new ArrayList<Collection>();
	ActionBar actionbar;
	ViewPager pager;
	public static ArrayList<Collection> currentUsers = new ArrayList<Collection>();
	public AlertDialog alertDialog;
	private String groupid;
	private final int DESCRIPTIONLENGTH = 250;

	public GroupPage() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		groupid = getIntent().getExtras().getString("groupid").toString();
		getCurrentUsers();
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new Adapter(getSupportFragmentManager()));

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				actionbar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tab1 = actionbar.newTab();
		tab1.setText("Accepted");
		tab1.setTabListener(this);
		ActionBar.Tab tab2 = actionbar.newTab();
		tab2.setText("Pending");
		tab2.setTabListener(this);
		ActionBar.Tab tab3 = actionbar.newTab();
		tab3.setText("Completed");
		tab3.setTabListener(this);

		actionbar.addTab(tab1);
		actionbar.addTab(tab2);
		actionbar.addTab(tab3);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.challengemenu, menu);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User_Groups");
		query.whereEqualTo("UserID", Beginscherm.userid);
		query.whereEqualTo("GroupID", getIntent().getExtras().get("groupid"));
		query.whereEqualTo("Admin", false);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					if (userList.size() > 0) {
						MenuItem item = menu.findItem(R.id.adduser);
						item.setVisible(false);
					}
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.adduser:
			showAddUser();
			return true;
		case R.id.users:
			showUserScreen();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void getCurrentUsers() {
		currentUsers.clear();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User_Groups");
		query.whereEqualTo("GroupID", getIntent().getExtras().get("groupid"));
		query.whereNotEqualTo("UserId", Beginscherm.userid);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					for (ParseObject user : userList) {
						ParseQuery<ParseUser> query = ParseUser.getQuery();
						query.whereEqualTo("objectId", user.get("UserID"));
						query.findInBackground(new FindCallback<ParseUser>() {

							@Override
							public void done(List<ParseUser> userList,
									ParseException e) {
								if (e == null) {
									for (ParseUser userr : userList) {
										currentUsers.add(new User(userr
												.getObjectId().toString(),
												userr.get("username")
														.toString(), null));
									}
								} else {
									Log.d("Post retrieval",
											"Error: " + e.getMessage());
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

	public void showUserScreen() {
		ArrayAdapterItem adapter = new ArrayAdapterItem(this,
				R.layout.list_view_row_item, currentUsers);

		ListView listViewItems = new ListView(this);
		listViewItems.setAdapter(adapter);

		AlertDialog.Builder dialog = new AlertDialog.Builder(GroupPage.this);
		dialog.setView(listViewItems).setTitle("Users").show();
	}

	public void showAddUser() {
		ArrayAdapterItem adapter = new ArrayAdapterItem(this,
				R.layout.list_view_row_item, users);

		ListView listViewItems = new ListView(this);
		listViewItems.setAdapter(adapter);
		listViewItems
				.setOnItemClickListener(new OnItemClickListenerListViewItem());
		AlertDialog.Builder dialog = new AlertDialog.Builder(GroupPage.this);
		dialog.setView(listViewItems).setTitle("Users").show();
		dialog.create();

	}

	public void checkUser(final String userid) {
		try {
			Thread.sleep(2000);

			ParseQuery<ParseObject> query = ParseQuery.getQuery("User_Groups");
			query.whereEqualTo("GroupID", groupid);
			query.whereEqualTo("UserID", userid);

			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> userList, ParseException e) {
					if (e == null) {
						Log.d("userlist size", userList.size() + " users");
						if (userList.size() == 0) {
							addUser(userid);

						} else {
							Toast.makeText(getApplicationContext(),
									"User already in group", Toast.LENGTH_SHORT)
									.show();
						}
					} else {
						Log.d("Post retrieval", "Error: " + e.getMessage());
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void makeChallenge() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Maak een Challenge");
		alert.setMessage("Voer een naam,description in");

		// Set an EditText view to get user input

		final EditText input1 = new EditText(this);
		input1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				MainActivity.MAXLETTERS) });
		final EditText input2 = new EditText(this);
		input2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				DESCRIPTIONLENGTH) });
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(input1);
		ll.addView(input2);
		alert.setView(ll);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				String value1 = input1.getText().toString();
				String value2 = input2.getText().toString();
				
				ParseObject challenges = new ParseObject("Challenges");
				challenges.put("ChallengeName", value1);
				challenges.put("Description", value2);
				challenges.put("GroupId", groupid);
				challenges.put("State", 0);
				challenges.put("Acceptees", 0);

				challenges.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							setResult(RESULT_OK);
						} else {
							Toast.makeText(getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}

				});

			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();
	}
	public void addUser(String userid) {
		ParseObject usergroup = new ParseObject("User_Groups");
		usergroup.put("UserID", userid);
		usergroup.put("GroupID", groupid);
		usergroup.put("Admin", false);
		usergroup.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					setResult(RESULT_OK);
					getCurrentUsers();
					Toast.makeText(getApplicationContext(), "User added",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Error saving: " + e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {

	}
}

class Adapter extends FragmentPagerAdapter {

	public Adapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		if (position == 0) {
			fragment = new Accepted();
		}
		if (position == 1) {
			fragment = new Pending();
		}
		if (position == 2) {
			fragment = new Completed();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}
}
