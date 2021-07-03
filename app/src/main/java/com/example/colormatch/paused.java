package com.example.colormatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

public class paused extends Activity {

    Button continueBTN,exitBTN;
    ArrayList<highScore> highScoreList; // Used to save the score incase user wants to exit game before losing
    String userName;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paused);

        Bundle extras = getIntent().getExtras(); // Get username and score from SecondActivityGame
        if(extras !=null) {
            userName = extras.getString("username");
            score = extras.getInt("score");
        }



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height));

        continueBTN = findViewById(R.id.continueplay);
        exitBTN = findViewById(R.id.exit);

        // Shared Preferences - get data
        highScoreList = PrefConfigGal.readListFromPref(this);
        if ( highScoreList ==null || highScoreList.isEmpty()) {
            highScoreList = new ArrayList<>();
        }

        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // return to the game
            }
        });

        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHighScores();
                finishAffinity(); // finish all activitys below current activity and load mainActivity
                startActivity(new Intent(paused.this, MainActivity.class));
            }
        });

    }

    // This function is used to save highscore in case user chooses to exit game before losing
    private void updateHighScores()
    {
        highScore newHighScore = new highScore(userName,Integer.toString(score)); // Create a new highscore with username and current points
        highScoreList.add(newHighScore); // Add it to the list
        Collections.sort(highScoreList); // Sort the list in descending order , so the highest score will be first
        // Shared Preferences
        PrefConfigGal.writeListInPref(getApplicationContext(),highScoreList); // Write list to shared preferences for other activities to use
    }

}
