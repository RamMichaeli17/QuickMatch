/*package com.example.colormatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.zip.Inflater;

public class AboutUsPopup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_popup);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));

    }
}*/

/*
public class AboutUsPopup extends AppCompatActivity {
    ImageButton infoBtn;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_popup);

        infoBtn=(ImageButton)findViewById(R.id.about_us_button);
        dialog= new Dialog(this);

        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              dialog.setContentView(R.layout.about_us_popup);
              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
              WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
              lp.dimAmount = 0.7f;

                final TextView infoTV = dialog.findViewById(R.id.info_aboutUS);
                final ImageView logoIV=dialog.findViewById(R.id.logo_in_info);

                dialog.show();
            }
        });
    }
}*/
