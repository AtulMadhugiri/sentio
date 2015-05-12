package com.stemfbla.sentio;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SchoolActivity.class));
                finish();
            }
        }, PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).contains("school_code") ? 1000 : 0);
    }
}