package com.example.seagull;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BankDetailsFragment extends Fragment {

    private TextView bankName;
    private TextView phoneTxtView;
    private TextView addressTxtView;

    private Button webBtn;
    private Button dialBtn;

    private String website = null;
    private String name;
    private String phone= null;
    private String address = null;

    public BankDetailsFragment() {
        // Required empty public constructor
        name =  "Click a Bank";
        website =  "https://bentley.edu";
        address =  "Click a Bank";
        phone =  "123-123-1232";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            try {
                name =getArguments().getString("name");
            } catch(Exception e){

            }
            try {
                website =getArguments().getString("website");
            } catch(Exception e){

            }
            try {
                phone =getArguments().getString("phone");
            } catch(Exception e){

            }
            try {
                address =getArguments().getString("address");
            } catch(Exception e){

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_bank_details, container, false);

        // Inflate the layout for this fragment
        webBtn = rootView.findViewById(R.id.websiteBtn);
        dialBtn = rootView.findViewById(R.id.dialBtn);
        bankName = rootView.findViewById(R.id.bankName);

        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an explicit intent to start the WebView activity
                Intent intent = new Intent(getActivity(), BankDetailsFragment.class);

                // pass the URL of the web page as extra data
                intent.putExtra("website", "https://www.example.com");

                // start the WebView activity
                startActivity(intent);
            }
        });
        return rootView;
    }
}