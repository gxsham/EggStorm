package com.example.gxsha.eggstorm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonPlay;
    TextView lastScore, highScore;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        lastScore = (TextView) findViewById(R.id.lastScore);
        highScore = (TextView) findViewById(R.id.highScore);
        sharedPreferences = getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);

        setSore();
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
            }
        });

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1073771898433460~9663588269");
        AdView mAdView = (AdView)findViewById(R.id.ads_place);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void setSore(){
        lastScore.setText("Last Score:" + sharedPreferences.getInt("lastScore",0));
        highScore.setText("High Score:" + sharedPreferences.getInt("highScore",0));
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSore();
    }
}
