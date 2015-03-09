package com.stemfbla.sentio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class ClubPageActivity extends ActionBarActivity {

    private com.daimajia.slider.library.SliderLayout sliderShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);
        final int clubNumber = Integer.parseInt(getIntent().getExtras().getString("page"));
        String[] description = getResources().getStringArray(R.array.description_array);
        android.widget.TextView desc = (android.widget.TextView) findViewById(R.id.description);
        String[] name = getResources().getStringArray(R.array.cname_array);
        android.widget.TextView nameText = (android.widget.TextView) findViewById(R.id.name);
        final String[] mail = getResources().getStringArray(R.array.cmail_array);
        android.widget.TextView mailText = (android.widget.TextView) findViewById(R.id
                .emailaddress);
        desc.setText(description[clubNumber]);
        nameText.setText(name[clubNumber] + ":");
        mailText.setText(mail[clubNumber]);
        mailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + mail[clubNumber])));
            }
        });
        sliderShow = (com.daimajia.slider.library.SliderLayout) findViewById(R.id.clubSlider);
        java.util.ArrayList<Integer> file_maps = new java.util.ArrayList<Integer>();
        switch(clubNumber) {
            case 0:
                getSupportActionBar().setTitle("Envirostories");
                file_maps.add(R.drawable.enviro1);
                file_maps.add(R.drawable.enviro2);
                file_maps.add(R.drawable.enviro3);
                file_maps.add(R.drawable.enviro4);
                break;
            case 1:
                getSupportActionBar().setTitle("Ethics Bowl");
                file_maps.add(R.drawable.ethics1);
                file_maps.add(R.drawable.ethics2);
                break;
            case 2:
                getSupportActionBar().setTitle("FBLA");
                file_maps.add(R.drawable.fbla1);
                file_maps.add(R.drawable.fbla2);
                file_maps.add(R.drawable.fbla3);
                file_maps.add(R.drawable.fbla4);
                break;
            case 3:
                getSupportActionBar().setTitle("HOSA");
                file_maps.add(R.drawable.hosa1);
                file_maps.add(R.drawable.hosa2);
                break;
            case 4:
                getSupportActionBar().setTitle("American Red Cross");
                file_maps.add(R.drawable.redcross);
                break;
            case 5:
                getSupportActionBar().setTitle("Math Club");
                file_maps.add(R.drawable.math1);
                file_maps.add(R.drawable.math2);
                file_maps.add(R.drawable.math3);
                file_maps.add(R.drawable.math4);
                break;
            case 6:
                getSupportActionBar().setTitle("Model United Nations");
                file_maps.add(R.drawable.model1);
                file_maps.add(R.drawable.model2);
                break;
            case 7:
                getSupportActionBar().setTitle("National Art Honor Society");
                file_maps.add(R.drawable.nahs);
                break;
            case 8:
                getSupportActionBar().setTitle("National Honor Society");
                file_maps.add(R.drawable.nhs);
                break;
            case 9:
                getSupportActionBar().setTitle("Peer Tutor Lab");
                file_maps.add(R.drawable.peer1);
                file_maps.add(R.drawable.peer2);
                break;
            case 10:
                getSupportActionBar().setTitle("PHASER");
                file_maps.add(R.drawable.phaser);
                break;
            case 11:
                getSupportActionBar().setTitle("Quantum Robotics");
                file_maps.add(R.drawable.qu1);
                file_maps.add(R.drawable.qu2);
                file_maps.add(R.drawable.qu3);
                file_maps.add(R.drawable.qu4);
                file_maps.add(R.drawable.qu5);
                break;
            case 12:
                getSupportActionBar().setTitle("Relay for Life");
                file_maps.add(R.drawable.relay);
                break;
            case 13:
                getSupportActionBar().setTitle("Science Bowl");
                file_maps.add(R.drawable.scibowl);
                break;
            case 14:
                getSupportActionBar().setTitle("Technology Student Association");
                file_maps.add(R.drawable.tsa1);
                file_maps.add(R.drawable.tsa2);
                break;
        }
        for(int a = 0; a < file_maps.size(); a++) {
            com.daimajia.slider.library.SliderTypes.TextSliderView textSliderView = new com
                    .daimajia.slider.library.SliderTypes.TextSliderView(this);
            textSliderView
                    .image(file_maps.get(a))
                    .setScaleType(com.daimajia.slider.library.SliderTypes.BaseSliderView.ScaleType.Fit);
            sliderShow.addSlider(textSliderView);
        }
    }
}