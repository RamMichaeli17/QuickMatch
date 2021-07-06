package com.example.colormatch;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SecondActivityGame extends AppCompatActivity {

    ImageView iv_button,iv_button2, ShapeFillerColor, ShapeOutline;
    TextView tv_points,highestscoreTV,paused, difficuiltyAlertTV;
    ProgressBar progressBar;
    Button continueBTN,exitBTN;
    LinearLayout rotatingAnswersLL;
    boolean firstTimeOnResumeCalled, gameIsNotPaused;
    

    Handler handler;
    Runnable runnable;

    Random r;


    private final static int STATE_BLUE = 1;
    private final static int STATE_GREEN = 2;
    private final static int STATE_RED = 3;
    private final static int STATE_YELLOW = 4;


    int buttonState = STATE_BLUE;
    int buttonState2 = STATE_BLUE;

    int arrowState = STATE_BLUE; // DELETE
    int chosenShape = 1;
    int chosenColor = 1;

    int currentTime = 12000;
    int startTime = 12000;

    int currentPoints = 0;

    ArrayList<highScore> highScoreList;

    String userName;

    int[] ShapesFillerColor = {R.drawable.ic_r_strangeshape_firstshape_color,R.drawable.ic_strangeshape_a_color,R.drawable.ic_strangeshape_b_color,R.drawable.ic_strangeshape_c_color,R.drawable.ic_strangeshape_d_color,R.drawable.ic_strangeshape_e_color,R.drawable.ic_strangeshape_f_color,R.drawable.ic_strangeshape_g_color,R.drawable.ic_strangeshape_h_color,R.drawable.ic_strangeshape_i_color,R.drawable.ic_strangeshape_j_color,R.drawable.ic_strangeshape_k_color,R.drawable.ic_strangeshape_l_color,R.drawable.ic_strangeshape_m_color,R.drawable.ic_strangeshape_n_color,R.drawable.ic_strangeshape_o_color};
    int[] ShapesOutline = {R.drawable.ic_r_strangeshape_firstshape_outline,R.drawable.ic_strangeshape_a_outline,R.drawable.ic_strangeshape_b_outline,R.drawable.ic_strangeshape_c_outline,R.drawable.ic_strangeshape_d_outline,R.drawable.ic_strangeshape_e_outline,R.drawable.ic_strangeshape_f_outline,R.drawable.ic_strangeshape_g_outline,R.drawable.ic_strangeshape_h_outline,R.drawable.ic_strangeshape_i_outline,R.drawable.ic_strangeshape_j_outline,R.drawable.ic_strangeshape_k_outline,R.drawable.ic_strangeshape_l_outline,R.drawable.ic_strangeshape_m_outline,R.drawable.ic_strangeshape_n_outline,R.drawable.ic_strangeshape_o_outline};


    private int currentApiVersion;
    @SuppressLint("NewApi")



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);


        //The next 32 lines of code is used to permanently hide & draw over the navigation bar at the right side of the screen
        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }






        Bundle extras = getIntent().getExtras(); // Get username from mainActivity (dialog that pops before game)
        if(extras !=null) {
             userName = extras.getString("userName");
        }

        firstTimeOnResumeCalled=true;
        gameIsNotPaused =true;
        iv_button=findViewById(R.id.iv_button);
       // iv_button2=findViewById(R.id.iv_button2a);
        ShapeFillerColor =findViewById(R.id.iv_arrow);
        ShapeOutline =findViewById(R.id.iv_arrow2stroke);
        tv_points=findViewById(R.id.tv_points);
        progressBar=findViewById(R.id.progressbar);
        highestscoreTV=findViewById(R.id.highestscoreTV);
        paused=findViewById(R.id.pause);
        continueBTN=findViewById(R.id.continueplay);
        exitBTN=findViewById(R.id.exit);
        rotatingAnswersLL=findViewById(R.id.rotatingAnswersLinearLayout);
        difficuiltyAlertTV =findViewById(R.id.movingShapeAlert);


        // Shared Preferences - get data
        highScoreList = PrefConfigGal.readListFromPref(this);
        if ( highScoreList ==null || highScoreList.isEmpty()) {
            highestscoreTV.setText("0");
            highScoreList = new ArrayList<>();
        }
        else
            highestscoreTV.setText(highScoreList.get(0).getScore());



        //set the initial progressbar time to 4 seconds
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText("Points: " + currentPoints);

        //generate random arrow color at the start of the game
        //generate random shape and color at the start of the game
        r = new Random();
        chosenShape= 0; // starts from 0 -> last for testing purposes
        setImageShape(chosenShape);
       // setImageColor(chosenColor);

        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueTheGame();
            }
        });

        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHighScores();
                finish();
            }
        });

        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the button with the colors
                setButtonImage(setButtonPosition(buttonState));
            }
        });


