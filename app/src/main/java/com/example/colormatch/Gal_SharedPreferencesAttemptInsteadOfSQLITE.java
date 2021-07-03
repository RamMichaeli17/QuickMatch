package com.example.colormatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Gal_SharedPreferencesAttemptInsteadOfSQLITE extends AppCompatActivity {

    ListView scorelistview;
    EditText usernameET,scoreET;
    Button addButton,clearBTN;
    ArrayList<highScore> peopleList;

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

        clearBTN=findViewById(R.id.clearBTN);


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