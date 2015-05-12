package com.stemfbla.sentio;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONException;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.FragmentDrawerListener {
    private NavigationDrawerFragment drawerFragment;
    int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);
        ImageView logo = (ImageView) findViewById(R.id.nav_school_logo);
        TextView sName = (TextView) findViewById(R.id.nav_school_name);
        try {
            sName.setText(SchoolActivity.schoolData.getString("school_name"));
            if(!SchoolActivity.schoolData.getString("logo").equals("")) {
                byte[] decodedString = new byte[0];
                decodedString = android.util.Base64.decode(com.stemfbla.sentio.SchoolActivity
                                .schoolData.getString("logo"), android.util.Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                logo.setImageBitmap(decodedByte);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        RelativeLayout header = (RelativeLayout) findViewById(R.id.nav_header_container);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerFragment.closeDrawer();
                new android.app.AlertDialog.Builder(MainActivity.this).setTitle(
                        "Switch or Reload School Data").setMessage(
                        "All school data and cached images will be deleted. After downloading school data another time, you may enter back into the application.").setNegativeButton(
                        "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new WipeData().execute();
                    }
                }).show();
            }
        });
        if(SchoolActivity.offline)
            SnackbarManager.show(Snackbar.with(MainActivity.this).text("Offline mode enabled!"));
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        drawerFragment.clearRecycler();
        view.setBackgroundColor(Color.parseColor("#E8E8E8"));
        displayView(position);
    }
    private void displayView(int position) {
        drawerFragment.closeDrawer();
        if(currentPosition != position) {
            currentPosition = position;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch(position) {
                case 0:
                    fragmentTransaction.replace(R.id.container_body, new HomeFragment()).commit();
                    getSupportActionBar().setTitle("Home");
                    break;
                case 1:
                    CaldroidFragment calFragment = new CaldroidFragment();
                    Bundle args = new Bundle();
                    args.putInt(CaldroidFragment.MONTH, Calendar.getInstance().get(Calendar.MONTH) + 1);
                    args.putInt(CaldroidFragment.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                    calFragment.setArguments(args);
                    fragmentTransaction.replace(R.id.container_body, new CalendarFragment());
                    fragmentTransaction.replace(R.id.calendar, calFragment).commit();
                    calFragment.refreshView();
                    getSupportActionBar().setTitle("Calendar");
                    break;
                case 2:
                    fragmentTransaction.replace(R.id.container_body, new ClubsFragment()).commit();
                    getSupportActionBar().setTitle("Clubs");
                    break;
            }
        }
    }
    private class WipeData extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new android.app.ProgressDialog(MainActivity.this);
            dialog.setMessage("Deleting school data...");
            dialog.setCancelable(false);
            dialog.show();
        }
        protected Integer doInBackground(Integer... in) {
            if(!SchoolActivity.offline) {
                SchoolActivity.schoolData = null;
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().remove(
                        "school_code").commit();
            }
            File cache = getCacheDir();
            File appDir = new File(cache.getParent());
            if(appDir.exists()){
                String[] children = appDir.list();
                for(String s : children)
                    if(!s.equals("lib"))
                        deleteDir(new File(appDir, s));
            }
            return 0;
        }

        public boolean deleteDir(File dir) {
            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success)
                        return false;
                }
            }
            return dir.delete();
        }

        protected void onPostExecute(Integer result) {
            if (dialog.isShowing())
                dialog.dismiss();
            startActivity(new Intent(MainActivity.this, SchoolActivity.class));
            finish();
        }
    }
}