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
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.Locale;

// Main Activity class that hosts the ViewPager and TabLayout
public class MainActivity extends AppCompatActivity implements FormSubmitListener, TextToSpeech.OnInitListener {

    //TABS VARIABLES
    private ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;

    /* ViewPager2 swipes between fragments
    FragmentAdapter is the adapter for ViewPager2 to manage all the fragments
     TabLayout shows the tabs for the respective fragments*/

    //FRAGMENTS
    private FormFragment formFragment;
    private TableFragment tableFragment;
//    private MapFragment mapFragment;
    //TTS
    private TextToSpeech tts;
    //NOTIFICATION
    private NotificationManager mManager;
    public static final String NOTIFICATION_CHANNEL_ID = "com.seagull.notification";
    public static final String NOTIFICATION_CHANNEL_NAME = "CHANNEL";
    public static final int NOTIFICATION_ID = 315;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tts = new TextToSpeech(this, this);


        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        //add table fragment
        tableFragment = new TableFragment();

        //add form fragment & respective listener
        formFragment = new FormFragment();
        formFragment.setFormSubmitListener(this); // Set the listener
        RepFragment repFragment = new RepFragment();


        //add fragments to adapters
        fragmentAdapter.addFragment(tableFragment, "Expenses/Earnings");
        fragmentAdapter.addFragment(formFragment, "Submission Form");
        fragmentAdapter.addFragment(repFragment, "Seagull Representatives");


        viewPager.setAdapter(fragmentAdapter);

        //connects tablayout to viewpager using the tablayoutmediator...
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(fragmentAdapter.getFragmentTitle(position))
        );
        tabLayoutMediator.attach();

        //notification
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

        androidChannel.enableLights(true);

        androidChannel.enableVibration(true);
        androidChannel.setVibrationPattern(new long[] { 1000, 500, 1000, 500 });

        androidChannel.setLightColor(Color.BLUE);

        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mManager.createNotificationChannel(androidChannel);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_IMMUTABLE);

        //SEND NOTIFICATION AT 9 AM EVERY DAY
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void onFormSubmit(LineItem lineItem, boolean isExpense) {
        tableFragment.updateTable(lineItem, isExpense);
    }

    public void switchToTab(int tabIndex) {
        viewPager.setCurrentItem(tabIndex, true);
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
        showNotification(getApplicationContext());
        super.onDestroy();
    }

    // Broadcast Receiver for handling notifications
    public static class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNotification(context);
        }
    }

    // Helper method to show notification
    private static void showNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Seagull")
                .setContentText("Have you inputted your expenses for today?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}



