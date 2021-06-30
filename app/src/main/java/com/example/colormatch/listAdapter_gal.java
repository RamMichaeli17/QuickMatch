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


public class listAdapter_gal extends ArrayAdapter<Person> {

    private Context mContext;
    int mResource;

    public listAdapter_gal(Context context, int resource, ArrayList<Person> objects)
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

        Person person = new Person(username,score);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvUserName = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvScore = (TextView) convertView.findViewById(R.id.textView3);
        ImageView image = (ImageView) convertView.findViewById(R.id.scoreImage);

        tvUserName.setText(username);
        tvScore.setText(score);

        if (position == 0)
            image.setImageAlpha(255);
        else
            image.setImageAlpha(0);




        return convertView;
    }
}
