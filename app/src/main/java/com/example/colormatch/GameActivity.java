package com.example.colormatch;

import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ImageView fourColorsImage, ShapeFillerColor, ShapeOutline;
    ImageView[] answerPositions= new ImageView[4];
    TextView tv_points,highestscoreTV,paused, difficuiltyAlertTV,welldoneTV,fantasticTV;
    ProgressBar progressBar;
    Button continueBTN,exitBTN;
    LinearLayout rotatingAnswersLL;
    boolean firstTimeOnResumeCalled, gameIsNotPaused,airplanepause, restart=false;
    ConstraintLayout fourShapesLayout;
    ImageButton pauseBtn;
    ImageView trapArrows;

    LottieAnimationView fireworks1,fireworks2,welldoneConfeti,airplane;
    RelativeLayout newHighScoreLayout,wellDoneLayout;


    Handler handler;
    Runnable runnable;

    Random r;


    private final static int STATE_BLUE = 1;
    private final static int STATE_GREEN = 2;
    private final static int STATE_RED = 3;
    private final static int STATE_YELLOW = 4;


    int buttonState = STATE_BLUE; // Left side (colors)

    int chosenShape;
    int chosenColor;
    int chosenShapePositionInAnswers;
    int otherThreeAnswers;
    int[] chosenAnswers = new int[16];
    int[] tempIndexArray = new int[4];
    int[] rotation = {0,1,2,3};

    // שיניתי מ12 ל24
    int currentTime = 8000;
    int startTime = 8000;

    int currentPoints = 0;

    int rotationCounter=0;

    int highScore;

    int difficulty;

    int screenWidth;

    ArrayList<HighScoreObject> highScoreList;

    String userName;

    int[] ShapesFillerColorArray= {R.drawable.star_1_fill_color,R.drawable.star_2_fill_color,R.drawable.star_3_fill_color,R.drawable.star_4_fill_color,R.drawable.circles_1_fill_color,R.drawable.circles_2_fill_color,R.drawable.circles_3_fill_color,R.drawable.circles_4_fill_color,R.drawable.three_shapes_1_fill_color,R.drawable.three_shapes_2_fill_color,R.drawable.three_shapes_3_fill_color,R.drawable.three_shapes_4_fill_color,R.drawable.noodles_1_fill_color,R.drawable.noodles_2_fill_color,R.drawable.noodles_3_fill_color,R.drawable.noodles_4_fill_color};
    int[] ShapesOutlineArray= {R.drawable.star_1_outline,R.drawable.star_2_outline,R.drawable.star_3_outline,R.drawable.star_4_outline,R.drawable.circles_1_outline,R.drawable.circles_2_outline,R.drawable.circles_3_outline,R.drawable.circles_4_outline,R.drawable.three_shapes_1_outline,R.drawable.three_shapes_2_outline,R.drawable.three_shapes_3_outline,R.drawable.three_shapes_4_outline,R.drawable.noodles_1_outline,R.drawable.noodles_2_outline,R.drawable.noodles_3_outline,R.drawable.noodles_4_outline};
    private int currentApiVersion;
    @SuppressLint("NewApi")



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);


        //The next 32 lines of code is used to permanently hide & draw over the navigation bar at the right side of the screen
        {
            currentApiVersion = Build.VERSION.SDK_INT;

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
        }


        Bundle extras = getIntent().getExtras(); // Get username from mainActivity (dialog that pops before game)
        if(extras !=null) {
            userName = extras.getString("userName","Unknown");
            restart=extras.getBoolean("restart",false);
            difficulty=extras.getInt("difficulty",1); // 1 Beginner , 2 Adv , 3 Pro
        }

        firstTimeOnResumeCalled=true;
        gameIsNotPaused =true;
        fourColorsImage =findViewById(R.id.fourColorsImage); //צריך להחליף לצבעים ולשנות בהתאם
        ShapeFillerColor =findViewById(R.id.main_shape_color);
        ShapeOutline =findViewById(R.id.main_shape_outline);
        tv_points=findViewById(R.id.tv_points);
        progressBar=findViewById(R.id.progressbar);
        highestscoreTV=findViewById(R.id.highest_score_tv);
        paused=findViewById(R.id.pause);
        /* continueBTN=findViewById(R.id.continueplay);*/
        /*exitBTN=findViewById(R.id.exit);*/
        rotatingAnswersLL=findViewById(R.id.rotating_answers_linear_layout);
        difficuiltyAlertTV =findViewById(R.id.movingShapeAlert);
        fourShapesLayout=findViewById(R.id.fourShapes_layout);


        fireworks1=findViewById(R.id.fireworks1);
        fireworks2=findViewById(R.id.fireworks2);
        welldoneConfeti=findViewById(R.id.welldoneConfetiAnimation);
        wellDoneLayout=findViewById(R.id.welldoneAnimationRelativeLayout);
        welldoneTV=findViewById(R.id.welldoneTV);
        airplane=findViewById(R.id.airplaneAnimation);
        fantasticTV=findViewById(R.id.fantasticWithAirplaneTV);

        newHighScoreLayout=findViewById(R.id.newHighScoreRelativeLayout);

        pauseBtn=findViewById(R.id.pauseBtn);

        trapArrows = findViewById(R.id.trap_arrows_iv);

        // These 4 lines of code are used in airplane animation
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;





        answerPositions[0]=findViewById(R.id.shape_TOP);
        answerPositions[1]=findViewById(R.id.shape_RIGHT);
        answerPositions[2]=findViewById(R.id.shape_BOTTOM);
        answerPositions[3]=findViewById(R.id.shape_LEFT);







        // Shared Preferences - get data
        highScoreList = PrefConfigGal.readListFromPref(this);
        if ( highScoreList ==null || highScoreList.isEmpty()) {
            highestscoreTV.setText("0");
            highScoreList = new ArrayList<>();
        }
        else
            highestscoreTV.setText(highScoreList.get(0).getScore());

        highScore = Integer.parseInt(highestscoreTV.getText().toString()); // save HighScoreObject to an int to avoid calling parseInt/getText/toString multiple times during runtime

        // איך 4 שניות אם למעלה רשום 12 ?
        //set the initial progressbar time to 4 seconds
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText("Points: " + currentPoints);

        //generate random shape and color at the start of the game
        r = new Random();
        setImageShapeAndColor(); // The function will use the above random
        // לכפות על הביגינר שיהיו 2 דומים ולא רק 1 אותו דבר כדי שבאמת יהיה עניין למשחק....

        /*
           in the next few lines of code we generate the chosenShape at a random position (logic is inside functions for reuse later in main-game loop)
           then we generate 3 random shapes at the 3 remaining positions at random        (                             ^^                            )

           Note:
                 since in beginner we don't force similar shapes in the answers (only in harder difficulties) ,
                 it's not important that the other 3 random shapes (the ones that aren't the answer)
                 will be at a random position of the 3 remaining positions (after generateing chosenShape)

                 but when we do want to force similar shapes (in harder difficulties) , we aren't choosing a random shape ,
                 we are choosing a specific shape that looks like chosenShape , so we must put it in a random position
                 (* since the shape isn't random, its placing needs to be random)

                 So I just used random shape + random position to solve this problem (in beginner wont matter , in harder difficulties it will)

                 Answers index will look like this:
                                    1
                                 4     2
                                    3
         */

        chosenShapePositionInAnswers=r.nextInt(4)+1; // 4 possible options for answer to be in (1=top 2= right 3=bottom 4=left
        generateAnswerAtPosition(chosenShapePositionInAnswers,chosenShape);

        generateOtherThreeShapes();


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameIsNotPaused)
                    pauseTheGame();

            }
        });

      /*  continueBTN.setOnClickListener(new View.OnClickListener() {
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
        });*/

        fourColorsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the colors
                setButtonImage(setButtonPosition(buttonState));
            }
        });

        fourShapesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the shapes
                rotateShapes();
            }
        });


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
                    if (true /* for testing purposes - you can't lose.  here should be "if (color and shape is correct)"... else -> you lose*/)
                    {
                        //increase points and show them

                        currentPoints = currentPoints +1;
                        tv_points.setText("Points: "+currentPoints);

                        // reset the chosenAnswers array
                        Arrays.fill(chosenAnswers,0 ); // java method to fill every index in the array with value 0 (best way to reset the array)

                        //make the speed higher after every turn / if the speed is 1 second make it again 2 seconds
                        startTime=startTime-100;
                        if (startTime < 1000){
                            startTime = 2000;
                        }

                        // Adding difficulty to level / Alerting before difficulty level
                        if (currentPoints==1)
                            alertUserToCustomDifficulty();
                        if (currentPoints==2)
                            moveShape();

                        // Checking if new highscore
                        if (highScore!=0 && currentPoints == highScore + 1 ) {
                            playIngameAnimation("newhighscore");
                        }

                        if (currentPoints%4==0)
                            playIngameAnimation("trap_rotate");

                        // When to say welldone
                        if(currentPoints==3)
                            playIngameAnimation("welldone");

                        if(currentPoints==5) {
                            playIngameAnimation("airplane");
                        }



                        progressBar.setMax(startTime);
                        currentTime=startTime;
                        progressBar.setProgress(currentTime);


                        //generate random shape(and color) in the middle of the screen
                        setImageShapeAndColor();

                        //generate the above chosenShape in a random position in the answers
                        //and then generate 3 other random answers in random locations
                        chosenShapePositionInAnswers=r.nextInt(4)+1;
                        generateAnswerAtPosition(chosenShapePositionInAnswers,chosenShape);

                        generateOtherThreeShapes();


                        if (!airplanepause)
                            handler.postDelayed(runnable,20);
                    }
                    else {
                        fourColorsImage.setEnabled(false);
                        fourShapesLayout.setEnabled(false);
                        Toast.makeText(GameActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                        updateHighScores();
                    }
                }
            }
        };

        //start the game loop

        if(!restart)
            handler.postDelayed(runnable,100);
        else
            continueTheGame();
    }

    //rotate animation of the button when clicked
    private void setRotation(final ImageView image, final int drawable)
    {
        //rotate 90 degrees
        RotateAnimation rotateAnimation = new RotateAnimation(0,90, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(75);
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
                setRotation(fourColorsImage,R.drawable.ic_button_blue);
                buttonState = STATE_BLUE;
                break;
            case STATE_RED:
                setRotation(fourColorsImage,R.drawable.ic_button_red);
                buttonState = STATE_RED;
                break;
            case STATE_YELLOW:
                setRotation(fourColorsImage,R.drawable.ic_button_yellow);
                buttonState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                setRotation(fourColorsImage,R.drawable.ic_button_green);
                buttonState = STATE_GREEN;
                break;
        }
    }

    public void rotateShapes() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            Path path2 = new Path();
            Path path3 = new Path();
            Path path4 = new Path();

            //              0
            //            3   1   <---
            //              2
            path.arcTo(answerPositions[3].getLeft(), answerPositions[0].getTop(), answerPositions[1].getLeft(),  answerPositions[2].getTop() , 270f, 90f, true);
            path2.arcTo(answerPositions[3].getLeft(), answerPositions[0].getTop(), answerPositions[1].getLeft(),  answerPositions[2].getTop() , 360f, 90f, true);
            path3.arcTo(answerPositions[3].getLeft(), answerPositions[0].getTop(), answerPositions[1].getLeft(),  answerPositions[2].getTop() , 90, 90f, true);
            path4.arcTo(answerPositions[3].getLeft(), answerPositions[0].getTop(), answerPositions[1].getLeft(),  answerPositions[2].getTop(), 180, 90f, true);


            ObjectAnimator animator1,animator2,animator3,animator4;

            animator1 = ObjectAnimator.ofFloat( answerPositions[(rotation[0])], View.X, View.Y, path);
            animator2 = ObjectAnimator.ofFloat( answerPositions[(rotation[1])] , View.X, View.Y, path2);
            animator3 = ObjectAnimator.ofFloat( answerPositions[(rotation[2])] , View.X, View.Y, path3);
            animator4 = ObjectAnimator.ofFloat( answerPositions[(rotation[3])] , View.X, View.Y, path4);

            for(int i=0;i<=3;i++)
            {
                tempIndexArray[i]=rotation[i];
            }
            for(int i=0;i<=3;i++)
            {
                rotation[i]=tempIndexArray[(i+3)%4];
            }

            // 0 1 2 3
            // 3 0 1 2
            // 2 3 0 1
            // 1 2 3 0
            // 0 1 2 3

            AnimatorSet set = new AnimatorSet();
            set.play(animator1).with(animator2).with(animator3).with(animator4);
            set.setDuration(75);
            set.start();



        }
    }



    private void updateHighScores() //דרוש הסבר
    {
        HighScoreObject newHighScore = new HighScoreObject(userName,Integer.toString(currentPoints)); // Create a new highscore with username and current points
        highScoreList.add(newHighScore); // Add it to the list
        Collections.sort(highScoreList); // Sort the list in descending order so the highest score will be first
        // (this only works because we implemented Comparable in HighScoreObject.java class and override compareTo function

        // Shared Preferences
        PrefConfigGal.writeListInPref(getApplicationContext(), highScoreList); // Write list to shared preferences so it would be saved if we re-open the application
    }

    public void setImageShapeAndColor()
    {
        // This function is used to create the shape+color in the middle of the screen (the main shape)
        chosenShape=r.nextInt(16); // random shape

        ShapeOutline.setImageResource(ShapesOutlineArray[chosenShape]); // Drawing the outline
        ShapeFillerColor.setImageResource(ShapesFillerColorArray[chosenShape]); // Drawing the inside (default red)
        colorTheShape(ShapeFillerColor); // Painting the inside (from default red to random color)

    }


    public void colorTheShape(ImageView theImage)
    {
        chosenColor=r.nextInt(4) + 1; // generate random color
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

    @Override
    public void onBackPressed() {
        System.out.println("onBackPressed()");
        if(gameIsNotPaused)
            pauseTheGame();

    }

    @Override
    protected void onResume() {
        super.onResume();

        /*
           If the user navigates away from our application (clicking home button for example) onPause() gets called which stops the game (without the visuals continueBTN/exitBTN/paused text)
           we want the game to be in paused mode (visually) when the user returns to the app ( onResume() ).

           But because onResume() gets called when the game ("GameActivity.java") first loads , the game starts in paused mode ...
           we don't want that to happen , so if it's the first time onResume has been called (if game just started) , we ignore it (we don't pause)
         */
        if (firstTimeOnResumeCalled || restart==true) {
            firstTimeOnResumeCalled = false;
        }
        else {
            if(gameIsNotPaused)
                pauseTheGame();
        }


    }

    public void alertUserToCustomDifficulty()
    {
        // this function is used to alert the user 1 level before a difficult level comes (for example , moving shape! level)
        difficuiltyAlertTV.animate().alpha(0.6f).setDuration(800);
    }
    public void moveShape()
    {
        difficuiltyAlertTV.animate().alpha(0).setDuration(400);
        // Some animations on the shape in the middle (right to left to center), to increase level difficulty
        ShapeOutline.animate().translationX(800).setDuration(350); // take shape to right side
        ShapeFillerColor.animate().translationX(800).setDuration(350).withEndAction(new Runnable() {
            @Override
            public void run() {
                ShapeOutline.animate().translationX(-800).setDuration(700); // take to left side
                ShapeFillerColor.animate().translationX(-800).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ShapeOutline.animate().translationX(0).setDuration(400); // return to center
                        ShapeFillerColor.animate().translationX(0).setDuration(400);
                    }
                });
            }
        });

    }


    public void generateOtherThreeShapes() // This method sets 3 random WRONG answers at all the positions except the chosenShapePosition
    {
        Arrays.fill(chosenAnswers,0 );
        chosenAnswers[chosenShapePositionInAnswers]=1;
        int firstIndexOfChosenShape=chosenShape,confusingShapes=difficulty; // beginner = 1 confusing shape , advanced =2 , pro =3
        while (firstIndexOfChosenShape%4!=0) {
            System.out.println(" Gal 1 ");
            firstIndexOfChosenShape--;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) { // Make a list with 1,2,3,4
            list.add(new Integer(i));
        }
        Collections.shuffle(list); // Mix the list to get random order of the numbers in it
        for (int i = 0; i <= 3; i++) { // Pull all the unsorted numbers from the above list (list[0],list[1],list[2],list[3]) , these numbers will be used for the next position to draw to
            if (list.get(i)!=chosenShapePositionInAnswers) // this if-statement is to avoid choosing the same position as the correct shape
            {

                if (confusingShapes!=0) { // One confusing shape
                    otherThreeAnswers = r.nextInt(4) + firstIndexOfChosenShape;  // 0->15 This generates a random shape and saves the shape at "otherThreeAnswers'
                    while (chosenAnswers[otherThreeAnswers] == 1) {
                        System.out.println(" Gal 2");
                        for(int j=0;j<16;j++)
                            System.out.println(" ["+j+"] ="+chosenAnswers[j]);

                        otherThreeAnswers = r.nextInt(4) + firstIndexOfChosenShape; // Generate a new random shape until the random shape is unique and isn't a duplicate of an already existing answer)
                    }
                    generateAnswerAtPosition(list.get(i), otherThreeAnswers); // finally , we draw the random image (wrong answer) at a random (empty) position
                    confusingShapes--;
                }
                else
                { // Generate random wrong answer that hasn't been generated before and isn't confusing shape
                    otherThreeAnswers=r.nextInt(16);
                    while (chosenAnswers[otherThreeAnswers] == 1 || (otherThreeAnswers >=firstIndexOfChosenShape && otherThreeAnswers<=firstIndexOfChosenShape+3 )) {
                        System.out.println(" Gal 3 ");
                        otherThreeAnswers = r.nextInt(16);
                    }

                    generateAnswerAtPosition(list.get(i), otherThreeAnswers); // finally , we draw the random image (wrong answer) at a random (empty) position


                }
            }
        }

    }

    public void generateAnswerAtPosition(int pos,int shapeOutline) // This method sets given image(shapeOutline) at given position(pos)
    {
        chosenAnswers[shapeOutline]=1; // Mark answer as used
        switch(pos)
        {
            case 1:
                answerPositions[0].setImageResource(ShapesOutlineArray[shapeOutline]);
                break;
            case 2:
                answerPositions[1].setImageResource(ShapesOutlineArray[shapeOutline]);
                break;
            case 3:
                answerPositions[2].setImageResource(ShapesOutlineArray[shapeOutline]);
                break;
            case 4:
                answerPositions[3].setImageResource(ShapesOutlineArray[shapeOutline]);
        }
    }

    public void pauseTheGame()
    {
        // This method takes care of pausing the game
/*        if(difficuiltyAlertTV.getAlpha()==0.6f) // If a difficuilty alert (moving shape!) is on the screen
            difficuiltyAlertTV.setAlpha(0.1f); // make it less noticeable*/
        gameIsNotPaused=false; // used in many places that needs to know if the game is paused or running
        currentTime=startTime; // reset the time left
        handler.removeCallbacks(runnable); // stop the handler - a way to pause the game
     /*   continueBTN.setVisibility(View.VISIBLE); // show the 'continue' and 'exit' buttons
        exitBTN.setVisibility(View.VISIBLE);*/

        rotatingAnswersLL.setAlpha((float) 0.1); // make the bottom half of the screen (linear layout containing the answers) less noticeable
        fourColorsImage.setClickable(false); // disable the ability to rotate the colors
        fourShapesLayout.setClickable(false); // disable the ability to click on the 4 shape-answers
        paused.setVisibility(View.VISIBLE); // show "Paused"
        ShapeFillerColor.setVisibility(View.INVISIBLE); // remove shape in the middle of the screen
        ShapeOutline.setVisibility(View.INVISIBLE);


        Dialog dialog= new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.activity_paused);


        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button restart_paused=dialog.findViewById(R.id.restart_pauseBtn);
        Button resume_paused=dialog.findViewById(R.id.resume_pauseBtn);
        Button back_to_menu=dialog.findViewById(R.id.back_to_menu_pauseBtn);
        ImageButton music_on=dialog.findViewById(R.id.music_button2);
        ImageButton sound_on=dialog.findViewById(R.id.pink_sound_button2);
        restart_paused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                intent.putExtra("restart",true);
                intent.putExtra("difficulty",difficulty);
                startActivity(intent);
            }
        });

        resume_paused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                continueTheGame();

            }
        });

        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHighScores();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        music_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sound_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    };

    public void continueTheGame() {
        // This method takes care of pausing the game
        if(restart)
        {
            gameIsNotPaused=false;
            paused.setVisibility(View.VISIBLE);
            ShapeFillerColor.setVisibility(View.INVISIBLE);
            ShapeOutline.setVisibility(View.INVISIBLE);
        }
        handler.postDelayed(runnable, 3000); // continue the game after 3 seconds
        // these 4 lines of code is , again , to draw in the middle of the screen a new shape , and then 3 wrong answers and 1 correct answer
        // ** we change the shapes to avoid pause-cheating
        setImageShapeAndColor();
        chosenShapePositionInAnswers = r.nextInt(4) + 1;
        generateAnswerAtPosition(chosenShapePositionInAnswers, chosenShape);
        generateOtherThreeShapes();
        if (difficuiltyAlertTV.getAlpha() == 0.1f) // If a difficuilty alert (moving shape!) is on the screen
            difficuiltyAlertTV.animate().alpha(0.6f).setDuration(1500); // return it to normal alpha



      /*  continueBTN.setVisibility(View.GONE); // remove continue/exit buttons
        exitBTN.setVisibility(View.GONE);*/

        rotatingAnswersLL.animate().alpha(1).setDuration(1500); // return the answers to normal alpha
        paused.setAlpha(0); // This is used (with the animate() 2 rows later) to create the 3-2-1 fading effect
        paused.setText("3");
        paused.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() { // return the "3" alpha to 1 , makes fading effect
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

                                fourColorsImage.setClickable(true); // Make the game playable again (click = rotation)
                                fourShapesLayout.setClickable(true);

                                if (currentPoints == 2) // If this wont be here (When game returns) we lose the "difficulty effect" (moving shape!) when we resume the game
                                    moveShape();

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


    public void playIngameAnimation(String animName)
    {
        switch(animName) {
            case "newhighscore":
                fireworks1.setRepeatCount(1); // 1 *repeat* = 2 in total
                fireworks1.setRepeatMode(LottieDrawable.RESTART);
                fireworks1.setSpeed(0.5f);
                fireworks2.setRepeatCount(1);
                fireworks2.setRepeatMode(LottieDrawable.RESTART);
                fireworks1.playAnimation();
                fireworks2.playAnimation();
                newHighScoreLayout.animate().translationX(620).setDuration(600).alpha(1).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        newHighScoreLayout.animate().translationX(0).alpha(0).setStartDelay(1500);
                    }
                });
                break;

            case "welldone":
                wellDoneLayout.setVisibility(View.VISIBLE);
                welldoneTV.animate().alpha(1).setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        welldoneTV.animate().alpha(0).setDuration(400).setStartDelay(300);
                    }
                });
                welldoneConfeti.setRepeatMode(LottieDrawable.RESTART);
                welldoneConfeti.setRepeatCount(0);
                welldoneConfeti.playAnimation();
                break;

            case "airplane":
                /*
                   We need the fantasticTV to "fly" the screenwidth + 390 device pixels + some extra
                   in order to "fly" it out of the screen
                   the airplane animation is covered by that since its to the right of fantasticTV - it's guaranteed to fly out of the screen
                */
                gameIsNotPaused=false;
                ShapeFillerColor.setVisibility(View.INVISIBLE); // Hide center shape
                ShapeOutline.setVisibility(View.INVISIBLE);
                airplanepause=true; // Disable game loop
                Resources reso = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 450f, reso.getDisplayMetrics() ); // Get 185 in device independent pixels

                fantasticTV.animate().translationX(screenWidth+px).setDuration(3500).start();
                fantasticTV.animate().translationY(40).rotationX(-10).setDuration(1250).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        fantasticTV.animate().translationY(-30).rotationX(10).setDuration(1900).start();
                    }
                });
                airplane.animate().translationX(screenWidth+px).setDuration(3500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ShapeFillerColor.setVisibility(View.VISIBLE);
                        ShapeOutline.setVisibility(View.VISIBLE);
                        airplane.cancelAnimation();
                        gameIsNotPaused=true;
                        airplanepause=false; // Enable game loop
                        handler.postDelayed(runnable,20); // Resume
                    }
                });

                break;

            case "trap_rotate":
                trapArrows.setVisibility(View.VISIBLE);
                ObjectAnimator animator0 = ObjectAnimator.ofFloat(answerPositions[0],"rotation",360).setDuration(750);
                animator0.setRepeatCount(1);
                animator0.start();
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(answerPositions[1],"rotation",360).setDuration(750);
                animator1.setRepeatCount(1);
                animator1.start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(answerPositions[2],"rotation",360).setDuration(750);
                animator2.setRepeatCount(1);
                animator2.start();
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(answerPositions[3],"rotation",360).setDuration(750);
                animator3.setRepeatCount(1);
                animator3.start();
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(trapArrows,"alpha",0).setDuration(750);
                animator4.start();




        }
    }

import android.util.DisplayMetrics;
import android.view.Gravity;

    @Override
    protected void onPause() {
        System.out.println("OnPause()");
        handler.removeCallbacks(runnable);
        super.onPause();
    }



}