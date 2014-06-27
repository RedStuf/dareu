package com.phonezilla.dareu.schermen.grouppackage.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import android.provider.MediaStore;
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

	public static final int TAKE_PHOTO_REQUEST = 0;
	public static final int TAKE_VIDEO_REQUEST = 1;
	public static final int PICK_PHOTO_REQUEST = 2;
	public static final int PICK_VIDEO_REQUEST = 3;

	public static final int MEDIA_TYPE_IMAGE = 4;
	public static final int MEDIA_TYPE_VIDEO = 5;
	public static final int FILE_SIZE_LIMIT = 1024 * 1024 * 10;

	public static final String TAG = ChallengeDetails.class.getSimpleName();
	
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
		setContentView(R.layout.challenge_description);
		addButtons();
		mDialogListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface mDialogListener, int which) {
				switch (which) {
				case 0: // Take Picture
					Intent takePhotoIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					mMediaUri = getOutputMediaFile(MEDIA_TYPE_IMAGE);
					if (mMediaUri == null) {
						// Display toast with error message
						Toast.makeText(
								ChallengeDetails.this,
								"There was a problem accessing your device's external storage",
								Toast.LENGTH_LONG).show();
					}
					takePhotoIntent
							.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
					startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
					break;

				case 1: // Take video
					Intent videoIntent = new Intent(
							MediaStore.ACTION_VIDEO_CAPTURE);
					mMediaUri = getOutputMediaFile(MEDIA_TYPE_VIDEO);
					if (mMediaUri == null) {
						// Display toast with error message
						Toast.makeText(
								ChallengeDetails.this,
								"There was a problem accessing your device's external storage",
								Toast.LENGTH_LONG).show();
					} else {
						videoIntent
								.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
						videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,
								10);
						videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
						startActivityForResult(videoIntent, TAKE_VIDEO_REQUEST);
					}

					break;

				case 2: // choose picture
					Intent choosePhotoIntent = new Intent(
							Intent.ACTION_GET_CONTENT);
					choosePhotoIntent.setType("image/*");
					startActivityForResult(choosePhotoIntent,
							PICK_PHOTO_REQUEST);
					break;

				case 3: // choose video
					Intent chooseVideoIntent = new Intent(
							Intent.ACTION_GET_CONTENT);
					chooseVideoIntent.setType("video/*");
					startActivityForResult(chooseVideoIntent,
							PICK_VIDEO_REQUEST);
					break;

				}
				Button viewproof = (Button)findViewById(R.id.previewEvedince_button);
				viewproof.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						AlertDialog.Builder alert = new AlertDialog.Builder(ChallengeDetails.this);

						alert.setTitle("Proof");
						ImageView view = new ImageView(ChallengeDetails.this);
						view.setImageBitmap(bp);
						
						alert.setView(view);

						alert.show();
						
					}
				});
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
			if(requestCode == TAKE_PHOTO_REQUEST)
			{
				try {
					stream = getContentResolver().openInputStream(data.getData());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        bp = BitmapFactory.decodeStream(stream);
			}
			// add it to gallery
			
			if (requestCode == PICK_PHOTO_REQUEST) {
				try {
					stream = getContentResolver().openInputStream(data.getData());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        bp = BitmapFactory.decodeStream(stream);
				
				if (data == null) {
					Toast.makeText(ChallengeDetails.this,
							"Sorry, there was an error !", Toast.LENGTH_LONG)
							.show();
				} else {
					mMediaUri = data.getData();
				}

				if (requestCode == PICK_PHOTO_REQUEST) {
					int fileSize = 0;
					Log.i(TAG, "Media: URI" + mMediaUri);
					InputStream inputStream = null;

					try {
						inputStream = getContentResolver().openInputStream(
								mMediaUri);
						fileSize = inputStream.available();
					} catch (FileNotFoundException e) {
						Toast.makeText(ChallengeDetails.this,
								R.string.error_opening_file, Toast.LENGTH_LONG)
								.show();
						return;
					} catch (IOException e) {
						Toast.makeText(ChallengeDetails.this,
								R.string.error_opening_file, Toast.LENGTH_LONG)
								.show();
						return;
					} finally {
						try {
							inputStream.close();
						} catch (IOException e) {
							// Intentionally blank//
						}

						if (fileSize >= FILE_SIZE_LIMIT) {
							Toast.makeText(ChallengeDetails.this,
									R.string.error_file_size_too_large,
									Toast.LENGTH_LONG).show();
							return;
						}
					}
				}
			}

			else {
				Intent mediaScanIntent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				mediaScanIntent.setData(mMediaUri);
				sendBroadcast(mediaScanIntent);
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
		String challengeid = getIntent().getExtras().getString("challengeid");
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
