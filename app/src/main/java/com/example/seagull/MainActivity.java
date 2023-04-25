package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<LineItem> expenseList = new ArrayList<>();
    ArrayList<LineItem> earningsList = new ArrayList<>();

    ListView expenseView;
    ListView earningsView;
    ExpensesAdapter expenseAdapter;
    EarningsAdapter earningsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseView = findViewById(R.id.expenseView);
        earningsView = findViewById(R.id.earningView);

        //use adapater as bridge to connect arraylist and listview (UI & logic)
        expenseAdapter = new ExpensesAdapter(this, expenseList);
        expenseView.setAdapter(expenseAdapter);

        earningsAdapter = new EarningsAdapter(this, earningsList);
        earningsView.setAdapter(earningsAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            expenseList.add(new LineItem(20.4, "Crunchyroll Subscription", sdf.parse("2023-04-12")));
            expenseList.add(new LineItem(18.21, "WSJ Subscription", sdf.parse("2033-04-12")));
            expenseList.add(new LineItem(10.42, "Cereal", sdf.parse("2021-03-12")));
            expenseList.add(new LineItem(2.40, "Donation to Homeless", sdf.parse("2022-01-12")));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        try {
            earningsList.add(new LineItem(20.4, "Mother", sdf.parse("2023-04-12")));
            earningsList.add(new LineItem(18.21, "Paycheck", sdf.parse("2033-04-12")));
            earningsList.add(new LineItem(10.42, "Uber Repayment", sdf.parse("2021-03-12")));
            earningsList.add(new LineItem(2.40, "Gift from Grandparent", sdf.parse("2022-01-12")));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


}