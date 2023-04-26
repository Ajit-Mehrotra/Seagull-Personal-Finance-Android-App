package com.example.seagull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpensesAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<LineItem> expenseList;
    private TextView expenseAmount;
    private TextView expenseTitle;
    private TextView expenseDate;

    public ExpensesAdapter(Context context, ArrayList<LineItem> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.expenseList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //call inflater to create a View from an xml layout file
        convertView = LayoutInflater.from(context).inflate(R.layout.expense_row, parent, false);

        //get references for row widgets
        expenseTitle = convertView.findViewById(R.id.expenseTitle);
        expenseAmount = convertView.findViewById(R.id.expenseAmount);
        expenseDate = convertView.findViewById(R.id.expenseDate);

        //get the amount from the arraylist and set the UI to it
        Double amt = expenseList.get(position).getAmount();
        expenseAmount.setText(String.format("%.2f", amt));

        //get the title from the arraylist and set the UI to it
        String title = expenseList.get(position).getTitle();
        expenseTitle.setText(title);

        // get the date from the arraylist and set the UI to it
        Date date = expenseList.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = sdf.format(date);
        expenseDate.setText(formattedDate);

        return convertView;
    }
}