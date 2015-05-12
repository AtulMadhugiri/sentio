package com.stemfbla.sentio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MemberActivity extends ActionBarActivity {
    int objNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mem_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        objNum = getIntent().getExtras().getInt("position");
        try {
            getSupportActionBar().setTitle(
                    SchoolActivity.schoolData.getJSONArray("faculty").getJSONObject(
                            objNum).getString("faculty_name"));
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
        TextView roleText = (android.widget.TextView) findViewById(com.stemfbla.sentio.R.id
                .roleText);
        byte[] decodedString = new byte[0];
        try {
            decodedString = android.util.Base64.decode(com.stemfbla.sentio.SchoolActivity
                    .schoolData.getJSONArray("faculty").getJSONObject(objNum).getString("image_data"),
                    android.util.Base64
                    .DEFAULT);
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
        android.graphics.Bitmap decodedByte = android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        android.widget.ImageView image = (android.widget.ImageView) findViewById(com.stemfbla.sentio.R.id.imageView3);
        image.setImageBitmap(decodedByte);
        try {
            roleText.setText(SchoolActivity.schoolData.getJSONArray("faculty")
                    .getJSONObject(objNum).getString("role"));
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(android.view.Menu.NONE,
                1,
                android.view.Menu.NONE,
                "Teacher Email")
                .setIcon(R.drawable.email)
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(android.view.Menu.NONE,
                0,
                android.view.Menu.NONE,
                "Teacher Biography")
                .setIcon(com.stemfbla.sentio.R.drawable.information)
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item.toString().equals("Teacher Biography")) {
            try {
                new android.app.AlertDialog.Builder(com.stemfbla.sentio.MemberActivity.this)
                        .setTitle(com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("faculty").getJSONObject
                                (objNum).getString("faculty_name"))
                        .setMessage(com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("faculty").getJSONObject
                                (objNum).getString("biography"))
                        .show();
            } catch(org.json.JSONException e) {
                e.printStackTrace();
            }
        } else if(item.toString().equals("Teacher Email")) {
            try {
                startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android
                        .net.Uri.parse("mailto:" + SchoolActivity.schoolData
                                        .getJSONArray("faculty").getJSONObject(objNum).getString("email")
                        )));
            } catch(org.json.JSONException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
