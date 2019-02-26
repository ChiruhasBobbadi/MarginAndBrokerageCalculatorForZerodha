package com.chiruhas.android.zerodhamargincalculator.View.Activities;

import com.chiruhas.android.zerodhamargincalculator.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodhamargincalculator.HelperClasses.ObjectConverter;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodhamargincalculator.View.bookmark.Fragments.CommodityFrag;
import com.chiruhas.android.zerodhamargincalculator.View.bookmark.Fragments.CurrencyFrag;
import com.chiruhas.android.zerodhamargincalculator.View.bookmark.Fragments.EquityFrag;
import com.chiruhas.android.zerodhamargincalculator.View.bookmark.Fragments.FuturesFrag;
import com.chiruhas.android.zerodhamargincalculator.room.Commodity.CommodityViewModel;
import com.chiruhas.android.zerodhamargincalculator.room.equity.EquityViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;


import com.chiruhas.android.zerodhamargincalculator.R;
import com.google.gson.internal.bind.ObjectTypeAdapter;

public class BookmarkActivity extends AppCompatActivity implements EquityFrag.EquityFragmentListener,CommodityFrag.OnFragmentInteractionListener
,FuturesFrag.OnFragmentInteractionListener,CurrencyFrag.OnFragmentInteractionListener{


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    EquityViewModel equityViewModel;
    CommodityViewModel commodityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmarks");

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

        //room code for equit margins
        equityViewModel = ViewModelProviders.of(this).get(EquityViewModel.class);

        // commodity margin

        commodityViewModel = ViewModelProviders.of(this).get(CommodityViewModel.class);


    }



    @Override
    public void showPopup(GodModel GodModel) {
        new AlertHelper(BookmarkActivity.this).loadEquityPopup(GodModel);
    }

    @Override
    public void deleteBookmarkCommodity(GodModel GodModel) {

        commodityViewModel.delete(ObjectConverter.GodModeltoGodCommodity(GodModel));
    }

    // deleting equity
    @Override
    public void deleteBookmark(GodModel GodModel) {
        // converting object of GodModel into object of GodEquity
        GodEquity godEquity = new GodEquity(GodModel.getMargin(),GodModel.getCo_lower(),GodModel.getMis_multiplier(),GodModel.getTradingsymbol(),GodModel.getCo_upper(),GodModel.getNrml_margin(),GodModel.getMis_margin());
        equityViewModel.delete(godEquity);
    }

    // currency and futures callback method
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new EquityFrag();
            switch(position){
                case 0:
                    fragment = new EquityFrag();
                    break;
                case 1:
                    fragment = new CommodityFrag();
                    break;
                case 2:
                    fragment=new FuturesFrag();
                    break;
                case 3:
                    fragment = new CurrencyFrag();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
