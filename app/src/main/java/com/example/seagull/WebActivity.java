package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {
    private WebView webView;

    private TextView bankName;
    private TextView phoneTxtView;
    private TextView addressTxtView;

    private Button dialBtn;
    private String website;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.web_view);
        dialBtn = findViewById(R.id.dialBtn);
        bankName = findViewById(R.id.bankName);
        phoneTxtView = findViewById(R.id.bankPhone);
        addressTxtView = findViewById(R.id.bankAddress);

        //enables javascript to be used in WebView.
        webView.getSettings().setJavaScriptEnabled(true);

        //ensure clicking links keep opening in the widget rather than opening the browser.
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();

        try {
            bankName.setText(intent.getStringExtra("name"));
        } catch(Exception e){

        }
        try {
            website =intent.getStringExtra("website");
            loadUrl(website);
        } catch(Exception e){

        }
        try {
            phone = intent.getStringExtra("phone");
            phoneTxtView.setText(phone);
        } catch(Exception e){

        }
        try {
            addressTxtView.setText(intent.getStringExtra("address"));
        } catch(Exception e){

        }
        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formattedPhoneNumber = PhoneNumberUtils.formatNumber(phone, "US");
                String digitsPhoneNumber = PhoneNumberUtils.stripSeparators(formattedPhoneNumber);

                Uri uri = Uri.parse("tel:" + digitsPhoneNumber);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });



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
        }else{
            webView.loadUrl("https://www.bentley.edu");
        }
    }
}