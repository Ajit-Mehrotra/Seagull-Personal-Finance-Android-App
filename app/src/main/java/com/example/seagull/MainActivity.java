package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    }
}