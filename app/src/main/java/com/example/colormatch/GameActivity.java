package com.example.colormatch;

import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Button nxtBtn;


















    ImageView fourColorsImage, ShapeFillerColor, ShapeOutline;
    ImageView[] answerPositions= new ImageView[4]; //contains the 4 rotating shapes
    TextView tv_points, highestScoreTV,paused, difficultyAlertTV, wellDoneTV,fantasticTV, levelsPlayedVALUETV, timePlayedVALUETV, finalScoreVALUETV;
    ProgressBar progressBar;
    LinearLayout rotatingAnswersLL;
    boolean firstTimeOnResumeCalled, gameIsNotPaused, airPlanePause, restart=false,firstTimePlaying;
    ConstraintLayout fourShapesLayout;
    ImageButton pauseBtn;
    ImageView trapArrows;
    ObjectAnimator animator0,animator1,animator2,animator3;
    AnimatorSet trapRotateAnimationSet = new AnimatorSet();
    LottieAnimationView fireworks1,fireworks2, wellDoneConfetti,airplane;
    RelativeLayout newHighScoreLayout,wellDoneLayout;
    Handler handler; // When and how frequerently our game will loop
    Runnable runnable; // the game loop
    Random r;
    //Colors
    private final static int STATE_BLUE = 1;
    private final static int STATE_GREEN = 2;
    private final static int STATE_RED = 3;
    private final static int STATE_YELLOW = 4;
    int buttonState = STATE_BLUE; // Left side (colors)
    int chosenShape;
    int chosenColor,selectedColor=STATE_BLUE;
    int chosenShapePositionInAnswers;
    int otherThreeAnswers;
    int[] chosenAnswers = new int[16]; // which shapes have been chosen
    int[] rotation = {0,1,2,3};

    //time set to 8 seconds
    int currentTime = 50000;
    int startTime = 50000;
    int currentPoints = 0;
    int levelsPlayedCounter=0;
    int[] tempIndexArray = new int[4];
    int rotationCounter=0;
    int highScore;
    int difficulty;
    int screenWidth;
    int secondsPassed=0;
    Thread t;
    Animation anim;
    ArrayList<HighScoreObject> highScoreList;
    String userName;
    MediaPlayer song;
    MediaPlayer swipeSound;
    MediaPlayer clickSound;
    boolean musicButtonState; //Music mode
    boolean soundButtonState; //Sound mode
    boolean highScoreFlag=true;
    //Arrays of the outline shapes and their colors, sorted by 4 groups
    int[] ShapesFillerColorArray= {R.drawable.star_1_fill_color,R.drawable.star_2_fill_color,R.drawable.star_3_fill_color,R.drawable.star_4_fill_color,R.drawable.circles_1_fill_color,R.drawable.circles_2_fill_color,R.drawable.circles_3_fill_color,R.drawable.circles_4_fill_color,R.drawable.three_shapes_1_fill_color,R.drawable.three_shapes_2_fill_color,R.drawable.three_shapes_3_fill_color,R.drawable.three_shapes_4_fill_color,R.drawable.noodles_1_fill_color,R.drawable.noodles_2_fill_color,R.drawable.noodles_3_fill_color,R.drawable.noodles_4_fill_color};
    int[] ShapesOutlineArray= {R.drawable.star_1_outline,R.drawable.star_2_outline,R.drawable.star_3_outline,R.drawable.star_4_outline,R.drawable.circles_1_outline,R.drawable.circles_2_outline,R.drawable.circles_3_outline,R.drawable.circles_4_outline,R.drawable.three_shapes_1_outline,R.drawable.three_shapes_2_outline,R.drawable.three_shapes_3_outline,R.drawable.three_shapes_4_outline,R.drawable.noodles_1_outline,R.drawable.noodles_2_outline,R.drawable.noodles_3_outline,R.drawable.noodles_4_outline};
    private int currentApiVersion;
    @SuppressLint("NewApi")



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);


        nxtBtn= findViewById(R.id.GALTESTnext);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime=0;
            }
        });









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



        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            restart=extras.getBoolean("restart",false); // makes 3,2,1 count
            difficulty=extras.getInt("difficulty",1); // 1 Beginner , 2 Adv , 3 Pro
            musicButtonState=extras.getBoolean("musicButtonState",true);
            soundButtonState=extras.getBoolean("soundButtonState",true);
        }


         startClock(); // Start counting seconds , stop when paused. this will be used for game-over dialog to show time passed


        firstTimeOnResumeCalled=true;
        gameIsNotPaused =true;
        fourColorsImage =findViewById(R.id.fourColorsImage);
        ShapeFillerColor =findViewById(R.id.main_shape_color);
        ShapeOutline =findViewById(R.id.main_shape_outline);
        tv_points=findViewById(R.id.tv_points);
        progressBar=findViewById(R.id.progressbar);
        highestScoreTV =findViewById(R.id.highest_score_tv);
        paused=findViewById(R.id.pause);
        rotatingAnswersLL=findViewById(R.id.rotating_answers_linear_layout);
        difficultyAlertTV =findViewById(R.id.movingShapeAlert);
        fourShapesLayout=findViewById(R.id.fourShapes_layout);
        fireworks1=findViewById(R.id.fireworks1);
        fireworks2=findViewById(R.id.fireworks2);
        wellDoneConfetti =findViewById(R.id.welldoneConfetiAnimation);
        wellDoneLayout=findViewById(R.id.welldoneAnimationRelativeLayout);
        wellDoneTV =findViewById(R.id.welldoneTV);
        airplane=findViewById(R.id.airplaneAnimation);
        fantasticTV=findViewById(R.id.fantasticWithAirplaneTV);
        newHighScoreLayout=findViewById(R.id.newHighScoreRelativeLayout);
        pauseBtn=findViewById(R.id.pauseBtn);
        trapArrows = findViewById(R.id.trap_arrows_iv);
        //Sounds
        swipeSound = MediaPlayer.create(this,R.raw.press_game);
        clickSound = MediaPlayer.create(this,R.raw.click_sound);
        song = MediaPlayer.create(GameActivity.this, R.raw.during_game_music);
        song.setLooping(true);
        song.start();

        if (musicButtonState) {
            song.setVolume(1,1);
        }
        else
            song.setVolume(0,0);

        if (soundButtonState)
            clickSound.start();
        else
        {
            swipeSound.pause();
            clickSound.pause();
        }

        // These 3 lines of code are used in airplane animation
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;

        answerPositions[0]=findViewById(R.id.shape_TOP);
        answerPositions[1]=findViewById(R.id.shape_RIGHT);
        answerPositions[2]=findViewById(R.id.shape_BOTTOM);
        answerPositions[3]=findViewById(R.id.shape_LEFT);

        // Shared preferences - get data
        highScoreList = ConfigSharedPreferences.readListFromPref(this); // fetch high score data
        firstTimePlaying = ConfigSharedPreferences.readIsItFirstTime(this);
        if (firstTimePlaying) // if it's your first time playing then show instructions
        {
            Intent firstTimeIntent = new Intent(GameActivity.this, Tutorial.class);
            firstTimeIntent.putExtra("musicButtonState",musicButtonState);
            firstTimeIntent.putExtra("soundButtonState",soundButtonState);
            startActivity(firstTimeIntent);
        }


        if ( highScoreList == null || highScoreList.isEmpty()) {
            highestScoreTV.setText("0");
            highScoreList = new ArrayList<>();
        }
        else
            highestScoreTV.setText(highScoreList.get(0).getScore());

        highScore = Integer.parseInt(highestScoreTV.getText().toString()); // save HighScoreObject to an int to avoid calling parseInt/getText/toString multiple times during runtime

        //set the initial progressbar time
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText(R.string.points_calculator + currentPoints);

        r = new Random();


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
                                    0
                                 3     1
                                    2
         */

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundButtonState)clickSound.start();
                if (gameIsNotPaused)
                    pauseTheGame();

            }
        });


        fourColorsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the colors
                if(soundButtonState) swipeSound.start();

                selectedColor=(selectedColor+1)%5; // 1->2->3->4 -->  0(1)->2->3->4 -->  0(1)->2....
                if (selectedColor == 0)
                    selectedColor = 1;

                setButtonImage(setButtonPosition(buttonState));
            }
        });

        fourShapesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the shapes
                if(soundButtonState) swipeSound.start();
                rotationCounter=(rotationCounter+1)%4; // 0->1->2->3 -->  ....
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
                else{ // check if the color and shape is correct
                    if ((chosenColor==selectedColor)&&(4-(chosenShapePositionInAnswers+rotationCounter))%4==0 ){

                        //increase points and show them
                        levelsPlayedCounter++; // For game over dialog
                        currentPoints = currentPoints + 10;

                        // reset the chosenAnswers array
                        Arrays.fill(chosenAnswers, 0); // java method to fill every index in the array with value 0 (best way to reset the array)

                        //make the speed higher after every turn
                        startTime = startTime - 1000;
                        if (startTime <= 17000) {
                            startTime = 17000;
                        }

                        // Adding difficulty to level / Alerting before difficulty level
                        if (currentPoints == 20|| currentPoints == 130)
                            alertUserToCustomDifficulty();

                        if (currentPoints == 30 || currentPoints == 140)
                            moveShape();

                        if (currentPoints == 40 || currentPoints == 150)
                        {
                            playIngameAnimation("welldone");
                            currentPoints+=10;
                        }

                        // Checking if new high score
                        if (highScore!=0 && currentPoints>highScore && highScoreFlag ) {
                            playIngameAnimation("newhighscore");
                            highScoreFlag = false;
                        }

                        if (currentPoints == 70 || currentPoints == 180)
                            playIngameAnimation("trap_rotate");

                        // When to say well done
                        if(currentPoints == 80|| currentPoints == 190) {
                            playIngameAnimation("welldone");
                            currentPoints+=10;
                        }

                        if(currentPoints == 120 || currentPoints == 230)
                            playIngameAnimation("airplane");

                        tv_points.setText(getString(R.string.points_calculator) + currentPoints);

                        progressBar.setMax(startTime);
                        currentTime=startTime;
                        progressBar.setProgress(currentTime);


                        //generate random shape(and color) in the middle of the screen
                        setImageShapeAndColor();

                        //generate the above chosenShape in a random position in the answers
                        //and then generate 3 other random answers in random locations
                        chosenShapePositionInAnswers=r.nextInt(4);
                        generateAnswerAtPosition(chosenShapePositionInAnswers,chosenShape);
                        generateOtherThreeShapes();
                        if (!airPlanePause) // puase the game during the airplane animation
                            handler.postDelayed(runnable,20);
                    }

                    // Game over
                    else {
                        fourColorsImage.setEnabled(false);
                        fourShapesLayout.setEnabled(false);

                        Dialog dialog= new Dialog(GameActivity.this);
                        dialog.setContentView(R.layout.activity_game_over);
                        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.85);
                        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.95);
                        dialog.getWindow().setLayout(width, height);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        levelsPlayedVALUETV =dialog.findViewById(R.id.levelsplayedVALUETV);
                        timePlayedVALUETV =dialog.findViewById(R.id.time_played_VALUETV);
                        finalScoreVALUETV =dialog.findViewById(R.id.finalscoreVALUETV);

                        levelsPlayedVALUETV.setText(levelsPlayedCounter+"");
                        timePlayedVALUETV.setText((secondsPassed/60)+" "+getString(R.string.mins)+" "+(secondsPassed)%60+" "+getString(R.string.secs));
                        finalScoreVALUETV.setText(currentPoints+"");

                        RelativeLayout relativeLayout = dialog.findViewById(R.id.game_over_layout);
                        Button shareBtn = dialog.findViewById(R.id.share_score);
                        Button restartGameOver = dialog.findViewById(R.id.replay);
                        Button mainMenu= dialog.findViewById(R.id.mainmenu);
                        Button submitGameOverBTN=dialog.findViewById(R.id.submit_game_over);
                        EditText nicknameET=dialog.findViewById(R.id.nicknameET);
                        LottieAnimationView newHighScoreFireworks = dialog.findViewById(R.id.newhighscorefireworks);
                        TextView newHighScoreTV = dialog.findViewById(R.id.newhighscoreTV);


                        if (currentPoints > highScore )
                        {
                            newHighScoreTV.setVisibility(View.VISIBLE);
                            newHighScoreFireworks.setVisibility(View.VISIBLE);


                                newHighScoreTV.animate().scaleY(1.3f).rotation(3f).setDuration(1400);
                                newHighScoreTV.animate().scaleX(1.3f).setDuration(1400).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        newHighScoreTV.animate().scaleX(1).rotation(0).setDuration(1400);
                                        newHighScoreTV.animate().scaleY(1).setDuration(1400);
                                    }
                                });


                        }

                        shareBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String sharedText = getString(R.string.with_share);
                                Bitmap bitmap = getBitmapFromView(relativeLayout); // convert the layout to an image

                                try {
                                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator +"Quick Match Record.png");
                                    FileOutputStream fOut = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                                    fOut.flush();
                                    fOut.close();
                                    file.setReadable(true, false);
                                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);

                                    intent.putExtra(Intent.EXTRA_TEXT,sharedText);
                                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent.setType("image/png");

                                    startActivity(Intent.createChooser(intent, "Share image via"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        restartGameOver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                restartTheGame();
                            }
                        });

                        mainMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switchToMainActivity();
                            }
                        });

                        submitGameOverBTN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                    submitGameOverBTN.setText(getString(R.string.submit_btn));
                                    submitGameOverBTN.setTextSize(13);
                                    submitGameOverBTN.setAlpha(0.4f);
                                    userName = nicknameET.getText().toString();
                                    if(userName.isEmpty())
                                        userName=getString(R.string.unknown_user);
                                    updateHighScores();
                                    submitGameOverBTN.setClickable(false);
                            }
                        });

                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);

                    }
                }
            }
        };

        //start the game loop
        if(!restart)
            handler.postDelayed(runnable,100);
        else {
            continueTheGame();
        }
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
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation)
            {
                image.setImageResource(drawable);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
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
                setRotation(fourColorsImage,R.drawable.newcolors); // = default blue top
                buttonState = STATE_BLUE;
                break;
            case STATE_RED:
                setRotation(fourColorsImage,R.drawable.colorsredtop);
                buttonState = STATE_RED;
                break;
            case STATE_YELLOW:
                setRotation(fourColorsImage,R.drawable.colorsyellowtop);
                buttonState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                setRotation(fourColorsImage,R.drawable.colorsgreentop);
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

    private void updateHighScores()
    {
        HighScoreObject newHighScore = new HighScoreObject(userName,Integer.toString(currentPoints)); // Create a new highscore with username and current points
        highScoreList.add(newHighScore); // Add it to the list
        Collections.sort(highScoreList); // Sort the list in descending order so the highest score will be first
        // (this only works because we implemented Comparable in HighScoreObject.java class and override compareTo function

        // Shared Preferences
        ConfigSharedPreferences.writeListInPref(getApplicationContext(), highScoreList); // Write list to shared preferences so it would be saved if we re-open the application
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
        if (!musicButtonState)
            song.setVolume(0,0);
        else
            song.setVolume(1,1);

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

    @Override
    protected void onStop() {
        super.onStop();
        t.interrupt();
        song.setVolume(0,0);

    }

    public void alertUserToCustomDifficulty()
    {
        // this function is used to alert the user 1 level before a difficult level comes (for example , moving shape! level)
        difficultyAlertTV.animate().alpha(0.6f).setDuration(800);
    }
    public void moveShape()
    {
        difficultyAlertTV.animate().alpha(0).setDuration(400);
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
        chosenAnswers[chosenShape]=1;
        int firstIndexOfChosenShape=chosenShape,confusingShapes=difficulty; // beginner = 1 confusing shape , advanced =2 , pro =3
        while (firstIndexOfChosenShape%4!=0) {
            firstIndexOfChosenShape--;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 3; i++) { // Make a list with 0,1,2,3
            list.add(new Integer(i));
        }
        Collections.shuffle(list); // Mix the list to get random order of the numbers in it (Ex. 3,1,2,0)
        for (int i = 0; i <= 3; i++) { // Pull all the unsorted numbers from the above list (list[0],list[1],list[2],list[3]) , these numbers will be used for the next position to draw to
            if (list.get(i)!=chosenShapePositionInAnswers) // this if-statement is to avoid choosing the same position as the correct shape
            {

                if (confusingShapes!=0) { // One confusing shape
                    otherThreeAnswers = r.nextInt(4) + firstIndexOfChosenShape;  // 0->15 This generates a random shape and saves the shape at "otherThreeAnswers'
                    while (chosenAnswers[otherThreeAnswers] == 1)
                        otherThreeAnswers = r.nextInt(4) + firstIndexOfChosenShape; // Generate a new random shape until the random shape is unique and isn't a duplicate of an already existing answer)

                    generateAnswerAtPosition(list.get(i), otherThreeAnswers); // finally , we draw the random image (wrong answer) at a random (empty) position
                    confusingShapes--;
                }
                else
                { // Generate random wrong answer that hasn't been generated before and isn't confusing shape
                    otherThreeAnswers=r.nextInt(16);
                    while (chosenAnswers[otherThreeAnswers] == 1 || (otherThreeAnswers >=firstIndexOfChosenShape && otherThreeAnswers<=firstIndexOfChosenShape+3 ))
                        otherThreeAnswers = r.nextInt(16);


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
                break;
        }
    }

    public void pauseTheGame()
    {
        // This method takes care of pausing the game
/*        if(difficuiltyAlertTV.getAlpha()==0.6f) // If a difficuilty alert (moving shape!) is on the screen
            difficuiltyAlertTV.setAlpha(0.1f); // make it less noticeable*/

        t.interrupt(); // stop counting play time seconds



        gameIsNotPaused=false; // used in many places that needs to know if the game is paused or running
        currentTime=startTime; // reset the time left
        handler.removeCallbacks(runnable); // stop the handler - a way to pause the game
     /*   continueBTN.setVisibility(View.VISIBLE); // show the 'continue' and 'exit' buttons
        exitBTN.setVisibility(View.VISIBLE);*/

        rotatingAnswersLL.setAlpha((float) 0.1); // make the bottom half of the screen (linear layout containing the answers) less noticeable
        fourColorsImage.setClickable(false); // disable the ability to rotate the colors
        fourShapesLayout.setClickable(false); // disable the ability to click on the 4 shape-answers
       // paused.setVisibility(View.VISIBLE); // show "Paused"
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

        ImageButton music_bn=dialog.findViewById(R.id.music_button2);
        if(!musicButtonState)
            music_bn.setImageResource(R.drawable.music_off);


        ImageButton sound_btn=dialog.findViewById(R.id.pink_sound_button2);
        if(!soundButtonState)
            sound_btn.setImageResource(R.drawable.sound_off);


        restart_paused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartTheGame();
            }
        });

        resume_paused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundButtonState)clickSound.start();
                dialog.dismiss();
                continueTheGame();

            }
        });

        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainActivity();
            }
        });

        music_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicButtonState) {
                    if(soundButtonState)clickSound.start();
                    song.setVolume(0,0);
                    musicButtonState = false;
                    music_bn.setImageResource(R.drawable.music_off);
                    Toast.makeText(GameActivity.this, R.string.no_music, Toast.LENGTH_SHORT).show();
                }
                else {
                    song.setVolume(1,1);

                    music_bn.setImageResource(R.drawable.music_on);
                    Toast.makeText(GameActivity.this, R.string.music_on, Toast.LENGTH_SHORT).show();
                    musicButtonState = true;
                }


            }
        });

        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundButtonState) {
                    swipeSound.pause();
                    clickSound.pause();
                    soundButtonState = false;
                    sound_btn.setImageResource(R.drawable.sound_off);
                    Toast.makeText(GameActivity.this, R.string.no_sound, Toast.LENGTH_SHORT).show();
                    return;
                }
                sound_btn.setImageResource(R.drawable.sound_on);
                clickSound.start();
                Toast.makeText(GameActivity.this, R.string.sound_on, Toast.LENGTH_SHORT).show();
                soundButtonState = true;
                if(soundButtonState)clickSound.start();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    public void continueTheGame() {
        // This method takes care of pausing the game
        gameIsNotPaused=false;
        t.interrupt(); // Stop counting time (relevant if we restart the gam)
        paused.setVisibility(View.VISIBLE);
        ShapeFillerColor.setVisibility(View.INVISIBLE);
        ShapeOutline.setVisibility(View.INVISIBLE);
        fourShapesLayout.setVisibility(View.INVISIBLE);




        handler.postDelayed(runnable, 3000); // continue the game after 3 seconds
        // these 4 lines of code is , again , to draw in the middle of the screen a new shape , and then 3 wrong answers and 1 correct answer
        // ** we change the shapes to avoid pause-cheating
        setImageShapeAndColor();
        chosenShapePositionInAnswers = r.nextInt(4) ;
        generateAnswerAtPosition(chosenShapePositionInAnswers, chosenShape);
        generateOtherThreeShapes();
        if (difficultyAlertTV.getAlpha() == 0.1f) // If a difficuilty alert (moving shape!) is on the screen
            difficultyAlertTV.animate().alpha(0.6f).setDuration(1500); // return it to normal alpha


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
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {


                                paused.setVisibility(View.GONE);
                                paused.setText("Paused");

                                fourShapesLayout.setVisibility(View.VISIBLE);
                                fourColorsImage.setClickable(true); // Make the game playable again (click = rotation)
                                fourShapesLayout.setClickable(true);

                                if (currentPoints == 2) // If this wont be here (When game returns) we lose the "difficulty effect" (moving shape!) when we resume the game
                                    moveShape();
                               // if (currentPoints==3)
                                playIngameAnimation("blinking");

                                gameIsNotPaused = true;

                                ShapeFillerColor.setVisibility(View.VISIBLE);
                                ShapeOutline.setVisibility(View.VISIBLE);

                                startClock();

                            }
                        });
                    }
                });

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
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
                newHighScoreLayout.animate().translationX(850).setDuration(600).alpha(1).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        newHighScoreLayout.animate().translationX(0).alpha(0).setStartDelay(1500);
                    }
                });
                break;

            case "welldone":
                wellDoneLayout.setVisibility(View.VISIBLE);
                wellDoneTV.animate().alpha(1).setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        wellDoneTV.animate().alpha(0).setDuration(400).setStartDelay(300);
                    }
                });
                wellDoneConfetti.setRepeatMode(LottieDrawable.RESTART);
                wellDoneConfetti.setRepeatCount(0);
                wellDoneConfetti.playAnimation();
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
                airPlanePause =true; // Disable game loop
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
                        airPlanePause =false; // Enable game loop
                        handler.postDelayed(runnable,20); // Resume
                    }
                });

                break;

            case "trap_rotate":

                trapArrows.animate().alpha(1).setDuration(150).setStartDelay(0).translationX(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        trapArrows.animate().alpha(0).setDuration(200).setStartDelay(1000).start();
                        trapArrows.animate().setDuration(400).translationX(0).setStartDelay(1000);

                    }
                });



                animator0 = ObjectAnimator.ofFloat(answerPositions[0],"rotation",360).setDuration(startTime/7);
                animator1 = ObjectAnimator.ofFloat(answerPositions[1],"rotation",360).setDuration(startTime/7);
                animator2 = ObjectAnimator.ofFloat(answerPositions[2],"rotation",360).setDuration(startTime/7);
                animator3 = ObjectAnimator.ofFloat(answerPositions[3],"rotation",360).setDuration(startTime/7);



                trapRotateAnimationSet.play(animator0).with(animator1).with(animator2).with(animator3);
                trapRotateAnimationSet.start();
                break;

        }
    }

    public void switchToMainActivity ()
    {
        if(soundButtonState)clickSound.start();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("musicButtonState",musicButtonState);
        intent.putExtra("soundButtonState",soundButtonState);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void restartTheGame ()
    {
        if(soundButtonState)clickSound.start();
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putExtra("restart",true);
        intent.putExtra("difficulty",difficulty);
        intent.putExtra("musicButtonState",musicButtonState);
        intent.putExtra("soundButtonState",soundButtonState);
        startActivity(intent);
    }


    private void startClock(){
         t = new Thread() {
            @Override
            public void run() {
                try {
                    secondsPassed++;
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                secondsPassed++;

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }





    // Convert a view (layout) to a picture
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }



    @Override
    protected void onPause() {
        System.out.println("OnPause()");
        handler.removeCallbacks(runnable);
        song.setVolume(0,0);
        t.interrupt();
        super.onPause();
    }


}