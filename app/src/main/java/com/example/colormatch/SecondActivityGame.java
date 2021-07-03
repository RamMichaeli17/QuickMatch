package com.example.colormatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SecondActivityGame extends AppCompatActivity {

    ImageView iv_button,iv_button2, iv_arrow , iv_arrow2stroke;
    TextView tv_points,highestscoreTV,paused;
    ProgressBar progressBar;

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

    ArrayList<Person> peopleList;

    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);

        Bundle extras = getIntent().getExtras(); // Get username from mainActivity (dialog that pops before game)
        if(extras !=null) {
             userName = extras.getString("userName");
        }

        iv_button=findViewById(R.id.iv_button);
       // iv_button2=findViewById(R.id.iv_button2a);
        iv_arrow=findViewById(R.id.iv_arrow);
        iv_arrow2stroke=findViewById(R.id.iv_arrow2stroke);
        tv_points=findViewById(R.id.tv_points);
        progressBar=findViewById(R.id.progressbar);
        highestscoreTV=findViewById(R.id.highestscoreTV);
        paused=findViewById(R.id.pause);



        // Shared Preferences - get data
        peopleList = PrefConfigGal.readListFromPref(this);
        if ( peopleList==null || peopleList.isEmpty()) {
            highestscoreTV.setText("0");
            peopleList = new ArrayList<>();
        }
        else
            highestscoreTV.setText(peopleList.get(0).getScore());



        //set the initial progressbar time to 4 seconds
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText("Points: " + currentPoints);

        //generate random arrow color at the start of the game
        //generate random shape and color at the start of the game
        r = new Random();
        chosenShape= 1; // starts from 1 -> last for testing purposes
        setImageShape(chosenShape);
       // setImageColor(chosenColor);

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
                            progressBar.setMax(startTime);
                            currentTime=startTime;
                            progressBar.setProgress(currentTime);

                            //generate new color of the arrow
                               //arrowState=r.nextInt(4) +1;
                               //  setArrowImage(arrowState);


                            //generate new color and shape
                           // chosenShape=r.nextInt(4) + 1;

                            // Show all shapes one after another - for testing purposes
                           if(chosenShape!=16)
                                chosenShape++;
                            else
                                chosenShape=1;


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
                iv_arrow.setImageResource(R.drawable.ic_blue);
                arrowState = STATE_BLUE;
                break;
            case STATE_RED:
                iv_arrow.setImageResource(R.drawable.ic_red);
                arrowState = STATE_RED;
                break;
            case STATE_YELLOW:
                iv_arrow.setImageResource(R.drawable.ic_yellow);
                arrowState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                iv_arrow.setImageResource(R.drawable.ic_green);
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
        Person newPerson = new Person(userName,Integer.toString(currentPoints));

        peopleList.add(newPerson);


        Collections.sort(peopleList);

        // Shared Preferences
        PrefConfigGal.writeListInPref(getApplicationContext(),peopleList);
        //

    }
 //   setImageShape(chosenShape);
   // setImageColor(chosenColor);

    public void setImageShape(int theShape)
    {
        switch (theShape) {
            case 1:
                chosenShape = 1;
                iv_arrow2stroke.setImageResource(R.drawable.ic_oie_6ppwctotng3q222222222222222222);
                iv_arrow.setImageResource(R.drawable.ic_oie_6ppwctotng3q);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;

            case 2:
                chosenShape = 2;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_a_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_a_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 3:
                chosenShape = 3;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_b_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_b_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 4:
                chosenShape = 4;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_c_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_c_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 5:
                chosenShape = 5;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_d_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_d_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 6:
                chosenShape = 6;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_e_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_e_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 7:
                chosenShape = 7;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_f_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_f_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 8:
                chosenShape = 8;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_g_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_g_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 9:
                chosenShape = 9;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_h_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_h_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 10:
                chosenShape = 10;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_i_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_i_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 11:
                chosenShape = 11;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_j_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_j_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 12:
                chosenShape = 12;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_k_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_k_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;
            case 13:
                chosenShape = 13;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_l_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_l_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);

            case 14:
                chosenShape = 14;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_m_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_m_color);
                chosenColor=r.nextInt(4) + 1;
                colorTheShape(iv_arrow);
                break;

            case 15:
                chosenShape = 15;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_n_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_n_color);
                colorTheShape(iv_arrow);
                break;

            case 16:
                chosenShape = 16;
                iv_arrow2stroke.setImageResource(R.drawable.ic_strangeshape_o_outline);
                iv_arrow.setImageResource(R.drawable.ic_strangeshape_o_color);
                colorTheShape(iv_arrow);
                break;



        }
    }

    public void colorTheShape(ImageView theImage)
    {
        chosenColor=r.nextInt(4) + 1;
        switch (chosenColor)
        {
            case 1:
                theImage.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                theImage.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 3:
                theImage.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 4:
                theImage.setColorFilter(ContextCompat.getColor(iv_arrow.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
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

        handler.removeCallbacks(runnable);
        paused.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(SecondActivityGame.this,paused.class);
        startActivity(myIntent);

    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable,0);
        currentTime=0;
        currentPoints--;
        paused.setVisibility(View.INVISIBLE);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
}