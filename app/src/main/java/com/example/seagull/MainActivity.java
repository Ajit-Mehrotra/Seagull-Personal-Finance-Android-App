package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREATE TAB HOST
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        //INITIALIZE TAB 1 - MONTHLY
        spec = tabs.newTabSpec("tag1");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab1);           //add tab view content
        spec.setIndicator("Monthly");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 1 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag1")) {
                    // START NEW INTENT FOR MONTHLY TAB
                    Intent intent = new Intent(getApplicationContext(), Monthly.class);
                    startActivity(intent);
                }
            }
        });

        //INITIALIZE TAB 2 - GOALS
        spec = tabs.newTabSpec("tag2");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab2);           //add tab view content
        spec.setIndicator("Goals");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 2 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag2")) {
                    // START NEW INTENT FOR GOALS TAB
                    Intent intent = new Intent(getApplicationContext(), Goals.class);
                    startActivity(intent);
                }
            }
        });

        //INITIALIZE TAB 3 - REVIEW
        spec = tabs.newTabSpec("tag3");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab3);           //add tab view content
        spec.setIndicator("Review");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 3 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag3")) {
                    // START NEW INTENT FOR REVIEW TAB
                    Intent intent = new Intent(getApplicationContext(), Review.class);
                    startActivity(intent);
                }
            }
        });

        //INITALIZE TAB 4 - MAP
        spec = tabs.newTabSpec("tag4");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab4);           //add tab view content
        spec.setIndicator("Map");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 4 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag2")) {
                    // START NEW INTENT FOR MAP TAB
                    Intent intent = new Intent(getApplicationContext(), Map.class);
                    startActivity(intent);
                }
            }
        });


    }
}