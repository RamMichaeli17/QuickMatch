package com.example.colormatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT=2500;  //Splash duration


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Fit the splash screen to any device
        setContentView(R.layout.splash_activity);

        Animation fadeOut = new AlphaAnimation(0,1); // the direction of the fade out animation
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(2400);  // the duration of the animation
        ImageView imageView= findViewById(R.id.imageView); // the image source of the animation

        imageView.setAnimation(fadeOut);

        // move to the first (main) activity
        new Handler().postDelayed((Runnable) () -> {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        },SPLASH_SCREEN_TIMEOUT);
    }
}
