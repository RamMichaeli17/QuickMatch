package com.example.colormatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class listview_adapter extends ArrayAdapter<HighScoreObject> {

    private Context mContext;
    int mResource;

    public listview_adapter(Context context, int resource, ArrayList<HighScoreObject> objects)
    {
        super(context,resource,objects);
        mContext=context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();
        String score = getItem(position).getScore();

        HighScoreObject highScore = new HighScoreObject(username,score);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvUserName = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvScore = (TextView) convertView.findViewById(R.id.textView3);
        ImageView image = (ImageView) convertView.findViewById(R.id.scoreImage);

        tvUserName.setText(username);
        tvScore.setText(score);

        if (position == 0)
            image.setImageResource(R.drawable.highscores_numberonebadge);


        return convertView;
    }
}
