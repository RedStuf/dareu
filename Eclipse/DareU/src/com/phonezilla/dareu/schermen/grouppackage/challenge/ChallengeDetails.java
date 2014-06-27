package com.phonezilla.dareu.schermen.grouppackage.challenge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
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

	public static final int MEDIA_TYPE_IMAGE = 4;
	public static final int MEDIA_TYPE_VIDEO = 5;
	public static final int FILE_SIZE_LIMIT = 1024 * 1024 * 10;

	public static final String TAG = ChallengeDetails.class.getSimpleName();
	protected static final int PICK_PHOTO_REQUEST = 0;

	ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
	Bitmap bp;

	protected Uri mMediaUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * challengeTitle = (TextView) findViewById(R.id.challengeTitleView);
		 * challengeDescription =
		 * (TextView)findViewById(R.id.challengeDescriptionView); challengeScore
		 * = (TextView) findViewById(R.id.scoreView);
		 */
		ParseACL defaultACL = new ParseACL();

		/*
		 * If you would like all objects to be private by default, remove this
		 * line
		 */
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
		setContentView(R.layout.challenge_description);
		addButtons();
		TextView challengename = (TextView) findViewById(R.id.challengeNameView);
		TextView challengedescription = (TextView) findViewById(R.id.challengeDescriptionView);
		Button viewproof = (Button) findViewById(R.id.previewEvedince_button);
		viewproof.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseQuery<ParseObject> query = ParseQuery
						.getQuery("Challenges");
				query.whereEqualTo("objectId", getIntent().getExtras()
						.get("id").toString());
				query.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> challengeList,
							ParseException e) {

						InputStream stream = null;
						if (e == null) {
							for (ParseObject challenge : challengeList) {
								AlertDialog.Builder alert = new AlertDialog.Builder(
										ChallengeDetails.this);

								alert.setTitle("Proof");
								ImageView view = new ImageView(
										ChallengeDetails.this);
								try {
									stream = new ByteArrayInputStream(
											((ParseFile) challenge
													.get("Proof"))
													.getData());
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								view.setImageBitmap(BitmapFactory
										.decodeStream(stream));
								
								//view.setImageBitmap(bp);
								alert.setView(view);

								alert.show();
							}
						} else {
							Log.d("Post retrieval",
									"Error: " + e.getMessage());
						}
					}
				});

			}
		});
		challengename.setText(getIntent().getExtras().get("name").toString());
		// challengedescription.setText(getIntent().getExtras().get("description").toString());
		mDialogListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface mDialogListener, int which) {

				Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
				choosePhotoIntent.setType("image/*");
				startActivityForResult(choosePhotoIntent, PICK_PHOTO_REQUEST);

				
			}

			private Uri getOutputMediaFile(int mediaType) {
				// TODO Auto-generated method stub

				String appName = ChallengeDetails.this
						.getString(R.string.app_name);
				if (isExternalStorageAvailable()) {
					// 1 Get the external storage directory
					File mediaStorageDir = new File(
							Environment
									.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
							appName);

					// Creates a subdirectory
					if (!mediaStorageDir.exists()) {
						if (!mediaStorageDir.mkdirs()) {
							Log.e(TAG, "Failed to create directory");
							return null;
						}
					}

					// 3. Create a File name

					// 4. Create the file
					File mediaFile;
					Date now = new Date();
					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHss",
							Locale.ENGLISH).format(now);

					String path = mediaStorageDir.getPath() + File.separator;
					if (mediaType == MEDIA_TYPE_IMAGE) {
						mediaFile = new File(path = "IMG_" + timeStamp
								+ ".jpeg");

					} else if (mediaType == MEDIA_TYPE_VIDEO) {
						mediaFile = new File(path = "VID_" + timeStamp + ".mp4");
					} else {
						return null;
					}

					Log.d(TAG, "File" + Uri.fromFile(mediaFile));

					// 5. Return the file's URI
					return Uri.fromFile(mediaFile);

				}
				return mMediaUri;
			}

			boolean isExternalStorageAvailable() {
				String state = Environment.getExternalStorageState();

				if (state.equals(Environment.MEDIA_MOUNTED)) {
					return true;
				} else {
					return false;
				}

			}
		};

	}

	public void uploadStatus() {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		InputStream stream = null;

		if (resultCode == RESULT_OK) {
			try {
				stream = getContentResolver().openInputStream(data.getData());
				bp = BitmapFactory.decodeStream(stream);

				ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
				bp.compress(Bitmap.CompressFormat.PNG, 100, stream1);
				byte[] image = stream1.toByteArray();
				final ParseFile file = new ParseFile("androidbegin.png", image);
				
				file.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							setResult(RESULT_OK);
							
							
							Toast.makeText(getApplicationContext(), "User added",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				Thread.sleep(10000);
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"Challenges");
				 
				ParseObject usergroup;
				try {
					usergroup = query.get(getIntent().getExtras().get("id").toString());
					usergroup.put("Proof", file);
					ParseACL defaultACL = new ParseACL();
					defaultACL.setPublicReadAccess(true);
					usergroup.setACL(defaultACL);
					usergroup.saveInBackground();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		} else if (resultCode != RESULT_CANCELED) {
			Toast.makeText(this, "Sorry, there was an error !",
					Toast.LENGTH_LONG).show();
		}
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
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
		case R.id.action_camera:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ChallengeDetails.this);
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

	public void addButtons() {
		final LinearLayout ll = (LinearLayout) findViewById(R.id.acceptlayout);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge_User");
		query.whereEqualTo("UserID", Beginscherm.userid);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					if (userList.size() <= 0) {

						ll.setVisibility(LinearLayout.VISIBLE);
					}
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}
			}
		});
		ImageButton accept = (ImageButton) findViewById(R.id.accept);
		ImageButton decline = (ImageButton) findViewById(R.id.decline);
		String challengeid = getIntent().getExtras().getString("id");
		accept.setOnClickListener(new myOnClickListener(true, challengeid, ll));
		decline.setOnClickListener(new myOnClickListener(false, challengeid, ll));

	}

}

class myOnClickListener implements View.OnClickListener {
	boolean accepted;
	String challengeid;
	View view;

	public myOnClickListener(boolean accepted, String challengeid, View view) {
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
