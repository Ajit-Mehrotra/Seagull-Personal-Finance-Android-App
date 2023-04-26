package com.example.seagull;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

// Main Activity class that hosts the ViewPager and TabLayout
public class MainActivity extends AppCompatActivity implements FormSubmitListener{

    //STUFF RELATING TO TABS

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //add fragments to adapters
        fragmentAdapter.addFragment(tableFragment, "Expenses/Earnings");
        fragmentAdapter.addFragment(formFragment, "Submission Form");
        fragmentAdapter.addFragment(mapFragment, "ATM Maps");


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


}


