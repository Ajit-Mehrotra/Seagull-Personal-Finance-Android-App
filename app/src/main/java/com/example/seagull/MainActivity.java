package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {
    ArrayList<LineItem> arrayList = new ArrayList<LineItem>();
    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        //use adapater as bridge to connect arraylist and listview (UI & logic)
        adapter = new MyAdapter(this, arrayList);
        listView.setAdapter(adapter);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            arrayList.add(new LineItem(20.4, "Crunchyroll Subscription", sdf.parse("2023-04-12"),false));
            arrayList.add(new LineItem(18.21, "WSJ Subscription", sdf.parse("2033-04-12"), false));
            arrayList.add(new LineItem(10.42, "Cereal", sdf.parse("2021-03-12"), false));
            arrayList.add(new LineItem(2.40, "Donation to Homeless", sdf.parse("2022-01-12"), false));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
            if (tabId.equals("tag4")) {
                // START NEW INTENT FOR MAP TAB
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        }
        });

    }
}