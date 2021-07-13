package com.example.colormatch;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class FlyingBalloon  extends View {

    private Bitmap balloon;
    int direction_x_balloon, direction_y_balloon;
    int xAxis_balloon, yAxis_balloon;

    public FlyingBalloon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        balloon = BitmapFactory.decodeResource(getResources(),R.drawable.balloonforhighscores);
        direction_x_balloon =0;
        direction_y_balloon =3;
        xAxis_balloon =630;
        yAxis_balloon =630;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        if(yAxis_balloon >=canvas.getHeight()-670)
        {
            direction_y_balloon =-3;
        }
        if(yAxis_balloon <=100)
        {
            direction_y_balloon =3;
        }


        yAxis_balloon += direction_y_balloon; // Update balloon position according to the speed (direction)
        canvas.drawBitmap(balloon, xAxis_balloon, yAxis_balloon,paint);
        invalidate();

    }
}