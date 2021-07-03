package com.example.colormatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class paused extends Activity {

    Button continueBTN,exitBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paused);



        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width),(int)(height));

        continueBTN=findViewById(R.id.continueplay);
        exitBTN=findViewById(R.id.exit);

        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(R.layout.paused);
                finishActivity(R.layout.activity_game_activity);
                startActivity(new Intent(paused.this,MainActivity.class));
            }
        });


    }
}
