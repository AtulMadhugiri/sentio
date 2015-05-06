package com.stemfbla.sentio;
import android.app.Activity;
import android.os.Handler;
import android.content.Intent;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** App begins here **/
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start Login page
                startActivity(new Intent(SplashActivity.this, SchoolActivity.class));
                finish();
            }
        }, (getSharedPreferences("sharedPrefs", /** Skip splash screen if logged out */
                android.content.Context.MODE_PRIVATE).contains("school_code") ? 1500 : 0));
    }
}