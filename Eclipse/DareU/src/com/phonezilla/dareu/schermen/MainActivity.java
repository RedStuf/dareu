package com.phonezilla.dareu.schermen;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.TabListener;
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
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.phonezilla.dareu.R;
import com.phonezilla.dareu.schermen.fragments.Challenges;
import com.phonezilla.dareu.schermen.fragments.Friends;
import com.phonezilla.dareu.schermen.fragments.Groups;
import com.phonezilla.dareu.schermen.fragments.Settings;
import com.phonezilla.dareu.schermen.grouppackage.GroupPage;


public class MainActivity extends FragmentActivity implements View.OnClickListener, TabListener {

    private ViewPager pager;
    private ActionBar actionbar;
    public static List groups = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new Adapter(getSupportFragmentManager()));
        
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new Adapter(getSupportFragmentManager()));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        actionbar=getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1= actionbar.newTab();
        tab1.setText("Groups");
        tab1.setTabListener(this);
        ActionBar.Tab tab2= actionbar.newTab();
        tab2.setText("Challenges");
        tab2.setTabListener(this);
        ActionBar.Tab tab3= actionbar.newTab();
        tab3.setText("Friends");
        tab3.setTabListener(this);
        ActionBar.Tab tab4= actionbar.newTab();
        tab4.setText("Settings");
        tab4.setTabListener(this);

        actionbar.addTab(tab1);
        actionbar.addTab(tab2);
        actionbar.addTab(tab3);
        actionbar.addTab(tab4);

        //Parse.initialize(this, "jO1gEQOmHYCbpI9S05t2v4jfgAhnWglBTx4Tma8m", "nylyg1NjpI5NcW4bOz74xNebQbEEDF9OctbTj5qI");
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

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
                    @Override
					public void onClick(DialogInterface dialog, int whichButton) {
                        int id = 1;
                        String value = input.getText().toString();
                        /* hier moet de groep aangemaakt worden in de database 
                         * vervolgens kan hij opgehaald worden 
                         * door middel van een refresh
                         */
                        ParseObject groups = new ParseObject("Groups");
                        groups.put("GroupName", value);
                                           
                        // Save the post and return
                        groups.saveInBackground(new SaveCallback () {
                       
                          @Override
                          public void done(ParseException e) {
                            if (e == null) {
                              setResult(RESULT_OK);
                            } else {
                              Toast.makeText(getApplicationContext(),
                              "Error saving: " + e.getMessage(),
                                     Toast.LENGTH_SHORT)
                                     .show();
                            }
                          }
                       
                        });
                        
                        
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
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
        Intent intent = new Intent(this,GroupPage.class);
        startActivity(intent);
    }
}

/*
 * dit is de adapter om pagina's te swipen
 */
class Adapter extends FragmentPagerAdapter {

    public Adapter(FragmentManager fm) {

        super(fm);
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