//        iv_button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setButtonImage2(setButtonPosition(buttonState2));
//            }
//        });


        //main game loop
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //show progress
                currentTime = currentTime-100;
                progressBar.setProgress(currentTime);

                //check if there is still some time left in the progressBar
                if (currentTime>0) {
                    handler.postDelayed(runnable, 10);
                }
                    else{ // check if the colors of the arrow and the button are the same
                        if (true /*buttonState == arrowState && buttonState2 == arrowState*/)
                        {
                            //increase points and show time

                            currentPoints = currentPoints +1;
                            tv_points.setText("Points: "+currentPoints);

                            //make the speed higher after every turn / if the speed is 1 second make it again 2 seconds
                            startTime=startTime-100;
                            if (startTime < 1000){
                                startTime = 2000;
                            }

                            // Adding difficulty
                            if (currentPoints==1)
                                alertUserToCustomDifficulty();
                            if (currentPoints==2)
                                moveShape();



                            progressBar.setMax(startTime);
                            currentTime=startTime;
                            progressBar.setProgress(currentTime);

                            //generate new color of the arrow
                               //arrowState=r.nextInt(4) +1;
                               //  setArrowImage(arrowState);


                            //generate new color and shape
                           // chosenShape=r.nextInt(4) + 1;

                            // Show all shapes one after another - for testing purposes [0->15 = 16 shapes]
                            chosenShape++;
                           if(chosenShape==16)
                                chosenShape=0;


                            setImageShape(chosenShape);
                            //setImageColor(chosenColor);

                            handler.postDelayed(runnable,20);
                        }
                        else {
                            iv_button.setEnabled(false);
                            iv_button2.setEnabled(false);
                            Toast.makeText(SecondActivityGame.this, "Game Over", Toast.LENGTH_SHORT).show();
                            updateHighScores();
                        }
                }
            }
        };

        //start the game loop
        handler.postDelayed(runnable,100);

    }
    //display the arrow color according to the generated number
    private void setArrowImage(int state)
    {
        switch (state) {
            case STATE_BLUE:
                ShapeFillerColor.setImageResource(R.drawable.ic_blue);
                arrowState = STATE_BLUE;
                break;
            case STATE_RED:
                ShapeFillerColor.setImageResource(R.drawable.ic_red);
                arrowState = STATE_RED;
                break;
            case STATE_YELLOW:
                ShapeFillerColor.setImageResource(R.drawable.ic_yellow);
                arrowState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                ShapeFillerColor.setImageResource(R.drawable.ic_green);
                arrowState = STATE_GREEN;
                break;
        }
    }

    //rotate animation of the button when clicked
    private void setRotation(final ImageView image, final int drawable)
    {
        //rotate 90 degrees
        RotateAnimation rotateAnimation = new RotateAnimation(0,90, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(drawable);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(rotateAnimation);
    }

    //set button colors position 1-4
    private int setButtonPosition(int position)
    {
        position = position + 1;
        if (position == 5)
            position = 1;
        return position;
    }

    //display the button colors positions
    private void setButtonImage(int state){
        switch (state) {
            case STATE_BLUE:
                setRotation(iv_button,R.drawable.ic_button_blue);
                buttonState = STATE_BLUE;
                break;
            case STATE_RED:
                setRotation(iv_button,R.drawable.ic_button_red);
                buttonState = STATE_RED;
                break;
            case STATE_YELLOW:
                setRotation(iv_button,R.drawable.ic_button_yellow);
                buttonState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                setRotation(iv_button,R.drawable.ic_button_green);
                buttonState = STATE_GREEN;
                break;
        }
    }

    // Right side
    private void setButtonImage2(int state){
        switch (state) {
            case STATE_BLUE:
                setRotation(iv_button2,R.drawable.ic_button_blue);
                buttonState2 = STATE_BLUE;
                break;
            case STATE_RED:
                setRotation(iv_button2,R.drawable.ic_button_red);
                buttonState2 = STATE_RED;
                break;
            case STATE_YELLOW:
                setRotation(iv_button2,R.drawable.ic_button_yellow);
                buttonState2 = STATE_YELLOW;
                break;
            case STATE_GREEN:
                setRotation(iv_button2,R.drawable.ic_button_green);
                buttonState2 = STATE_GREEN;
                break;
        }
    }

    private void updateHighScores()
    {
        highScore newHighScore = new highScore(userName,Integer.toString(currentPoints)); // Create a new highscore with username and current points
        highScoreList.add(newHighScore); // Add it to the list
        Collections.sort(highScoreList); // Sort the list in descending order , so the highest score will be first
        // Shared Preferences
        PrefConfigGal.writeListInPref(getApplicationContext(), highScoreList); // Write list to shared preferences for other activities to use
    }

    public void setImageShape(int theShape)
    {
        chosenShape = theShape;
        chosenColor=r.nextInt(4) + 1;

                ShapeOutline.setImageResource(ShapesOutline[theShape]);
                ShapeFillerColor.setImageResource(ShapesFillerColor[theShape]);
                colorTheShape(ShapeFillerColor);

    }


    public void colorTheShape(ImageView theImage)
    {
        chosenColor=r.nextInt(4) + 1;
        switch (chosenColor)
        {
            case 1:
                theImage.setColorFilter(ContextCompat.getColor(ShapeFillerColor.getContext(), R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                theImage.setColorFilter(ContextCompat.getColor(ShapeFillerColor.getContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 3:
                theImage.setColorFilter(ContextCompat.getColor(ShapeFillerColor.getContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 4:
                theImage.setColorFilter(ContextCompat.getColor(ShapeFillerColor.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
        }
    }

//    public void setImageColor(int theColor)
//    {
//        switch (theColor) {
//            case STATE_BLUE:
//                break;
//            case STATE_GREEN:
//                iv_arrow.setImageResource(R.drawable.ic_oie_6ppwctotng3q);
//                iv_arrow.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
//                chosenColor = STATE_GREEN;
//                break;
//           case STATE_RED:
//                iv_arrow.setImageResource(R.drawable.ic_oie_6ppwctotng3q);
//                iv_arrow.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
//                chosenColor = STATE_RED;
//                break;
//            case STATE_YELLOW:
//                 iv_arrow.setImageResource(R.drawable.ic_oie_6ppwctotng3q);
//                 iv_arrow.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
//                 chosenColor = STATE_YELLOW;
//                 break;
//
//        }
//    }

    @Override
    public void onBackPressed() {
        System.out.println("onBackPressed()");
        if(gameIsNotPaused)
        pauseTheGame();

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("OnResume()");

        if (firstTimeOnResumeCalled) {
            firstTimeOnResumeCalled = false;
        }
        else {
            if(gameIsNotPaused)
             pauseTheGame();
        }


    }

    public void alertUserToCustomDifficulty()
    {
        difficuiltyAlertTV.animate().alpha(0.6f).setDuration(800);
    }
    public void moveShape()
    {

        difficuiltyAlertTV.animate().alpha(0).setDuration(400);

        ShapeOutline.animate().translationX(800).setDuration(350);
        ShapeFillerColor.animate().translationX(800).setDuration(350).withEndAction(new Runnable() {
            @Override
            public void run() {
                ShapeOutline.animate().translationX(-800).setDuration(700);
                ShapeFillerColor.animate().translationX(-800).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ShapeOutline.animate().translationX(0).setDuration(400);
                        ShapeFillerColor.animate().translationX(0).setDuration(400);
                    }
                });
            }
        });

    }

    public void pauseTheGame()
    {
        if(difficuiltyAlertTV.getAlpha()==0.6f)
            difficuiltyAlertTV.setAlpha(0.1f);
        gameIsNotPaused=false;
        currentTime=startTime;
        handler.removeCallbacks(runnable); // stop the handler - a way to pause the game
        continueBTN.setVisibility(View.VISIBLE);
        exitBTN.setVisibility(View.VISIBLE);
        rotatingAnswersLL.setAlpha((float) 0.1);
        iv_button.setClickable(false);
        paused.setVisibility(View.VISIBLE);
        ShapeFillerColor.setVisibility(View.INVISIBLE);
        ShapeOutline.setVisibility(View.INVISIBLE);
    }

    public void continueTheGame()
    {
        if(difficuiltyAlertTV.getAlpha()==0.1f)
            difficuiltyAlertTV.animate().alpha(0.6f).setDuration(1500);
        continueBTN.setVisibility(View.GONE);
        exitBTN.setVisibility(View.GONE);
        handler.postDelayed(runnable,3000);
        paused.setText("3");
        rotatingAnswersLL.animate().alpha(1).setDuration(1500);
        paused.setAlpha(0);
        if (!gameIsNotPaused) {
            paused.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    paused.setAlpha(0);
                    paused.setText("2");
                    paused.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            paused.setAlpha(0);
                            paused.setText("1");
                            paused.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    paused.setVisibility(View.GONE);
                                    paused.setText("Paused");

                                    iv_button.setClickable(true);

                                    gameIsNotPaused = true;

                                    ShapeFillerColor.setVisibility(View.VISIBLE);
                                    ShapeOutline.setVisibility(View.VISIBLE);

                                }
                            });
                        }
                    });

                }
            });
        }


        /*
        Handler resumeGameHandler;



        if (!gameIsNotPaused) {
            resumeGameHandler = new Handler();
            resumeGameHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    paused.setText("2");
                }
            }, 1000);

            resumeGameHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    paused.setText("1");
                }
            }, 2000);

            resumeGameHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    paused.setVisibility(View.GONE);
                    paused.setText("Paused");

                    iv_button.setClickable(true);

                    gameIsNotPaused = true;

                    ShapeFillerColor.setVisibility(View.VISIBLE);
                    ShapeOutline.setVisibility(View.VISIBLE);

                }
            }, 3000);
        }*/

    }

    @Override
    protected void onPause() {
        System.out.println("OnPause()");
        handler.removeCallbacks(runnable);
        if (gameIsNotPaused)
            pauseTheGame();
        super.onPause();
    }



}