package com.phonezilla.dareu.schermen;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.fragments.Challenges;
import com.phonezilla.dareu.schermen.fragments.Friends;
import com.phonezilla.dareu.schermen.fragments.Groups;
import com.phonezilla.dareu.schermen.fragments.Settings;
import com.phonezilla.dareu.schermen.grouppackage.Group;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager pager;
    public static List groups = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new Adapter(this,getSupportFragmentManager()));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newgroup:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Maak een groep");
                alert.setMessage("Voer een groepsnaam in");

// Set an EditText view to get user input
                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int id = 1;
                        String value = input.getText().toString();
                        groups.add(new Group());


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,Group.class);
        startActivity(intent);
    }
}

/*
 * dit is de adapter om pagina's te swipen
 */
class Adapter extends FragmentPagerAdapter {

    Object x;
    public Adapter(Object x ,FragmentManager fm) {

        super(fm);
        this.x = x;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0)
        {
            fragment = new Groups();
        }
        if(position == 1)
        {
            fragment = new Challenges();
        }
        if(position == 2)
        {
            fragment = new Friends();
        }
        if(position == 3)
        {
            fragment = new Settings();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
