package com.example.colormatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton about_us_bn, music_bn, sound_btn;
    MediaPlayer song;
    boolean musicButtonState = true;
    boolean soundButtonState = true;


    Button easyBtn, mediumBtn, hardBtn, dualBtn;
    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyBtn = findViewById(R.id.easy_btn);
        mediumBtn = findViewById(R.id.medium_btn);
        hardBtn = findViewById(R.id.hard_btn);
        dualBtn = findViewById(R.id.dual_btn);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);


        easyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    easyBtn.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    easyBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });

        mediumBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mediumBtn.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mediumBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });

        hardBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hardBtn.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    hardBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });

        dualBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dualBtn.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    dualBtn.startAnimation(scaleDown);
                }


                return true;
            }
        });


        song = MediaPlayer.create(MainActivity.this, R.raw.mixaund_happy_day_);
        about_us_bn = (ImageButton) findViewById(R.id.about_us_button);
        music_bn = (ImageButton) findViewById(R.id.music_button);
        sound_btn = (ImageButton) findViewById(R.id.pink_sound_button);

        about_us_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutUsPopup.class));
            }
        });

        music_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicButtonState) {
                    song.pause();
                    musicButtonState = false;
                    music_bn.setImageResource(R.drawable.music_off);
                    Toast.makeText(MainActivity.this, R.string.no_music, Toast.LENGTH_SHORT).show();
                    return;
                }
                song.setLooping(true);
                song.start();
                music_bn.setImageResource(R.drawable.music_on);
                Toast.makeText(MainActivity.this, R.string.music_on, Toast.LENGTH_SHORT).show();
                musicButtonState = true;
            }
        });

        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundButtonState) {
                    soundButtonState = false;
                    sound_btn.setImageResource(R.drawable.sound_off);
                    Toast.makeText(MainActivity.this, R.string.no_sound, Toast.LENGTH_SHORT).show();
                    return;
                }
                song.setLooping(true);
                sound_btn.setImageResource(R.drawable.sound_on);
                Toast.makeText(MainActivity.this, R.string.sound_on, Toast.LENGTH_SHORT).show();
                soundButtonState = true;
            }
        });

    }

/*    @Override
    protected void onPause() {
        super.onPause();
        *//*song.release();*//*
        song.start();
    }*/

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