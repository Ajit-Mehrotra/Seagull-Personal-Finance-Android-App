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

public class MyAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<LineItem> arrayList;
    private TextView itemAmount;
    private TextView itemTitle;

    private TextView itemDate;

    public MyAdapter(Context context, ArrayList<LineItem> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //call inflater to create a View from an xml layout file
        convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        //get references for row widgets
        itemTitle = convertView.findViewById(R.id.itemTitle);
        itemAmount = convertView.findViewById(R.id.itemAmount);
        itemDate = convertView.findViewById(R.id.itemDate);

        //get the amount from the arraylist and set the UI to it
        Double amt = arrayList.get(position).getAmount();
        itemAmount.setText(String.format("%.2f", amt));

        //get the title from the arraylist and set the UI to it
        String title = arrayList.get(position).getTitle();
        itemTitle.setText(title);

        // get the date from the arraylist and set the UI to it
        Date date = arrayList.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = sdf.format(date);
        itemDate.setText(formattedDate);

        return convertView;
    }
}
