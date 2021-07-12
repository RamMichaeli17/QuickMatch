package com.example.colormatch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    ImageView colorIv, matchIv; //Title
    Animation tweenAnim1, tweenAnim2;  //Title animations


    Button beginnerBtn, advancedBtn, professionalBtn, oneVsOneBtn; // Difficulty buttons
    Animation scaleUp, scaleDown; // Buttons animations


    ImageButton about_us_bn, music_bn, sound_btn, goToDB, settingsBtn; //Settings buttons
    MediaPlayer song; //Background songs
    MediaPlayer clickSound;
    boolean musicButtonState = true; //Music mode
    boolean soundButtonState = true; //Sound mode




    //Settings menu
    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    boolean clicked = false;


    private String inputUsername = ""; //***GGGGALLLL****

    Button yesToQuitBTN, noToQuitBTN;//Exit game pop up
    private int currentApiVersion;
    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras(); // If we MainActivity got called by GameActivity , we want to remember sound options
        if(extras !=null) {
            musicButtonState=extras.getBoolean("musicButtonState",true);
            soundButtonState=extras.getBoolean("soundButtonState",true);
        }







        //Topic animations
        colorIv = findViewById(R.id.color_topic);
        matchIv = findViewById(R.id.match_topic);
        tweenAnim1=AnimationUtils.loadAnimation(this,R.anim.tween_anim_1);
        tweenAnim2=AnimationUtils.loadAnimation(this,R.anim.tween_anim_2);
        colorIv.startAnimation(tweenAnim1);
        matchIv.startAnimation(tweenAnim2);

        //Difficulty buttons
        beginnerBtn = findViewById(R.id.easy_btn);
        advancedBtn = findViewById(R.id.medium_btn);
        professionalBtn = findViewById(R.id.hard_btn);
        oneVsOneBtn = findViewById(R.id.dual_btn);

        //Settings menu
        rotateOpen= AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose= AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom= AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        settingsBtn= findViewById(R.id.settings_btn);

        //Click Sound
        clickSound = MediaPlayer.create(this,R.raw.click_sound);
        song = MediaPlayer.create(MainActivity.this, R.raw.mixaund_happy_day_);
        song.setLooping(true);
        song.start();


        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        goToDB=findViewById(R.id.trophy_button);

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
                if(soundButtonState)clickSound.start();

            }
        });

        beginnerBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    beginnerBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    beginnerBtn.startAnimation(scaleDown);
                }


                return false;
            }
        });

                advancedBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    advancedBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    advancedBtn.startAnimation(scaleDown);
                }


                return false;
            }
        });

                professionalBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    professionalBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    professionalBtn.startAnimation(scaleDown);
                }


                return false;
            }
        });

                oneVsOneBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN) {
                    oneVsOneBtn.startAnimation(scaleUp);
                }
                else if (event.getAction()== MotionEvent.ACTION_UP) {
                    oneVsOneBtn.startAnimation(scaleDown);
                }


                return false;
            }
        });

                beginnerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame(1); // 1 = difficulty (1 confusing shapes)
                    }
                });

                advancedBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame(2); // 2 = difficulty (2 confusing shapes)
                    }
                });

                professionalBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame(3); // 3 = difficulty (3 confusing shapes)
                    }
                });

                oneVsOneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(soundButtonState)
                        clickSound.start();
                    }
                });



        about_us_bn = (ImageButton) findViewById(R.id.about_us_button);

        music_bn = (ImageButton) findViewById(R.id.music_button);
        if(!musicButtonState)  // If user went in-game and disabled music , we want to load music_off in mainmenu if he returns to mainmenu
            music_bn.setImageResource(R.drawable.music_off);


        sound_btn = (ImageButton) findViewById(R.id.pink_sound_button);
        if(!soundButtonState) // As written above
            sound_btn.setImageResource(R.drawable.sound_off);

        about_us_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundButtonState)clickSound.start();
               Dialog dialog= new Dialog(MainActivity.this);
               dialog.setContentView(R.layout.about_us_popup);
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.5);
                dialog.getWindow().setLayout(width, height);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

               final TextView infoTV = dialog.findViewById(R.id.info_aboutUS);
               final ImageView logoIV=dialog.findViewById(R.id.logo_in_info);

                dialog.show();
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
                if(soundButtonState)clickSound.start();
            }
        });


        goToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                /* intent.putExtra("selectedMp3",SELECTED_MP3_INT);*/
                startActivity(intent);
                if(soundButtonState)clickSound.start();
            }
        });

        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundButtonState) {
                    clickSound.pause();
                    soundButtonState = false;
                    sound_btn.setImageResource(R.drawable.sound_off);
                    Toast.makeText(MainActivity.this, R.string.no_sound, Toast.LENGTH_SHORT).show();
                    return;
                }
                sound_btn.setImageResource(R.drawable.sound_on);
                clickSound.start();
                Toast.makeText(MainActivity.this, R.string.sound_on, Toast.LENGTH_SHORT).show();
                soundButtonState = true;
                if(soundButtonState)clickSound.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
    /*    startActivity(new Intent(MainActivity.this,AreYouSureYouWantToExit.class));
        // super.onBackPressed();*/
        Dialog dialog= new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.areyousureyouwanttoexit);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.35);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        yesToQuitBTN=dialog.findViewById(R.id.btnyes);
        noToQuitBTN=dialog.findViewById(R.id.btnno);

        yesToQuitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        noToQuitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



//    @Override
//    protected void onPause() {
//        super.onPause();
//        song.pause();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!musicButtonState)
            song.pause();
        else
            song.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        song.pause();
    }

    public void startGame(int difficulty)
    {
        if(soundButtonState)
            clickSound.start();

        Intent myIntent = new Intent(MainActivity.this, GameActivity.class);

        myIntent.putExtra("difficulty",difficulty);
        myIntent.putExtra("musicButtonState",musicButtonState);
        myIntent.putExtra("soundButtonState",soundButtonState);
        myIntent.putExtra("restart",true);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);




    }



}