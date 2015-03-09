package com.stemfbla.sentio;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment
        .NavigationDrawerCallbacks, HomeFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener,
        ClubsFragment.OnFragmentInteractionListener {

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
        Fragment fragment = null;
        boolean okay = true;
        switch(position) {
            default:
            case 0:
                getSupportActionBar().setTitle("Home");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
                break;
            case 1:
                getSupportActionBar().setTitle("Calendar");
                com.roomorama.caldroid.CaldroidFragment calFragment = new com.roomorama.caldroid
                        .CaldroidFragment();
                Bundle args = new Bundle();
                java.util.Calendar cal = java.util.Calendar.getInstance();
                args.putInt(com.roomorama.caldroid.CaldroidFragment.MONTH, cal.get(java.util.Calendar.MONTH) + 1);
                args.putInt(com.roomorama.caldroid.CaldroidFragment.YEAR, cal.get(java.util.Calendar.YEAR));
                calFragment.setArguments(args);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new CalendarFragment())
                        .commit();
                okay = false;
                fragmentManager.beginTransaction()
                        .replace(R.id.calendar, calFragment)
                        .commit();
                final com.roomorama.caldroid.CaldroidListener listener = new com.roomorama.caldroid.CaldroidListener() {
                    @Override
                    public void onSelectDate(java.util.Date date, android.view.View view) {
                        android.widget.Toast.makeText(getApplicationContext(), date.toString(),
                                android.widget.Toast.LENGTH_SHORT).show();
                    }
                };
                calFragment.setCaldroidListener(listener);
                calFragment.refreshView();
                break;
            case 2:
                getSupportActionBar().setTitle("Clubs");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ClubsFragment())
                        .commit();
                break;
        }
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