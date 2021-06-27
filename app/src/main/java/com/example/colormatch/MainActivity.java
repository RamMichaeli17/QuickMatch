package com.example.colormatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //Topic Animations
    ImageView colorIv, matchIv;
    Animation tweenAnim1, tweenAnim2;


    ImageButton about_us_bn, music_bn, sound_btn;
    MediaPlayer song;
    boolean musicButtonState = true;
    boolean soundButtonState = true;


    Button easyBtn, mediumBtn, hardBtn, dualBtn;
    Animation scaleUp, scaleDown;

    ImageButton goToDB;

    //Pop Up Menu
    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    ImageButton settingsBtn;
    boolean clicked = false;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Click Sound
        final MediaPlayer clickSound = MediaPlayer.create(this,R.raw.click_sound);

        //Topic Animations
        colorIv = findViewById(R.id.color_topic);
        matchIv = findViewById(R.id.match_topic);
        tweenAnim1=AnimationUtils.loadAnimation(this,R.anim.tween_anim_1);
        tweenAnim2=AnimationUtils.loadAnimation(this,R.anim.tween_anim_2);
        colorIv.startAnimation(tweenAnim1);
        matchIv.startAnimation(tweenAnim2);

        //Pop Up Menu
        rotateOpen= AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose= AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom= AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        settingsBtn= findViewById(R.id.settings_btn);



        easyBtn = findViewById(R.id.easy_btn);
        mediumBtn = findViewById(R.id.medium_btn);
        hardBtn = findViewById(R.id.hard_btn);
        dualBtn = findViewById(R.id.dual_btn);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);


        //Pop Up Menu

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked) {
                    about_us_bn.setVisibility(View.VISIBLE);
                    about_us_bn.startAnimation(fromBottom);
                    about_us_bn.setClickable(true);
                    music_bn.setVisibility(View.VISIBLE);
                    music_bn.startAnimation(fromBottom);
                    music_bn.setClickable(true);
                    sound_btn.setVisibility(View.VISIBLE);
                    sound_btn.startAnimation(fromBottom);
                    sound_btn.setClickable(true);
                    goToDB.setVisibility(View.VISIBLE);
                    goToDB.startAnimation(fromBottom);
                    goToDB.setClickable(true);
                    settingsBtn.startAnimation(rotateOpen);
                    clicked=true;
                }
                else {
                    about_us_bn.setVisibility(View.INVISIBLE);
                    about_us_bn.startAnimation(toBottom);
                    about_us_bn.setClickable(false);
                    music_bn.setVisibility(View.INVISIBLE);
                    music_bn.startAnimation(toBottom);
                    music_bn.setClickable(false);
                    sound_btn.setVisibility(View.INVISIBLE);
                    sound_btn.startAnimation(toBottom);
                    sound_btn.setClickable(false);
                    goToDB.setVisibility(View.INVISIBLE);
                    goToDB.startAnimation(toBottom);
                    goToDB.setClickable(false);
                    settingsBtn.startAnimation(rotateClose);
                    clicked=false;
                }
            }
        });



        easyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    easyBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    easyBtn.startAnimation(scaleDown);
                }


                return false;
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


                return false;
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


                return false;
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


                return false;
            }
        });

                easyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSound.start();
                    }
                });

                mediumBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSound.start();
                    }
                });

                hardBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSound.start();
                    }
                });

                dualBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSound.start();
                    }
                });


        song = MediaPlayer.create(MainActivity.this, R.raw.mixaund_happy_day_);
        about_us_bn = (ImageButton) findViewById(R.id.about_us_button);
        music_bn = (ImageButton) findViewById(R.id.music_button);
        sound_btn = (ImageButton) findViewById(R.id.pink_sound_button);

        about_us_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutUsPopup.class));
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


        goToDB=findViewById(R.id.trophy_button);

        goToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
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