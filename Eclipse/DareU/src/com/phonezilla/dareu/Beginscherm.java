package com.phonezilla.dareu;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phonezilla.dareu.objects.User;
import com.phonezilla.dareu.schermen.MainActivity;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

public class Beginscherm extends Activity {

	public static String username;
	public static String userid;
	Button loginbutton;
	EditText usernamefield;
	EditText passwordfield;
	String userName;
	String passWord;
	AlertDialog.Builder alertDialog;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_begin);
		Parse.initialize(this, "jO1gEQOmHYCbpI9S05t2v4jfgAhnWglBTx4Tma8m",
				"nylyg1NjpI5NcW4bOz74xNebQbEEDF9OctbTj5qI");
		getActionBar().hide();
		loginbutton = (Button) findViewById(R.id.button1);
		usernamefield = (EditText) findViewById(R.id.usernamefield);
		passwordfield = (EditText) findViewById(R.id.passwordfield);
		loginbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				userName = usernamefield.getText().toString();
				passWord = passwordfield.getText().toString();
				if (userName.length() > 3 && userName.length() < 15
						&& passWord.length() > 3 && passWord.length() < 15) {
					ParseQuery<ParseUser> query = ParseUser.getQuery();

					query.whereEqualTo("username", userName);
					query.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> userList,
								ParseException e) {
							if (e == null) {
								Log.d("userListCount", "" + userList.size());
								if (userList.size() > 0) {
									ParseUser.logInInBackground(userName,
											passWord, new LogInCallback() {
												public void done(
														ParseUser user,
														ParseException e) {
													if (user != null
															&& e == null) {
														Toast.makeText(
																Beginscherm.this,
																"Logged in",
																Toast.LENGTH_LONG)
																.show();
														userid = user
																.getObjectId();
														nextActivity();
													} else {
														Toast.makeText(
																Beginscherm.this,
																"login failed",
																Toast.LENGTH_LONG)
																.show();
													}
												}
											});
								} else {
									alertDialog = new AlertDialog.Builder(
											Beginscherm.this);
									alertDialog.setTitle("Account creater");
									alertDialog
											.setMessage("This account doesnt exist yet.Do you want to create a new Account?");
									alertDialog
											.setPositiveButton(
													"Ok",
													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(
																DialogInterface dialog,
																int whichButton) {
															addUser();
														}
													});

									alertDialog
											.setNegativeButton(
													"Cancel",
													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(
																DialogInterface dialog,
																int whichButton) {
															// Canceled.
														}
													});

									alertDialog.show();
								}
							} else {
								Log.d("Post retrieval",
										"Error: " + e.getMessage());
							}
						}
					});
				}

			}
		});

	}

	public void nextActivity() {
		username = userName;
		ParseQuery<ParseUser> query = ParseUser.getQuery();

		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> userList, ParseException e) {
				if (e == null) {
					for (ParseUser user : userList) {
						GroupPage.users.add(new User(user.getObjectId()
								.toString(), user.get("username").toString(),
								null));
					}

				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});

		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void addUser() {
		ParseUser user = new ParseUser();
		user.setUsername(userName);
		user.setPassword(passWord);
		try {
			user.signUp();
			userid = user.getObjectId();
			nextActivity();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

	}
}
