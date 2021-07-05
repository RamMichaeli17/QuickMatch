package com.example.colormatch;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Gal_SharedPreferencesAttemptInsteadOfSQLITE extends AppCompatActivity {

    ListView scorelistview;
    EditText usernameET,scoreET;
    Button addButton,clearBTN;
    ArrayList<highScore> peopleList;
    ImageView backgroundStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gal_shared_preferences_attempt_instead_of_sqlite);


        scorelistview=findViewById(R.id.listView);
        /*
        usernameET=findViewById(R.id.usernameET);
        scoreET=findViewById(R.id.scoreET);
        addButton=findViewById(R.id.addBTNsharedPreferences);
        */
        /*
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(ShapeFillerColor,"translationX",1000);
        animation1.setDuration(1000);
        ObjectAnimator animation1b = ObjectAnimator.ofFloat(ShapeOutline,"translationX",1000);
        animation1b.setDuration(1000);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(ShapeFillerColor,"translationX",-1000);
        animation2.setDuration(2000);
        ObjectAnimator animation2b = ObjectAnimator.ofFloat(ShapeOutline,"translationX",-1000);
        animation2b.setDuration(2000);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(ShapeFillerColor,"translationX",0);
        animation3.setDuration(1300);
        ObjectAnimator animation3b = ObjectAnimator.ofFloat(ShapeOutline,"translationX",0);
        animation3b.setDuration(1300);
        AnimatorSet set = new AnimatorSet();
        AnimatorSet set2 = new AnimatorSet();
        AnimatorSet set3 = new AnimatorSet();
        set.play(animation1).with(animation1b);
        set.start();
        set2.play(animation2).with(animation2b);
        set2.setStartDelay(1000);
        set2.start();
        set3.play(animation3).with(animation3b);
        set3.setStartDelay(3000);
        set3.start();

*/

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
        peopleList = PrefConfigGal.readListFromPref(this);
        if ( peopleList==null)
            peopleList = new ArrayList<>();

        // Initial screen load
        refreshScreen();



/*        //This is the manual "add" button , not longer used because scores come from the game now

          addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Person newPerson = new Person(usernameET.getText().toString(),scoreET.getText().toString());

                peopleList.add(newPerson);


                Collections.sort(peopleList);

                // Shared Preferences
                PrefConfigGal.writeListInPref(getApplicationContext(),peopleList);
                //


                refreshScreen();
            }
        });*/

        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Gal_SharedPreferencesAttemptInsteadOfSQLITE.this);
                builder.setTitle("Are you sure?");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        peopleList.clear();
                        PrefConfigGal.writeListInPref(getApplicationContext(),peopleList); // This line ensures that both lists (from game and from highscores) reset
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
        listAdapter_gal adapter = new listAdapter_gal(Gal_SharedPreferencesAttemptInsteadOfSQLITE.this, R.layout.adapter_view_layout_sharedpreferences_gal, peopleList);
        scorelistview.setAdapter(adapter);
    }
}