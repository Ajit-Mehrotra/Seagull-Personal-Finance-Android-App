package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.web_view);

        //enables javascript to be used in WebView.
        webView.getSettings().setJavaScriptEnabled(true);

        //ensure clicking links keep opening in the widget rather than opening the browser.
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String url = intent.getStringExtra("website");
            webView.loadUrl(url);


    }
    //the back key on emulator navigates back to the previous web page
    @Override
    public void onBackPressed() {
        webView = (WebView) findViewById(R.id.web_view);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }
}