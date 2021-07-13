package com.example.colormatch;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    ListView scorelistview;
    Button clearBTN, yesToClearBTN, cancelToClearBTN;
    ArrayList<HighScoreObject> highScoreObjectArrayList;
    ImageView backgroundStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);


        scorelistview=findViewById(R.id.listView);
        clearBTN=findViewById(R.id.clearBTN);


        // Shared Preferences - get data
        highScoreObjectArrayList = ConfigSharedPreferences.readListFromPref(this);
        if ( highScoreObjectArrayList ==null)
            highScoreObjectArrayList = new ArrayList<>();

        // Initial screen load
        refreshScreen();

        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(HighScore.this);
                dialog.setContentView(R.layout.areyousureyouwanttoclear);

                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.45);
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                yesToClearBTN=dialog.findViewById(R.id.btnyes);
                cancelToClearBTN=dialog.findViewById(R.id.btnno);

                yesToClearBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        highScoreObjectArrayList.clear();
                        ConfigSharedPreferences.writeListInPref(getApplicationContext(), highScoreObjectArrayList); // This line ensures that both lists (from game and from highscores) reset
                        refreshScreen();
                        dialog.dismiss();
                    }
                });

                cancelToClearBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }


    public void refreshScreen()
    {
        listview_adapter adapter = new listview_adapter(HighScore.this, R.layout.adapter_listview_item, highScoreObjectArrayList);
        scorelistview.setAdapter(adapter);
    }
}