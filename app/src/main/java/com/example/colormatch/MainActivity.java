package com.example.colormatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ImageButton about_us_bn, music_bn;
    MediaPlayer song;


    Button easyBtn, mediumBtn, hardBtn, dualBtn;
    Animation scaleUp, scaleDown;

    FloatingActionButton goToDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyBtn = findViewById(R.id.easy_btn);
        mediumBtn = findViewById(R.id.medium_btn);
        hardBtn = findViewById(R.id.hard_btn);
        dualBtn = findViewById(R.id.dual_btn);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        easyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    easyBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    easyBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });

                mediumBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    mediumBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    mediumBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });

                hardBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    hardBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    hardBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });

                dualBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    dualBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    dualBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });







        song=MediaPlayer.create(MainActivity.this,R.raw.boy_oh_boy);
        about_us_bn = (ImageButton)findViewById(R.id.about_us_button);
        music_bn=(ImageButton)findViewById(R.id.music_button);

        about_us_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutUsPopup.class));
            }
        });

        music_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.setLooping(true);
                song.stop();
             /*   music_bn.setImageDrawable();
              if (music_bn.callOnClick()){
                    song.release();
                }*/
            }




        });


        goToDB=findViewById(R.id.goToDBbtn);

        goToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*song.release();*/
        song.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        song.setLooping(true);
        song.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        song.release();
    }
}