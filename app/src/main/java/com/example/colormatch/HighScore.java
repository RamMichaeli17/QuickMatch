package com.example.colormatch;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    ListView scorelistview;
    Button clearBTN;
    ArrayList<HighScoreObject> highScoreObjectArrayList;
    ImageView backgroundStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gal_shared_preferences_attempt_instead_of_sqlite);


        scorelistview=findViewById(R.id.listView);
        clearBTN=findViewById(R.id.clearBTN);
        backgroundStars=findViewById(R.id.backgroundStars);

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(backgroundStars,"translationY",-450);
        animation1.setDuration(1200);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(backgroundStars,"alpha",1);
        animation2.setDuration(2500);

        AnimatorSet set = new AnimatorSet();
        set.play(animation1).with(animation2);
        set.start();


        // Shared Preferences - get data
        highScoreObjectArrayList = PrefConfigGal.readListFromPref(this);
        if ( highScoreObjectArrayList ==null)
            highScoreObjectArrayList = new ArrayList<>();

        // Initial screen load
        refreshScreen();




        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HighScore.this);
                builder.setTitle("Are you sure?");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        highScoreObjectArrayList.clear();
                        PrefConfigGal.writeListInPref(getApplicationContext(), highScoreObjectArrayList); // This line ensures that both lists (from game and from highscores) reset
                        refreshScreen();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });

    }


    public void refreshScreen()
    {
        listAdapter_gal adapter = new listAdapter_gal(HighScore.this, R.layout.adapter_view_layout_sharedpreferences_gal, highScoreObjectArrayList);
        scorelistview.setAdapter(adapter);
    }
}