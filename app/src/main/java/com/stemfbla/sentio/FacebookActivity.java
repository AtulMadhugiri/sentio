package com.stemfbla.sentio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class FacebookActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.stemfbla.sentio.R.layout.activity_facebook);

        android.webkit.WebView webView = (android.webkit.WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.facebook.com/sentioapplication/timeline");
        getSupportActionBar().setTitle("Facebook Page");
        webView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
