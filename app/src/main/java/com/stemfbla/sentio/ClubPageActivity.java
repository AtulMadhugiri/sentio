package com.stemfbla.sentio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class ClubPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.club_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final int clubNumber = getIntent().getExtras().getInt("page");
        android.widget.TextView desc = (android.widget.TextView) findViewById(R.id.description);
        android.widget.TextView nameText = (android.widget.TextView) findViewById(R.id.name);
        android.widget.TextView mailText = (android.widget.TextView) findViewById(R.id.emailaddress);
        try {
            for(int a = 0; a < SchoolActivity.schoolData.getJSONArray("clubs")
                    .length(); a++) {
                if(SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(a).getInt
                        ("club_id") == clubNumber) {
                    desc.setText(SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(a)
                            .getString("description"));
                    nameText.setText(SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(a)
                            .getString("contact_name") + ":");
                    mailText.setText(SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(a)
                            .getString("contact_email"));
                    final int b = a;
                    mailText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                startActivity(new android.content.Intent(android.content.Intent
                                        .ACTION_VIEW, android.net.Uri.parse("mailto:" + com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(b)
                                        .getString("contact_email"))));
                            } catch(org.json.JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    getSupportActionBar().setTitle(SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(a)
                            .getString("club_name"));
                }
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
        java.util.ArrayList<java.io.File> imageList = new java.util.ArrayList<java.io.File>();
        try {
            for(int a = 0; a < SchoolActivity.schoolData.getJSONArray("images")
                    .length(); a++) {
                if(SchoolActivity.schoolData.getJSONArray("images").getJSONObject(a).getInt
                        ("club_id") == clubNumber) imageList.add(SchoolActivity.saveImage(this,
                        SchoolActivity
                        .schoolData.getJSONArray("images")
                        .getJSONObject(a).getString("image_data")));
            }
            if(imageList.size() == 0 && !SchoolActivity.schoolData.getString("logo").equals("")) {
                imageList.add(SchoolActivity.saveImage(this, SchoolActivity.schoolData.getString("logo")));
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        } finally {
            com.daimajia.slider.library.SliderLayout sliderShow = (com.daimajia.slider.library
                    .SliderLayout) findViewById(R.id.clubSlider);
            if(imageList.size() < 2) sliderShow.setDuration(1000000);
            for(int a = 0; a < imageList.size(); a++) {
                com.daimajia.slider.library.SliderTypes.TextSliderView textSliderView = new com
                        .daimajia.slider.library.SliderTypes.TextSliderView(this);
                textSliderView.image(imageList.get(a)).setScaleType(com.daimajia.slider.library.SliderTypes.BaseSliderView.ScaleType.Fit);
                sliderShow.addSlider(textSliderView);
            }
        }
    }
}