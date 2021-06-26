package com.example.colormatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String HIGHSCORES = "HIGHSCORES";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_SCORE = "USER_SCORE";
    public static final String COLUMN_ACTIVE_USER = "ACTIVE_USER";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "newDBgal.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "+HIGHSCORES+" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_USER_NAME+", "+COLUMN_USER_SCORE+", "+COLUMN_ACTIVE_USER+")";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(customerModel customerModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, customerModel.getName());
        cv.put(COLUMN_USER_SCORE, customerModel.getScore());
        cv.put(COLUMN_ACTIVE_USER, customerModel.isActive());

        long insert = db.insert(HIGHSCORES, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public List<customerModel> getEveryone() {
        List<customerModel> returnList = new ArrayList<>();

        // Get data from database

        String queryString = "SELECT * FROM " + HIGHSCORES;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst())
        {
            //loop through the cursor (result set)
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                int userScore = cursor.getInt(2);
                boolean userActive = cursor.getInt(3)==1?true:false;

                customerModel newUser =  new customerModel(userID,userName,userScore,userActive);
                returnList.add(newUser);
            }
            while(cursor.moveToNext());
        }
        else
        {
            // failure. do not add anything to the list
        }

        // close both cursor and db when dote
        cursor.close();
        db.close();


        return returnList;
    }

    public boolean deleteOne(customerModel customerModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+HIGHSCORES+" WHERE "+COLUMN_ID+" = "+customerModel.getId();

        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst())
            return true;
        else
            return false;
    }
}
