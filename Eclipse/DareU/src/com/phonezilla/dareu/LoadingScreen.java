package com.phonezilla.dareu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phonezilla.dareu.objects.Challenge;
import com.phonezilla.dareu.objects.Collection;
import com.phonezilla.dareu.objects.Group;
import com.phonezilla.dareu.objects.User;
import com.phonezilla.dareu.schermen.MainActivity;

public class LoadingScreen extends Activity {
	private boolean loading = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadingscreen);
		Parse.initialize(this, "jO1gEQOmHYCbpI9S05t2v4jfgAhnWglBTx4Tma8m",
				"nylyg1NjpI5NcW4bOz74xNebQbEEDF9OctbTj5qI");
		Load();
	}
	public void Load()
	{
			// load users
		class Task extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... params) {
					try{
					loadUsers();
					loadGroups();
					Thread.sleep(3000);
					loadAcceptedChallenges();
					loadPendingChallenges();
					}catch(Exception e){e.printStackTrace();}

					
				return null;
			}
			@Override
	        protected void onPostExecute(String result) {
				Log.d("arraysize groups", MainActivity.groups.size()+"");
	        	NextActivity();
	        }

			
		}	    	

		new Task().execute("");
			
	}
	public static boolean loadUsers()
	{

		final ArrayList<Collection> temp = new ArrayList<Collection>();
		ParseQuery<ParseUser> userquery = ParseUser.getQuery();

		userquery.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> userList, ParseException e) {
				if (e == null) {
					for (ParseUser user : userList) {
						temp.add(new User(user.getObjectId()
								.toString(), user.get("username").toString(),
								null));
					}
					MainActivity.users.clear();
					MainActivity.users.addAll(temp);
					

				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});
		return true;
	}
	public static boolean loadGroups()
	{
		final ArrayList<Collection> temp = new ArrayList<Collection>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User_Groups");
		query.whereEqualTo("UserID", Beginscherm.userid);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> groupList, ParseException e) {
				if (e == null) {
					for (ParseObject group : groupList) {
						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("Groups");
						query.whereEqualTo("objectId", group.get("GroupID")
								.toString());
						query.findInBackground(new FindCallback<ParseObject>() {

							@Override
							public void done(List<ParseObject> groupList,
									ParseException e) {
								if (e == null) {
									for (ParseObject group : groupList) {
										temp.add(new Group(group.getObjectId(),group.getString("GroupName"),null));
										ParseQuery<ParseObject> query = ParseQuery.getQuery("User_Groups");
										query.whereEqualTo("GroupID", group.getObjectId());
										query.findInBackground(new FindCallback<ParseObject>() {
											public void done(List<ParseObject> userList, ParseException e) {
												if (e == null) {
														((Group)temp.get(temp.size()-1)).setUserCount(userList.size());
												}
											}
										});
										
									}
									MainActivity.groups.clear();
									MainActivity.groups.addAll(temp);
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
		return true;
	}
	public static boolean loadAcceptedChallenges()
	{
		for(final Collection group : MainActivity.groups)
		{ 
			((Group)group).clearAccepted();
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenges");
			query.whereEqualTo("GroupId", group.itemId);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> challengeList, ParseException e) {
					if (e == null) {
							for (final ParseObject challenge : challengeList) {
								
								ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge_User");
								query.whereEqualTo("ChallengeID", challenge.getObjectId());
								query.whereEqualTo("Accepted", true);
								query.findInBackground(new FindCallback<ParseObject>() {
									public void done(List<ParseObject> userList, ParseException e) {
										if (e == null) {
											if(userList.size() >= ((Group)group).getUserCount()/2)
											{
												((Group)group).setAccepted(new Challenge(challenge.getObjectId(),challenge.getString("ChallengeName"),challenge.getString("Description")));
											}
										} else {
											Log.d("Post retrieval", "Errorgvvgvhjgvjhgvr: " + e.getMessage());
										}
									}});
							}
					} else {
						Log.d("Post retrieval", "hoior: " + e.getMessage());
					}
				}
			});
			
		}
		return true;
		
	}
	public static boolean loadPendingChallenges()
	{
		for(final Collection group : MainActivity.groups)
		{
			((Group)group).clearPending();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenges");
			query.whereEqualTo("GroupId", group.itemId);
			query.findInBackground(new FindCallback<ParseObject>() {
	
				@Override
				public void done(List<ParseObject> challengeList, ParseException e) {
					if (e == null) {
							for (final ParseObject challenge : challengeList) {
								
								ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge_User");
								query.whereEqualTo("ChallengeID", challenge.getObjectId());
								query.whereEqualTo("Accepted", true);
								
								query.findInBackground(new FindCallback<ParseObject>() {
									public void done(List<ParseObject> userList, ParseException e) {
										if (e == null) {
											Log.d("userlist size", userList.size()+"");
											if(userList.size() < ((Group)group).getUserCount()/2)
											{
												((Group)group).setPending(new Challenge(challenge.getObjectId(),challenge.getString("ChallengeName"),challenge.getString("Description")));
											}
										} else {
											Log.d("Post retrieval", "Error: " + e.getMessage());
										}
									}});
									
							}
							
						
					} else {
						Log.d("Post retrieval", "Error: " + e.getMessage());
					}

				}
			});


			
		}
		return true;
	}
	public void NextActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
