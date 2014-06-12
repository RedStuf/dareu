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


public class MainActivity extends FragmentActivity implements TabListener {

    private ViewPager pager;
    private ActionBar actionbar;
    public static List groups = new ArrayList();
    public static final int GROUPLAYOUTHEIGHT =100;
    public static final int MAXLETTERS = 20;

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

