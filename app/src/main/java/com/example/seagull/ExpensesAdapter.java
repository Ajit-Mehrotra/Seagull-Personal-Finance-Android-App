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
    //TEXTVIEW
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

        //INFLATER
        convertView = LayoutInflater.from(context).inflate(R.layout.expense_row, parent, false);

        //TITLE
        expenseTitle = convertView.findViewById(R.id.expenseTitle);
        String title = expenseList.get(position).getTitle();
        expenseTitle.setText(title);

        //AMOUNT
        expenseAmount = convertView.findViewById(R.id.expenseAmount);
        Double amt = expenseList.get(position).getAmount();
        expenseAmount.setText(String.format("%.2f", amt));

        //DATE
        expenseDate = convertView.findViewById(R.id.expenseDate);
        Date date = expenseList.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        expenseDate.setText(formattedDate);

        return convertView;
    }
}
