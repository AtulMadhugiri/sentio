package com.stemfbla.sentio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;


public class FacultyActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.fac_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ListView facultyList = (android.widget.ListView) findViewById(com.stemfbla
                .sentio.R
                .id.facultyList);
        String[] values = new String[0];
        try {
            values = new String[com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray
                    ("faculty").length()];
            for(int a = 0; a < values.length; a++) {
                values[a] = com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("faculty")
                        .getJSONObject(a).getString("faculty_name");
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(this,
                R.layout.list_clubs, values);
        facultyList.setAdapter(adapter);
        facultyList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position,
                                    long id) {
                int pos = -1;
                try {
                    for(int a = 0; a < com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("faculty").length(); a++) {
                        if(SchoolActivity.schoolData.getJSONArray("faculty").getJSONObject(a)
                                .getString("faculty_name").equals(((android.widget.TextView)view)
                                        .getText().toString())) {
                            pos = a;
                            break;
                        }
                    }
                } catch(org.json.JSONException e) {
                    e.printStackTrace();
                }
                android.content.Intent open = new android.content.Intent(FacultyActivity.this, MemberActivity
                        .class);
                open.putExtra("position", pos);
                startActivity(open);
            }
        });
    }
}
