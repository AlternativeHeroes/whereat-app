package com.alternativeheroes.hackgt.whereat;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity
        implements AdapterView.OnItemSelectedListener {

    private LoginFragment loginFrag;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView     mDrawerList;
    private ListView     mEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            loginFrag = new LoginFragment();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.content, loginFrag)
//                    .commit();
//        } else {
//            loginFrag = (LoginFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.content);
//
//        }

        getActionBar().setIcon(R.drawable.action_search);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.empty,  /* "open drawer" description */
                R.string.empty  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);



        mDrawerList   = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new BlurbAdapter(this));

        mEventList    = (ListView) findViewById(R.id.events_list);
        mEventList.setAdapter(new EventsAdapter(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        ServerAPI.setCurrentOrdering(item.getTitleCondensed().toString());
        // Server will not order
        // ServerAPI.fetchEventsList();
        ServerAPI.switchOut();
        updateEventsListAdapter();

        return true;
    }

    // On blurb change
    public void onBlurbChange(int blurbIndex, boolean checked, CompoundButton btnView) {
        //Toast.makeText(this, ServerAPI.getBlurbs()[blurbIndex], Toast.LENGTH_SHORT).show();

        if (checked) {
            ((LinearLayout) btnView.getParent()).setBackgroundColor(getResources().getColor(R.color.blurb_selected));
            ServerAPI.activateBlurb(blurbIndex);
        }
        else {
            ((LinearLayout) btnView.getParent()).setBackgroundColor(getResources().getColor(R.color.blurb_unselected));
            ServerAPI.deactivateBlurb(blurbIndex);
        }
        ServerAPI.fetchEventsList();
        updateEventsListAdapter();
    }

    // On Location Change
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (ServerAPI.getCurrentLocation() == null
                || !ServerAPI.getCurrentLocation().equals(ServerAPI.getLocations()[position])) {
            //Toast.makeText(this, ServerAPI.getLocations()[position], Toast.LENGTH_SHORT).show();
            ServerAPI.setCurrentLocation(position);
            ServerAPI.fetchEventsList();
            updateEventsListAdapter();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    private void updateEventsListAdapter() {
        ((BaseAdapter) mEventList.getAdapter()).notifyDataSetChanged();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onUpVote(View v, int index) {
        ImageView image = ((ImageView) v.findViewById(R.id.upVote));
        Event currEvent = ServerAPI.getEvents().get(index);

        if (currEvent.isLiked()) {
            image.setImageResource(R.drawable.smyle_unselected);
        }
        else {
            image.setImageResource(R.drawable.smyle_selected);
        }
        currEvent.toggleLike();
        updateEventsListAdapter();
    }

    public void onMoreInfoSelect(View v, int index) {

    }
}
