package com.phonezilla.dareu.schermen;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.phonezilla.dareu.Beginscherm;
import com.phonezilla.dareu.LoadingScreen;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.objects.Collection;
import com.phonezilla.dareu.objects.Group;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;

public class MainActivity extends FragmentActivity {

	public static ArrayList<Collection> groups = new ArrayList<Collection>();
	public static ArrayList<Collection> users = new ArrayList<Collection>();
	public static final int GROUPLAYOUTHEIGHT = 100;
	public static final int MAXLETTERS = 20;
	public static final int REFRESHTIME = 4000;
	String tempid;
	ListView ListView;
	Context context;
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		Button button1 = (Button) findViewById(R.id.button1);
		context = this;
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				makeGroup();
			}
		});
		final Handler handler = new Handler();
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				//update();
				handler.postDelayed(this, 10000);
			}
		};
		handler.post(r);
		final Handler handler2 = new Handler();
		Runnable r2 = new Runnable() {
			
			@Override
			public void run() {
				getGroups();
				handler2.postDelayed(this, MainActivity.REFRESHTIME);
			}
		};
		handler2.postDelayed(r2, 1000);
	}
	public void update()
	{
		try{
			LoadingScreen.loadUsers();
			LoadingScreen.loadGroups();
			Thread.sleep(3000);
			LoadingScreen.loadAcceptedChallenges();
			LoadingScreen.loadPendingChallenges();
			Thread.sleep(3000);
			Log.d("updated", users.size()+" updated");
			Log.d("updated", groups.size()+"updated");
			for(Collection group : groups)
			{
				Log.d("updated",group.itemName + " = "	+((Group)group).getAccepted().size()+"accep");
				Log.d("updated",group.itemName + " = "	+((Group)group).getPending().size()+"pend");
			}
			}catch(Exception e){e.printStackTrace();}
	}

	public void makeGroup() {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Create a poule");
		alert.setMessage("Insert a name");

		// Set an EditText view to get user input
		final EditText input = new EditText(context);
		input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				MainActivity.MAXLETTERS) });
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				
				final ParseObject group = new ParseObject("Groups");
				group.put("GroupName", value);

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
					}
				});

		alert.show();

	}

	public void getGroups() {

		LinearLayout layout = (LinearLayout) findViewById(R.id.grouplayout);
		if (layout != null) {
			layout.removeAllViews();
		}
		for(Collection group : MainActivity.groups)
		{
			addGroup(group);
		}
	}

	public void addGroup(Collection group ) {

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
		t1.setText(group.itemName);
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

		ll.setOnClickListener(new KlikLuisteraar(group));
		LinearLayout layout = (LinearLayout) findViewById(R.id.grouplayout);
		ll.setMinimumWidth(layout.getWidth());
		if (layout != null)
			layout.addView(ll);
	}
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.refresh:
			update();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

class KlikLuisteraar implements OnClickListener {
	Collection group;

	public KlikLuisteraar(Collection group_) {
		this.group = group_;
	}

	@Override
	public void onClick(View v) {
		Context context = v.getContext();
		Intent intent = new Intent(context, GroupPage.class);
		Log.d("sda", group + "");
		intent.putExtra("group", group);
		context.startActivity(intent);

	}

}
