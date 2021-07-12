package com.example.colormatch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Tutorial extends AppCompatActivity {


    ImageView fourColorsImage, ShapeFillerColor, ShapeOutline;
    ImageView[] answerPositions = new ImageView[4];
    TextView tv_points, highestscoreTV, paused, difficuiltyAlertTV, welldoneTV, fantasticTV, levelsplayedVALUETV, timeplayedVALUETV, finalscoreVALUETV;
    ProgressBar progressBar;
    Button nextBtn;
    LinearLayout rotatingAnswersLL;
    boolean firstTimeOnResumeCalled, gameIsNotPaused, airplanepause, restart = false, firstTimePlaying;
    ConstraintLayout fourShapesLayout;
    ImageButton pauseBtn;
    ImageView trapArrows;


    TextView tutProgBar,tutCenterShape,colorstut1,colorstut2,tutShape,tutThatsIt,welcometv;
    ImageView colorstutarrow;
    Button finishtut,replaytut;

    LottieAnimationView tutProgBarArrow;





    ObjectAnimator animator0, animator1, animator2, animator3, animator4;
    AnimatorSet traprotate_animationSet = new AnimatorSet();


    LottieAnimationView fireworks1, fireworks2, welldoneConfeti, airplane;
    RelativeLayout newHighScoreLayout, wellDoneLayout;


    Handler handler, rotateHandler;
    Runnable runnable, rotateRunnable;

    Random r;


    private final static int STATE_BLUE = 1;
    private final static int STATE_GREEN = 2;
    private final static int STATE_RED = 3;
    private final static int STATE_YELLOW = 4;


    int buttonState = STATE_BLUE; // Left side (colors)

    int chosenShape, selectedShape;
    int chosenColor, selectedColor = STATE_BLUE;
    int chosenShapePositionInAnswers;
    int otherThreeAnswers;
    int[] chosenAnswers = new int[16];
    int[] tempIndexArray = new int[4];
    int[] rotation = {0, 3, 2, 1};

    // שיניתי מ12 ל24
    int currentTime = 28000;
    int startTime = 28000;

    int currentPoints = 0;


    int rotationCounter = 0;


    int difficulty;
    int nextCounter=0;
    boolean nextCooldown=true;

    Thread cooldown;



    ArrayList<HighScoreObject> highScoreList;

    String userName;

    MediaPlayer song; //Background songs
    MediaPlayer swipeSound;
    MediaPlayer clickSound;
    boolean musicButtonState; //Music mode
    boolean soundButtonState; //Sound mode

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
            if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

                getWindow().getDecorView().setSystemUiVisibility(flags);

                // Code below is to handle presses of Volume up or Volume down.
                // Without this, after pressing volume buttons, the navigation bar will
                // show up and won't hide
                final View decorView = getWindow().getDecorView();
                decorView
                        .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                            @Override
                            public void onSystemUiVisibilityChange(int visibility) {
                                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                    decorView.setSystemUiVisibility(flags);
                                }
                            }
                        });
            }
        }


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            musicButtonState = extras.getBoolean("musicButtonState", true);
            soundButtonState = extras.getBoolean("soundButtonState", true);
        }


        gameIsNotPaused = true;
        fourColorsImage = findViewById(R.id.fourColorsImage);
        ShapeFillerColor = findViewById(R.id.main_shape_color);
        ShapeOutline = findViewById(R.id.main_shape_outline);
        tv_points = findViewById(R.id.tv_points);
        progressBar = findViewById(R.id.progressbar);
        highestscoreTV = findViewById(R.id.highest_score_tv);
        rotatingAnswersLL = findViewById(R.id.rotating_answers_linear_layout);
        fourShapesLayout = findViewById(R.id.fourShapes_layout);
        nextBtn=findViewById(R.id.nextBtn);
        tutProgBar=findViewById(R.id.tutorialprogbar);
        tutProgBarArrow=findViewById(R.id.tutorialprogbarArrow);
        tutCenterShape=findViewById(R.id.centerShapeTut);
        colorstut1=findViewById(R.id.colorstut1);
        colorstut2=findViewById(R.id.colorstut2);
        colorstutarrow=findViewById(R.id.colorstutarrowdown);
        tutShape=findViewById(R.id.shapestut);
        tutThatsIt=findViewById(R.id.thatsitTut);
        finishtut=findViewById(R.id.finishTutbtn);
        replaytut=findViewById(R.id.replayTutbtn);
        welcometv=findViewById(R.id.welcometuttv);



       /* Hide all */
        fourColorsImage.setAlpha(0);
        ShapeFillerColor.setAlpha(0);
        ShapeOutline.setAlpha(0);
        tv_points.setVisibility(View.INVISIBLE);
        highestscoreTV.setVisibility(View.INVISIBLE);
        rotatingAnswersLL.setAlpha(0);
        fourShapesLayout.setAlpha(0);

        nextBtn.setVisibility(View.VISIBLE);





        pauseBtn = findViewById(R.id.pauseBtn);

        trapArrows = findViewById(R.id.trap_arrows_iv);

        //Click Sound
        swipeSound = MediaPlayer.create(this, R.raw.press_game);
        clickSound = MediaPlayer.create(this, R.raw.click_sound);
        song = MediaPlayer.create(Tutorial.this, R.raw.during_game_music);
        song.setLooping(true);
        song.start();

        if (musicButtonState) {
            song.setVolume(1, 1);
        } else
            song.setVolume(0, 0);


        if (soundButtonState)
            clickSound.start();
        else {
            swipeSound.pause();
            clickSound.pause();
        }


        answerPositions[0] = findViewById(R.id.shape_TOP);
        answerPositions[1] = findViewById(R.id.shape_LEFT);
        answerPositions[2] = findViewById(R.id.shape_BOTTOM);
        answerPositions[3] = findViewById(R.id.shape_RIGHT);


        highestscoreTV.setText("12");

        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText(R.string.points_calculator + currentPoints);

        //generate random shape and color for tutorial
        r = new Random();
        setImageShapeAndColor();


        generateAnswerAtPosition(2, chosenShape); // Correct answer will be at the bottom always

        generateOtherThreeShapes();

        welcometv.animate().alpha(1).setDuration(400);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextCooldown)
                {
                    nextCounter++;
                    nextCooldown=false;
                    switch (nextCounter)
                    {
                        case 1:
                            progressBarTutorial();
                            cooldownNext();
                            nextBtn.animate().alpha(0.3f).setDuration(500);
                            break;
                        case 2:
                            centerShapeTutorial();
                            cooldownNext();
                            nextBtn.animate().alpha(0.3f).setDuration(500);
                            break;
                        case 3:
                            colorsTutorial();
                            cooldownNext();
                            nextBtn.animate().alpha(0.3f).setDuration(500);
                            break;
                        case 4:
                            colorsTutorial2();
                            cooldownNext();
                            nextBtn.animate().alpha(0.3f).setDuration(500);
                            break;
                        case 5:
                            shapeTutorial();
                            cooldownNext();
                            nextBtn.animate().alpha(0.3f).setDuration(500);
                            break;
                        case 6:
                            thatsall();
                            break;
                    }

                }
            }
        });

        finishtut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundButtonState)clickSound.start();
                Intent intent = new Intent(Tutorial.this, MainActivity.class);
                intent.putExtra("musicButtonState",musicButtonState);
                intent.putExtra("soundButtonState",soundButtonState);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        replaytut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundButtonState)clickSound.start();
                Intent intent = new Intent(Tutorial.this, Tutorial.class);
                intent.putExtra("musicButtonState",musicButtonState);
                intent.putExtra("soundButtonState",soundButtonState);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundButtonState) clickSound.start();
                if (gameIsNotPaused)
                    pauseTheGame();

            }
        });


        fourColorsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the colors
                if (soundButtonState) swipeSound.start();

                selectedColor = (selectedColor + 1) % 5; // 1->2->3->4 -->  0(1)->2->3->4 -->  0(1)->2....
                if (selectedColor == 0)
                    selectedColor = 1;

                setButtonImage(setButtonPosition(buttonState));
            }
        });

        fourShapesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the shapes
                if (soundButtonState) swipeSound.start();

                rotationCounter++;      // 0->1->2->3 --> 0->3->2->1
                if (rotationCounter == 4)
                    rotationCounter = 0;

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
                } else {

                }
            }
        };

    }

    //rotate animation of the button when clicked
    private void setRotation(final ImageView image, final int drawable) {
        //rotate 90 degrees
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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
    private int setButtonPosition(int position) {
        position = position + 1;
        if (position == 5)
            position = 1;
        return position;
    }

    //display the button colors positions
    private void setButtonImage(int state) {
        switch (state) {
            case STATE_BLUE:
                setRotation(fourColorsImage, R.drawable.newcolors); // = default blue top
                buttonState = STATE_BLUE;
                break;
            case STATE_RED:
                setRotation(fourColorsImage, R.drawable.colorsredtop);
                buttonState = STATE_RED;
                break;
            case STATE_YELLOW:
                setRotation(fourColorsImage, R.drawable.colorsyellowtop);
                buttonState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                setRotation(fourColorsImage, R.drawable.colorsgreentop);
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
            path.arcTo(answerPositions[1].getLeft(), answerPositions[0].getTop(), answerPositions[3].getLeft(), answerPositions[2].getTop(), 270f, 90f, true);
            path2.arcTo(answerPositions[1].getLeft(), answerPositions[0].getTop(), answerPositions[3].getLeft(), answerPositions[2].getTop(), 360f, 90f, true);
            path3.arcTo(answerPositions[1].getLeft(), answerPositions[0].getTop(), answerPositions[3].getLeft(), answerPositions[2].getTop(), 90, 90f, true);
            path4.arcTo(answerPositions[1].getLeft(), answerPositions[0].getTop(), answerPositions[3].getLeft(), answerPositions[2].getTop(), 180, 90f, true);


            ObjectAnimator animator1, animator2, animator3, animator4;

            animator1 = ObjectAnimator.ofFloat(answerPositions[(rotation[0])], View.X, View.Y, path);
            animator2 = ObjectAnimator.ofFloat(answerPositions[(rotation[1])], View.X, View.Y, path2);
            animator3 = ObjectAnimator.ofFloat(answerPositions[(rotation[2])], View.X, View.Y, path3);
            animator4 = ObjectAnimator.ofFloat(answerPositions[(rotation[3])], View.X, View.Y, path4);

            for (int i = 0; i <= 3; i++) {
                tempIndexArray[i] = rotation[i];
            }
            for (int i = 0; i <= 3; i++) {
                rotation[i] = tempIndexArray[(i + 3) % 4];
            }

            AnimatorSet set = new AnimatorSet();
            set.play(animator1).with(animator2).with(animator3).with(animator4);
            set.setDuration(75);
            set.start();
        }
    }


    private void updateHighScores() //דרוש הסבר
    {
        HighScoreObject newHighScore = new HighScoreObject(userName, Integer.toString(currentPoints)); // Create a new highscore with username and current points
        highScoreList.add(newHighScore); // Add it to the list
        Collections.sort(highScoreList); // Sort the list in descending order so the highest score will be first
        // (this only works because we implemented Comparable in HighScoreObject.java class and override compareTo function

        // Shared Preferences
        ConfigSharedPreferences.writeListInPref(getApplicationContext(), highScoreList); // Write list to shared preferences so it would be saved if we re-open the application
    }

    public void setImageShapeAndColor() {
        // This function is used to create the shape+color in the middle of the screen (the main shape)
        rotationCounter = chosenShapePositionInAnswers; // re-adjust rotation counter
        chosenShape = r.nextInt(16); // random shape
        ShapeOutline.setImageResource(ShapesOutlineArray[chosenShape]); // Drawing the outline
        ShapeFillerColor.setImageResource(ShapesFillerColorArray[chosenShape]); // Drawing the inside (default red)
        colorTheShape(ShapeFillerColor); // Painting the inside (from default red to random color)
    }


    public void colorTheShape(ImageView theImage) {
        chosenColor = r.nextInt(4) + 1; // generate random color
        switch (chosenColor) {
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
        if (gameIsNotPaused)
            pauseTheGame();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!musicButtonState)
            song.setVolume(0, 0);
        else
            song.setVolume(1, 1);

        /*
           If the user navigates away from our application (clicking home button for example) onPause() gets called which stops the game (without the visuals continueBTN/exitBTN/paused text)
           we want the game to be in paused mode (visually) when the user returns to the app ( onResume() ).

           But because onResume() gets called when the game ("GameActivity.java") first loads , the game starts in paused mode ...
           we don't want that to happen , so if it's the first time onResume has been called (if game just started) , we ignore it (we don't pause)
         */
        if (firstTimeOnResumeCalled || restart == true) {
            firstTimeOnResumeCalled = false;
        } else {
            if (gameIsNotPaused)
                pauseTheGame();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        song.setVolume(0, 0);

    }



    public void generateOtherThreeShapes() // This method sets 3 random WRONG answers at all the positions except the chosenShapePosition
    {
        Arrays.fill(chosenAnswers, 0);
        chosenAnswers[chosenShape] = 1;
        int firstIndexOfChosenShape = chosenShape, confusingShapes = difficulty; // beginner = 1 confusing shape , advanced =2 , pro =3
        while (firstIndexOfChosenShape % 4 != 0) {
            firstIndexOfChosenShape--;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 3; i++) { // Make a list with 0,1,2,3
            list.add(new Integer(i));
        }
        Collections.shuffle(list); // Mix the list to get random order of the numbers in it (Ex. 3,1,2,0)
        for (int i = 0; i <= 3; i++) { // Pull all the unsorted numbers from the above list (list[0],list[1],list[2],list[3]) , these numbers will be used for the next position to draw to
            if (list.get(i) != chosenShapePositionInAnswers) // this if-statement is to avoid choosing the same position as the correct shape
            {

                if (confusingShapes != 0) { // One confusing shape
                    otherThreeAnswers = r.nextInt(4) + firstIndexOfChosenShape;  // 0->15 This generates a random shape and saves the shape at "otherThreeAnswers'
                    while (chosenAnswers[otherThreeAnswers] == 1)
                        otherThreeAnswers = r.nextInt(4) + firstIndexOfChosenShape; // Generate a new random shape until the random shape is unique and isn't a duplicate of an already existing answer)

                    generateAnswerAtPosition(list.get(i), otherThreeAnswers); // finally , we draw the random image (wrong answer) at a random (empty) position
                    confusingShapes--;
                } else { // Generate random wrong answer that hasn't been generated before and isn't confusing shape
                    otherThreeAnswers = r.nextInt(16);
                    while (chosenAnswers[otherThreeAnswers] == 1 || (otherThreeAnswers >= firstIndexOfChosenShape && otherThreeAnswers <= firstIndexOfChosenShape + 3))
                        otherThreeAnswers = r.nextInt(16);


                    generateAnswerAtPosition(list.get(i), otherThreeAnswers); // finally , we draw the random image (wrong answer) at a random (empty) position


                }
            }
        }

    }

    public void generateAnswerAtPosition(int pos, int shapeOutline) // This method sets given image(shapeOutline) at given position(pos)
    {
        chosenAnswers[shapeOutline] = 1; // Mark answer as used
        switch (pos) {
            case 0:
                answerPositions[0].setImageResource(ShapesOutlineArray[shapeOutline]);
                break;
            case 1:
                answerPositions[1].setImageResource(ShapesOutlineArray[shapeOutline]);
                break;
            case 2:
                answerPositions[2].setImageResource(ShapesOutlineArray[shapeOutline]);
                break;
            case 3:
                answerPositions[3].setImageResource(ShapesOutlineArray[shapeOutline]);
        }
    }

    public void pauseTheGame() {

    }




    public void progressBarTutorial()
    {
        welcometv.animate().alpha(0).setDuration(500);
        tutProgBarArrow.animate().alpha(1);
        tutProgBarArrow.playAnimation();
        tutProgBar.animate().alpha(1).setDuration(1000);

    }

    public void centerShapeTutorial()
    {
        tutProgBar.animate().alpha(0).setDuration(500);
        tutProgBarArrow.animate().alpha(0).setDuration(500).withEndAction(new Runnable() {
            @Override
            public void run() {
                tutCenterShape.animate().alpha(1).setDuration(1000);
                ShapeOutline.setAlpha(255);
                ShapeFillerColor.setAlpha(255);
                ShapeOutline.animate().alpha(0);
                ShapeFillerColor.animate().alpha(0);
                ShapeOutline.animate().alpha(1).setDuration(3000);
                ShapeFillerColor.animate().alpha(1).setDuration(3000);

            }
        });

    }

    public void colorsTutorial()
    {
        tutCenterShape.animate().alpha(0).setDuration(500);
        ShapeOutline.animate().alpha(0.1f).setDuration(500);
        ShapeFillerColor.animate().alpha(0.1f).setDuration(500);
        tutProgBar.animate().alpha(0).setDuration(500).withEndAction(new Runnable() {
            @Override
            public void run() {
                colorstut1.animate().alpha(1).setDuration(1000);
            }
        });

    }

    public void colorsTutorial2()
    {
        colorstut1.animate().alpha(0).setDuration(500);
        colorstut2.animate().alpha(1).setDuration(500);
        colorstutarrow.animate().alpha(1).setDuration(500);
        fourColorsImage.setAlpha(255);
        rotatingAnswersLL.animate().alpha(1).setDuration(500);
    }


    public void shapeTutorial()
    {
        colorstut2.animate().alpha(0).setDuration(500);
        colorstutarrow.animate().alpha(0).setDuration(500);

        tutShape.animate().alpha(1).setDuration(700);
        fourShapesLayout.animate().alpha(1).setDuration(700);


    }

    public void thatsall()
    {
        ConfigSharedPreferences.writeIsItFirstTime(getApplicationContext(),false); // Write to shared preferences that user already saw tutorial

        nextBtn.animate().alpha(0).setDuration(1500);
        tutShape.animate().alpha(0).setDuration(500);
        fourShapesLayout.animate().alpha(0.1f).setDuration(500);
        fourColorsImage.animate().alpha(0.1f).setDuration(500);
        tutThatsIt.animate().alpha(1).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                tutThatsIt.animate().alpha(0).setDuration(1200).setStartDelay(1500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        tutThatsIt.setText("Simple right?");
                        tutThatsIt.animate().alpha(1).setDuration(1200).setStartDelay(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                tutThatsIt.animate().alpha(0).setDuration(1200).setStartDelay(500).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        tutThatsIt.setText("See you in-game!");
                                        tutThatsIt.animate().alpha(1).setDuration(1200).setStartDelay(0);
                                        finishtut.setVisibility(View.VISIBLE);
                                        replaytut.setVisibility(View.VISIBLE);

                                        finishtut.animate().alpha(1).setDuration(1000);
                                        replaytut.animate().alpha(1).setDuration(1000);
                                    }
                                });

                            }
                        });
                    }
                });

            }
        });

    }

    private void cooldownNext(){
        cooldown = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nextCooldown=true;
                                nextBtn.animate().alpha(1).setDuration(500);
                                cooldown.interrupt();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        cooldown.start();
    }

















    @Override
    protected void onPause() {
        System.out.println("OnPause()");
        handler.removeCallbacks(runnable);
        song.setVolume(0, 0);
        super.onPause();
    }
}


