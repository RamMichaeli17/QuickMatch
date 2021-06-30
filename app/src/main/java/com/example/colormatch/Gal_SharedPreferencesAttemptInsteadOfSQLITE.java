package com.example.colormatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gal_SharedPreferencesAttemptInsteadOfSQLITE extends AppCompatActivity {

    ListView scorelistview;
    EditText usernameET,scoreET;
    Button addButton;
    ArrayList<Person> peopleList = new ArrayList<>();
    ImageView numberonebadge;
    int addButtonCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gal_shared_preferences_attempt_instead_of_sqlite);


        scorelistview=findViewById(R.id.listView);
        usernameET=findViewById(R.id.usernameET);
        scoreET=findViewById(R.id.scoreET);
        addButton=findViewById(R.id.addBTNsharedPreferences);
        numberonebadge=findViewById(R.id.numberonebadge);


        showTrophy();

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addButtonCounter++;
                showTrophy();
                Person newPerson = new Person(usernameET.getText().toString(),scoreET.getText().toString());

                peopleList.add(newPerson);

                Collections.sort(peopleList);


                listAdapter_gal adapter = new listAdapter_gal(Gal_SharedPreferencesAttemptInsteadOfSQLITE.this, R.layout.adapter_view_layout_sharedpreferences_gal, peopleList);

                scorelistview.setAdapter(adapter);
            }
        });

    }

    public void showTrophy()
    {
        if (addButtonCounter == 0) {
            numberonebadge.setVisibility(View.INVISIBLE);
        } else {
            numberonebadge.setVisibility(View.VISIBLE);
        }

    }
}