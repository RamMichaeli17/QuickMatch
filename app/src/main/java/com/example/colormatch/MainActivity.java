package com.example.colormatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton about_us_bn, music_bn;
    MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    protected void onPause() {
        super.onPause();
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