package com.example.seagull;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {
    //LISTS
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    //FRAGMENT ADAPTER CONSTRCUTOR
    public FragmentAdapter( FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    //ADD NEW FRAGMENT
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }
    //GET TITLE
    public String getFragmentTitle(int position) {
        return fragmentTitleList.get(position);
    }

    //CREATE FRAGMENT FROM LIST
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }
    //GET NUMBER OF FRAGMENTS
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    //RETURN SPECIFIC FRAGMENT
    public Fragment getFragmentAtPosition(int position) {
        if (position >= 0 && position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return null;
    }

}
