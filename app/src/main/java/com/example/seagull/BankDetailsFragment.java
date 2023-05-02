package com.example.seagull;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BankDetailsFragment extends Fragment {
    //UI
    private TextView bankName;
    private TextView phoneTxtView;
    private TextView addressTxtView;
    private Button webBtn;
    private Button dialBtn;

    //STRINGS
    private String website = null;
    private String name;
    private String phone= null;
    private String address = null;

    //LOG TAG
    private static final String TAG = "BANK_DET_FRAG";

    //(REQUIRED EMPTY PUBLIC) CONSTRUCTOR
    public BankDetailsFragment() {
        name =  "Click a Bank";
        website =  "https://bentley.edu";
        address =  "Click a Bank";
        phone =  "123-123-1232";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            try {name =getArguments().getString("name");}
                catch(Exception e){Log.e(TAG,"Name get exception caught");}

            try {website =getArguments().getString("website");}
                catch(Exception e){Log.e(TAG,"Website get exception caught");}

            try {phone =getArguments().getString("phone");}
                catch(Exception e){Log.e(TAG,"Phone get exception caught");}

            try {address =getArguments().getString("address");}
                catch(Exception e){Log.e(TAG,"Address get exception caught");}
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //INFLATER
        View rootView = inflater.inflate(R.layout.fragment_bank_details, container, false);


        dialBtn = rootView.findViewById(R.id.dialBtn);
        bankName = rootView.findViewById(R.id.bankName);

        //WEB BUTTON
        webBtn = rootView.findViewById(R.id.websiteBtn);
        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EXPLICIT INTENT -> WEBVIEW
                Intent intent = new Intent(getActivity(), BankDetailsFragment.class);
                //PASS URL
                intent.putExtra("website", "https://www.example.com");
                //START WEBVIEW ACTIVITY
                startActivity(intent);
            }
        });

        return rootView;
    }
}