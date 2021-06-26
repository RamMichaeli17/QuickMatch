package com.example.colormatch;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DBActivity extends AppCompatActivity {

    Button add,viewall;
    EditText name,score;
    Switch activeuser;
    ListView highscoreList;
    CheckBox cbdelete;

    ArrayAdapter userArrayAdapter;
    DataBaseHelper dataBaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_activity);

        add = findViewById(R.id.add_btn);
        viewall=findViewById(R.id.btn_viewall);
        name = findViewById(R.id.editTextTextPersonName2);
        score = findViewById(R.id.score);
        activeuser=findViewById(R.id.active);
        highscoreList=findViewById(R.id.highscoreList);
        cbdelete = findViewById(R.id.checkBoxDelete);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(DBActivity.this);


        ShowUserList(dataBaseHelper);


        cbdelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    isChecked=false;
                else
                    isChecked=true;
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerModel cusmodel;

                try {
                    cusmodel = new customerModel(-1,name.getText().toString(),Integer.parseInt(score.getText().toString()),activeuser.isChecked());
                    Toast.makeText(DBActivity.this, "Added "+name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(DBActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    cusmodel = new customerModel(-1,"error",0,false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(DBActivity.this);

                dataBaseHelper.addOne(cusmodel);

                ShowUserList(dataBaseHelper);



            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (highscoreList.getVisibility() == View.INVISIBLE)
                    highscoreList.setVisibility(View.VISIBLE);
                else
                    highscoreList.setVisibility(View.INVISIBLE);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(DBActivity.this);
                ShowUserList(dataBaseHelper);

            }
        });

        highscoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (cbdelete.isChecked()) {
                    customerModel clickedUser = (customerModel) parent.getItemAtPosition(position);
                    dataBaseHelper.deleteOne(clickedUser);
                    ShowUserList(dataBaseHelper);
                    Toast.makeText(DBActivity.this, "Deleted " + clickedUser.getName() +" #"+clickedUser.getId(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void ShowUserList(DataBaseHelper dataBaseHelper) {
        userArrayAdapter = new ArrayAdapter<customerModel>(DBActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        highscoreList.setAdapter(userArrayAdapter);
    }
}