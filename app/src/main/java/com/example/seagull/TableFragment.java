package com.example.seagull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TableFragment extends Fragment {

    private ArrayList<LineItem> expenseList = new ArrayList<>();
    private ArrayList<LineItem> earningsList = new ArrayList<>();
    private ListView expenseView;
    private ListView earningsView;
    private ExpensesAdapter expenseAdapter;
    private EarningsAdapter earningsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tables, container, false);

        expenseView = rootView.findViewById(R.id.expenseView);
        earningsView = rootView.findViewById(R.id.earningView);

        // Use adapter as bridge to connect ArrayList and ListView (UI & logic)
        expenseAdapter = new ExpensesAdapter(getActivity(), expenseList);
        expenseView.setAdapter(expenseAdapter);

        earningsAdapter = new EarningsAdapter(getActivity(), earningsList);
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

        return rootView;
    }
}