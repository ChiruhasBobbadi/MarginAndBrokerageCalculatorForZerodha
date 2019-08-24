package com.chiruhas.android.zerodha.View.Activities;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.View.Brokerage.BrokerageFragment;
import com.chiruhas.android.zerodha.View.Brokerage.commodity.commodityBrokerageActivity;
import com.chiruhas.android.zerodha.View.Brokerage.currency.CurrencyBrokerage;
import com.chiruhas.android.zerodha.View.Brokerage.equity.EquityBrokerage;
import com.chiruhas.android.zerodha.View.Fragments.MarginFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chiruhas.android.zerodha.R;
import com.vorlonsoft.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements MarginFragment.OnFragmentInteractionListener, BrokerageFragment.OnFragmentInteractionListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ViewPager mViewPager;
    private InterstitialAd bracket, equity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // Rate It

        AppRate.with(this)
                .setInstallDays((byte) 5)                  // default is 10, 0 means install day, 10 means app is launched 10 or more days later than installation
                .setLaunchTimes((byte) 3)                  // default is 10, 3 means app is launched 3 or more times
                .setRemindInterval((byte) 1)               // default is 1, 1 means app is launched 1 or more days after neutral button clicked
                .setRemindLaunchesNumber((byte) 1)         // default is 0, 1 means app is launched 1 or more times after neutral button clicked
                .monitor();                                // Monitors the app launch times
        AppRate.showRateDialogIfMeetsConditions(this);

        bracket = new InterstitialAd(this);
        equity = new InterstitialAd(this);

        //test
        bracket.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        equity.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // original

//        bracket.setAdUnitId("ca-app-pub-4351116683020455/7631704868");
//        equity.setAdUnitId("ca-app-pub-4351116683020455/7352473382");
        bracket.loadAd(new AdRequest.Builder().build());
        // navigating user to bracket activity
        bracket.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(MainActivity.this, BracketActivity.class));
            }
        });
        equity.loadAd(new AdRequest.Builder().build());
        equity.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(MainActivity.this, EquityActivity.class));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Margin and Brokerage Calculator");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        // nav view ref
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // for animating the menu icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.home);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
//                    case R.id.book:
//                        // open bookmark Activity
//                        //startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
//                        break;

                    case R.id.pp:
                        String url = "http://chiruhas.in/privacy_policy.html";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;

                    case R.id.about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.feedback:
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;
                    case R.id.feature:

                        Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                        intent2.setType("text/plain");
                        intent2.setData(Uri.parse("mailto:chiruhas.bobbadi123@gmail.com"));
                        intent2.putExtra(Intent.EXTRA_SUBJECT, "Margin & Brokerage Calculator App Feature Request");
                        //intent.putExtra(Intent.EXTRA_TEXT, "Place your email message here ...");
                        startActivity(Intent.createChooser(intent2, "Send Email"));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        //add initialization

        // original
       //MobileAds.initialize(this,"ca-app-pub-4351116683020455~8691946225");
//
        // test
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    // brokerage navigation
    @Override
    public void onBrokerageFragment(View v) {
        switch (v.getId()) {
            case R.id.equity:
                startActivity(new Intent(MainActivity.this, EquityBrokerage.class));
                break;

            case R.id.commodity:
                //startActivity(new Intent(MainActivity.this, commodityBrokerageActivity.class));
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.currency:
                //Toast.makeText(this, "Coming Soon..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, CurrencyBrokerage.class));
                break;
        }
    }

    //margin navigation
    @Override
    public void onMarginFragment(View v) {
        switch (v.getId()) {
            case R.id.equity:
                if (equity.isLoaded()) {
                    equity.show();
                } else {
                    startActivity(new Intent(MainActivity.this, EquityActivity.class));
                }

                break;
            case R.id.commodity:
                startActivity(new Intent(MainActivity.this, CommodityActivity.class));
                break;
            case R.id.futures:
               // Toast.makeText(this, "Coming Soon..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, FuturesActivity.class));

                break;
            case R.id.currency:
                startActivity(new Intent(MainActivity.this, CurrencyActivity.class));
                break;
            case R.id.bracket:

                if (bracket.isLoaded()) {
                    bracket.show();
                } else {
                    startActivity(new Intent(MainActivity.this, BracketActivity.class));
                }

                break;
        }
    }


//


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = new MarginFragment();
            switch (position) {
                case 0:
                    fragment = new MarginFragment();
                    break;
                case 1:
                    fragment = new BrokerageFragment();
                    break;

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
