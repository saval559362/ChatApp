package com.example.chatapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragTitles = new ArrayList<>();

    public ViewPageAdapter(FragmentManager fa, Lifecycle lc) {
        super(fa, lc);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        fragTitles.add(title);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
