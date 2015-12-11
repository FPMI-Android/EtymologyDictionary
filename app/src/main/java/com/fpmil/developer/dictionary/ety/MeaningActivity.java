package com.fpmil.developer.dictionary.ety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpmil.developer.dictionary.ety.entities.Word;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MeaningActivity extends AppCompatActivity {

    private Word word;
    private Toolbar toolbar;
    private ImageView btnFavourite;
    private long id;

    private Animation anim_out;
    private Animation anim_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);
        anim_out = AnimationUtils.loadAnimation(MeaningActivity.this, R.anim.fade_out);
        anim_in = AnimationUtils.loadAnimation(MeaningActivity.this, R.anim.fade_in);

        init();
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

        id = b.getLong("id");
        word = Word.load(Word.class,id);

        TextView origin_text = (TextView)findViewById(R.id.origin_text);
        origin_text.setText("@ " + word.origin);

        TextView definition_text = (TextView)findViewById(R.id.definition_text);
        definition_text.setText(word.definition);

        btnFavourite = (ImageView)findViewById(R.id.btn_fv);
        fvStatus(word.favourite);
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fv();
            }
        });

        createAds();
    }
    private void fv(){
        word.favourite = word.favourite==0?1:0;
        fvStatus(word.favourite);
        word.save();
    }
    private void fvStatus(final int favourite){
        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(favourite == 1)
                    btnFavourite.setBackgroundResource(R.drawable.fv_pressed);
                else
                    btnFavourite.setBackgroundResource(R.drawable.fv);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        btnFavourite.startAnimation(anim_in);
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

    }
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}