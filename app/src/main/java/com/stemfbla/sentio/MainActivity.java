package com.stemfbla.sentio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment
        .NavigationDrawerCallbacks, HomeFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener, ClubsFragment.OnFragmentInteractionListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout mDrawerLayout;
    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(this,
                mDrawerLayout, R.drawable.ic_drawer, 1, 1) {
            public void onDrawerClosed(android.view.View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(android.view.View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch(position) {
            default:
            case 0:
                getSupportActionBar().setTitle("Home");
                fragment = new HomeFragment();
                break;
            case 1:
                getSupportActionBar().setTitle("Calendar");
                fragment = new CalendarFragment();
                break;
            case 2:
                getSupportActionBar().setTitle("Clubs");
                fragment = new ClubsFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(android.net.Uri uri) { }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}