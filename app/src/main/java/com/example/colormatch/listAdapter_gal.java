package com.example.colormatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/14/2017.
 */

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

        tvUserName.setText(username);
        tvScore.setText(score);

        return convertView;
    }
}
