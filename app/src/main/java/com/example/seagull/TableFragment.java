package com.example.seagull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
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

    public TableFragment() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            expenseList.add(new LineItem(20.4, "Crunchyroll Subscription", sdf.parse("2023-04-12")));
            expenseList.add(new LineItem(18.21, "WSJ Subscription", sdf.parse("2033-04-12")));
            expenseList.add(new LineItem(10.42, "Cereal", sdf.parse("2021-03-12")));
            expenseList.add(new LineItem(2.40, "Donation to Homeless", sdf.parse("2022-01-12")));

            earningsList.add(new LineItem(20.4, "Mother", sdf.parse("2023-04-12")));
            earningsList.add(new LineItem(18.21, "Paycheck", sdf.parse("2033-04-12")));
            earningsList.add(new LineItem(10.42, "Uber Repayment", sdf.parse("2021-03-12")));
            earningsList.add(new LineItem(2.40, "Gift from Grandparent", sdf.parse("2022-01-12")));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
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

        return rootView;
    }

    public void updateTable(LineItem lineItem, boolean isExpense) {

        Log.e("bruh", "UpdateTable - TableFragment");
        if (isExpense) {
            expenseList.add(lineItem);
            Log.e("bruh", expenseList.get(expenseList.size()-1).getTitle());
            expenseAdapter.notifyDataSetChanged();
        } else {
            earningsList.add(lineItem);
            earningsAdapter.notifyDataSetChanged();
        }

        // Update the height of the ListViews
        setListViewHeightBasedOnChildren(expenseView);
        setListViewHeightBasedOnChildren(earningsView);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }
}
