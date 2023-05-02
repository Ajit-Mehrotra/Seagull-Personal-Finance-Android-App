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

public class EarningsAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<LineItem> earningList;
    //TEXTVIEW
    private TextView earningsAmount;
    private TextView earningTitle;
    private TextView earningDate;

    public EarningsAdapter(Context context, ArrayList<LineItem> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.earningList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //INFLATER
        convertView = LayoutInflater.from(context).inflate(R.layout.earning_row, parent, false);

        //TITLE
        earningTitle = convertView.findViewById(R.id.earningTitle);
        String title = earningList.get(position).getTitle();
        earningTitle.setText(title);

        //AMOUNT
        earningsAmount = convertView.findViewById(R.id.earningAmount);
        Double amt = earningList.get(position).getAmount();
        earningsAmount.setText(String.format("%.2f", amt));

        //DATE
        earningDate = convertView.findViewById(R.id.earningDate);
        Date date = earningList.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        earningDate.setText(formattedDate);

        return convertView;
    }
}
