package com.alternativeheroes.hackgt.whereat;

import android.graphics.Color;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;


public class MainActivity extends FragmentActivity implements ListView.OnItemClickListener {

    private LoginFragment loginFrag;

    private DrawerLayout mDrawerLayout;
    private ListView     mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if (savedInstanceState == null) {
            loginFrag = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, loginFrag)
                    .commit();
        } else {
            loginFrag = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content);

        }
        */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList   = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new BlurbAdapter(this));
        mDrawerList.setOnItemClickListener(this);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("ASLCKH", "SLDJC");
    }

    public void onBlurbChange(int blurbIndex, boolean checked, CompoundButton btnView) {
        Log.i("Hello, ", "Wrodl!");
    }

    public void onGroupItemClick(View v) {

    }
}
