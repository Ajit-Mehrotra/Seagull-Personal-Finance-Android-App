package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.Calendar;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

// Main Activity class that hosts the ViewPager and TabLayout
public class MainActivity extends AppCompatActivity implements FormSubmitListener, TextToSpeech.OnInitListener {

    /*  ViewPager2 swipes between fragments
        FragmentAdapter is the adapter for ViewPager2 to manage all the fragments
        TabLayout shows the tabs for the respective fragments
    */

    //TABS
    private ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;

    //FRAGMENTS
    private FormFragment formFragment;
    private TableFragment tableFragment;
    private MapFragment mapFragment;

    //LOCATION
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    LocationManager locationManager;
    public LocationManager getLocationManager() {
        this.locationManager = locationManager;
        return locationManager;
    }
    //MAIN ACTIVITY
    private Context mContext;
    public MainActivity(Context context) {
        mContext = context;
    }
    public MainActivity() {}

    //NOTIFICATION
    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "CHANNEL_ANDROID";
    public static final int SIMPLE_NOTIFICATION_ID = 101;
    static Notification.Builder nb;
    //TTS
    private TextToSpeech tts;

    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        //TTS
        tts = new TextToSpeech(this, this);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //FRAGMENTS
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        tableFragment = new TableFragment();

        formFragment = new FormFragment();
        formFragment.setFormSubmitListener(this); // Set the listener
        mapFragment = new MapFragment();

        //FRAGMENTS --> ADAPTERS
        fragmentAdapter.addFragment(tableFragment, "Expenses/Earnings");
        fragmentAdapter.addFragment(formFragment, "Submission Form");
        fragmentAdapter.addFragment(mapFragment, "ATM Maps");

        viewPager.setAdapter(fragmentAdapter);

        //CONNECT TAB LAYOUT TO VIEWPAGER
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(fragmentAdapter.getFragmentTitle(position))
        );
        tabLayoutMediator.attach();

        //NOTIFICATION - IMPLEMENTATION

        //ANDROID CHANNEL
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                                                                    ANDROID_CHANNEL_NAME,
                                                                    NotificationManager.IMPORTANCE_DEFAULT);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setVibrationPattern(new long[] { 1500, 1000, 1500, 1000 });
        androidChannel.setLightColor(Color.BLUE);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        //ALARM TIME OF DAY
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);

        //NOTIFICATION MANAGER
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mManager.createNotificationChannel(androidChannel);

        //NOTIFICATION INTENT
        Intent intent = new Intent(this,NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        //NOTIFICATION BUILDER
        nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle("Seagull")
                .setContentText("Have you check your finances today?")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        //ALARM MANAGER
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }//ON CREATE END

    //FORM SUBMIT
    public void onFormSubmit(LineItem lineItem, boolean isExpense) { tableFragment.updateTable(lineItem, isExpense); }
    //TAB SWITCH
    public void switchToTab(int tabIndex) {
        viewPager.setCurrentItem(tabIndex, true);
    }
    //LOCATION PERMISSIONS
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //FORWARD TO  EASY PERMISSIONS
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //LOCATION PERMISSION REQUEST
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
    //TTS - ON INIT
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
                Log.i("TTS", "Spoken");
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }
    //TTS - SPEAK
    private void speak(String text) {tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UtteranceId");}
    //TTS - ON DESTROY
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    // Broadcast Receiver for handling notifications
    public static class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Use the notification builder to show the notification
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(SIMPLE_NOTIFICATION_ID, nb.build());
        }
    }

}
