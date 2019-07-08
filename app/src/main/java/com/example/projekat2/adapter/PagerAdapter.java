package com.example.projekat2.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projekat2.R;
import com.example.projekat2.fragment.ChatFragment;
import com.example.projekat2.fragment.RasporedFragment;
import com.example.projekat2.fragment.WallFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    public static final int FRAGMENT_TEXT = 0;
    public static final int FRAGMENT_CHOICE = 1;

    private List<String> mTitles;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        initTitles(context);

    }

    private void initTitles(Context context) {
        mTitles = new ArrayList<>();
        mTitles.add(context.getString(R.string.fragment_title_1));
        mTitles.add(context.getString(R.string.fragment_title_2));
        mTitles.add(context.getString(R.string.fragment_title_3));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RasporedFragment.newInstance();
            case 1:
                return ChatFragment.newInstance();
            case 2:
                return WallFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
