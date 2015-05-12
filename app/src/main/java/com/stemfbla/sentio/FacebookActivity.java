package com.stemfbla.sentio;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import org.json.JSONException;


public class FacebookActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.stemfbla.sentio.R.layout.activity_facebook);
        Toolbar toolbar = (Toolbar) findViewById(R.id.fb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Facebook Page");
        WebView webView = (android.webkit.WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        try {
            webView.loadUrl(SchoolActivity.schoolData.getString("facebook"));
        } catch(JSONException e) {
            e.printStackTrace();
        }
        webView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}