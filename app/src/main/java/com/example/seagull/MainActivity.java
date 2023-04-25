package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

    private ArrayList<LineItem> expenseList = new ArrayList<>();
   private  ArrayList<LineItem> earningsList = new ArrayList<>();

    private ListView expenseView;
   private  ListView earningsView;
    private ExpensesAdapter expenseAdapter;
    private EarningsAdapter earningsAdapter;

    SupportMapFragment mapFragment = SupportMapFragment.newInstance();
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    private static MainActivity instance;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    public static MainActivity getInstance() {
        return instance;
    }
    public void changeTab(int tabIndex) {
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setCurrentTab(tabIndex);
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        requestLocationPermission();


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





        //CREATE TAB HOST
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        //INITIALIZE TAB 1 - MONTHLY
        spec = tabs.newTabSpec("tag1");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab1);           //add tab view content
        spec.setIndicator("Monthly");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 1 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag1")) {
                    // START NEW INTENT FOR MONTHLY TAB
                    Intent intent = new Intent(getApplicationContext(), Monthly.class);
                    startActivity(intent);
                }
            }
        });

        //INITIALIZE TAB 2 - GOALS
        spec = tabs.newTabSpec("tag2");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab2);           //add tab view content
        spec.setIndicator("Goals");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 2 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag2")) {
                    // START NEW INTENT FOR GOALS TAB
                    Intent intent = new Intent(getApplicationContext(), Goals.class);
                    startActivity(intent);
                }
            }
        });

        //INITIALIZE TAB 3 - REVIEW
        spec = tabs.newTabSpec("tag3");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab3);           //add tab view content
        spec.setIndicator("Review");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 3 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag3")) {
                    // START NEW INTENT FOR REVIEW TAB
                    Intent intent = new Intent(getApplicationContext(), Review.class);
                    startActivity(intent);
                }
            }
        });

        //INITALIZE TAB 4 - MAP
        spec = tabs.newTabSpec("tag4");   //CREATE NEW TAB SPECIFICATION
        spec.setContent(R.id.tab4);           //add tab view content
        spec.setIndicator("Map");         //TAB DISPLAY
        tabs.addTab(spec);                    //put tab in TabHost container

        //TAB 4 CHANGE LISTENER
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {


                if (tabId.equals("tag4")) {
                    // START NEW INTENT FOR MAP TAB
                    Intent intent = new Intent(getApplicationContext(), Map.class);
                    intent.putExtra("tabId", "tag1");
                    startActivity(intent);



                    ;
                }
            }
        });



    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }
    public SupportMapFragment getMapFragment() {
        return mapFragment;
    }
}