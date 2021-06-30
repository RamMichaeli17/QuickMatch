package com.example.colormatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Gal_SharedPreferencesAttemptInsteadOfSQLITE extends AppCompatActivity {

    ListView scorelistview;
    EditText usernameET,scoreET;
    Button addButton,clearBTN;
    ArrayList<Person> peopleList;
  //  ImageView numberonebadge;
    int addButtonCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gal_shared_preferences_attempt_instead_of_sqlite);


        scorelistview=findViewById(R.id.listView);
        usernameET=findViewById(R.id.usernameET);
        scoreET=findViewById(R.id.scoreET);
        addButton=findViewById(R.id.addBTNsharedPreferences);
        clearBTN=findViewById(R.id.clearBTN);
       // numberonebadge=findViewById(R.id.numberonebadge);

        // Shared Preferences - get data
        peopleList = PrefConfigGal.readListFromPref(this);
        if ( peopleList==null)
            peopleList = new ArrayList<>();
        else {
            addButtonCounter = 1;
        }

        // Initial screen load
        refreshScreen();

        //Show #1 medal icon if there is atleast 1 score
       // showTrophy();

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addButtonCounter++;
             //   showTrophy();
                Person newPerson = new Person(usernameET.getText().toString(),scoreET.getText().toString());

                peopleList.add(newPerson);


                Collections.sort(peopleList);

                // Shared Preferences
                PrefConfigGal.writeListInPref(getApplicationContext(),peopleList);
                //


                refreshScreen();
            }
        });

        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleList.clear();
                addButtonCounter=0;
               // showTrophy();
                refreshScreen();
            }
        });

    }

   /* public void showTrophy()
    {
        if (addButtonCounter == 0) {
            numberonebadge.setVisibility(View.INVISIBLE);
        } else {
            numberonebadge.setVisibility(View.VISIBLE);
        }

    }*/

    public void refreshScreen()
    {
        listAdapter_gal adapter = new listAdapter_gal(Gal_SharedPreferencesAttemptInsteadOfSQLITE.this, R.layout.adapter_view_layout_sharedpreferences_gal, peopleList);
        scorelistview.setAdapter(adapter);
    }
}