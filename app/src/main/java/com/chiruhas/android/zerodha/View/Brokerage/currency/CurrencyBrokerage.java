package com.chiruhas.android.zerodha.View.Brokerage.currency;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chiruhas.android.zerodha.R;
import com.google.android.material.tabs.TabLayout;

public class CurrencyBrokerage extends AppCompatActivity implements CurrencyBrokerageFrag.OnFragmentInteractionListener, CurrencyBrokerageFrag1.OnFragmentInteractionListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String def_state;
    private int stateIndex = 0;
    private SharedPreferences data;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_brokerage);

        init();


    }

    private void init() {
        View view = getWindow().getDecorView().getRootView();
        //AdViewHelper.loadBanner(view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Currency");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        data = getSharedPreferences("dataStore",
                MODE_PRIVATE);
        def_state = data.getString("default_state", "");
        if (!def_state.equals(""))
            stateIndex = Integer.parseInt(def_state);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = new CurrencyBrokerageFrag(stateIndex);
            ((CurrencyBrokerageFrag) fragment).updatePos(position);
            switch (position) {
                case 0:
                    fragment = new CurrencyBrokerageFrag(stateIndex);
                    ((CurrencyBrokerageFrag) fragment).updatePos(position);
                    break;
                case 1:
                    fragment = new CurrencyBrokerageFrag1();

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
