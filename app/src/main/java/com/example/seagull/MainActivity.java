package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

// Main Activity class that hosts the ViewPager and TabLayout
public class MainActivity extends AppCompatActivity implements FormSubmitListener, TextToSpeech.OnInitListener {


    private static final int REQUEST_LOCATION_PERMISSION = 1;
    //STUFF RELATING TO TABS
    LocationManager locationManager;
    private Context mContext;
    public MainActivity(Context context) {
        mContext = context;
    }
    public MainActivity() {
        // ...
    }
    public LocationManager getLocationManager() {
        this.locationManager = locationManager;
        return locationManager;
    }
    private ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;

    /* ViewPager2 swipes between fragments
    FragmentAdapter is the adapter for ViewPager2 to manage all the fragments
     TabLayout shows the tabs for the respective fragments*/

    //FRAGMENTS
    private FormFragment formFragment;
    private TableFragment tableFragment;
    private MapFragment mapFragment;

//    private BankDetailsFragment bankDetailsFragment;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tts = new TextToSpeech(this, this);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        //add table fragment
        tableFragment = new TableFragment();

        //add form fragment & respective listener
        formFragment = new FormFragment();
        formFragment.setFormSubmitListener(this); // Set the listener

        //add maps fragment
        mapFragment = new MapFragment();

        //add bankDetails fragment
//        bankDetailsFragment = new BankDetailsFragment();


        //add fragments to adapters
        fragmentAdapter.addFragment(tableFragment, "Expenses/Earnings");
        fragmentAdapter.addFragment(formFragment, "Submission Form");
        fragmentAdapter.addFragment(mapFragment, "ATM Maps");
//        fragmentAdapter.addFragment(bankDetailsFragment, "Bank Details");


        viewPager.setAdapter(fragmentAdapter);

        //connects tablayout to viewpager using the tablayoutmediator...
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(fragmentAdapter.getFragmentTitle(position))
        );
        tabLayoutMediator.attach();
    }

    public void onFormSubmit(LineItem lineItem, boolean isExpense) {
        tableFragment.updateTable(lineItem, isExpense);
    }

    public void switchToTab(int tabIndex) {
        viewPager.setCurrentItem(tabIndex, true);
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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
            } else {
                tts.setSpeechRate(1.0f); // Set the speech rate
                tts.setPitch(1.0f); // Set the pitch
                speak("hello there! This is your personal finance planning application");
                Log.e("TTS", "Spoken");
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    private void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UtteranceId");
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


}


