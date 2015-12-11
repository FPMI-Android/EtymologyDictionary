package com.fpmil.developer.dictionary.ety;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fpmil.developer.dictionary.ety.entities.Word;
import com.fpmil.developer.dictionary.ety.fragments.BestAppFragment;
import com.fpmil.developer.dictionary.ety.fragments.FavouriteFragment;
import com.fpmil.developer.dictionary.ety.fragments.VocabularyFragment;
import com.fpmil.developer.dictionary.ety.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    InterstitialAd mInterstitialAd;
    private Word wordSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        createAds();
    }

    private void createAds(){
        //Load banner
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1F6967AEA1AB5E2A6A19F4A99ED34299")
                .build();
        adView.setVisibility(View.VISIBLE);
        adView.setEnabled(true);

        adView.loadAd(adRequest);

        //Load ads fullscreen
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4779530043032617/4165829670");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                openMeaning();
            }
        });

        requestNewInterstitial();
    }
    public void openMeaning(Word word){
        this.wordSelected = word;

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else
            openMeaning();
    }
    private void openMeaning(){
        Intent intent = new Intent(this, MeaningActivity.class);
        Bundle b = new Bundle();
        b.putLong("id", wordSelected.getId());
        intent.putExtras(b);
        startActivity(intent);
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1F6967AEA1AB5E2A6A19F4A99ED34299")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VocabularyFragment(), "VOCABULARY");
        adapter.addFragment(new FavouriteFragment(), "FAVOURITE");
        adapter.addFragment(new BestAppFragment(), "BESTAPP");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
