package com.example.translatorapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.Stack;

public class MasterActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static Stack<String>[] appTitles;
    public int currentPage;

    final int[] ICONS = new int[]{
            R.drawable.saved,
            R.drawable.translate,
            R.drawable.settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        appTitles = new Stack[3]; //Initialise the title stacks
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.headerlogo);
        for (int i = 0; i < 3; i++)
            appTitles[i] = new Stack();
        //Add the titles to the stacks
        appTitles[0].push("Saved");
        appTitles[1].push("Translate");
        appTitles[2].push("Settings");
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                ActionBar actionBar = getsupportactionbar();
                currentPage = i;
                if (actionBar != null)
                    actionBar.setTitle(appTitles[i].peek());
            }
            @Override
            public void onPageSelected(int i) {
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        mViewPager.setCurrentItem(1);
    }
    public ActionBar getsupportactionbar() {
        ActionBar mActionBar = getSupportActionBar();
        return mActionBar;
    }
    public void setTitle(String title){
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(title);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return new HistoryFragment();
                case 1:
                    return new TranslateFragment();
                case 2:
                    return new SettingsFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
