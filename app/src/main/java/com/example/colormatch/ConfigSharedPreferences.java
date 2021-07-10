package com.example.colormatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConfigSharedPreferences {
    /*
    בגלל שאי אפשר לשמור arraylist כמו שהוא בSharedPreferences, יש צורך להמיר אותו לסוג מידע שכן אפשר לשמור בSP
    לכן בחרנו להמיר את הטבלה לסטרינג ג'יסון, דבר  שכן אפשר לשמור בSP ושום מידע לא הולך לאיבוד.

    לכן - כשנרצה לשמור מידע נמיר אותו קודם לג'יסון ונכניס את התוצאה לSP
    וכשנרצה לשאוב מידע נשאב את הג'יסון הקיים בSP ונמיר אותו לטיפוס המידע איתו אנו מעוניינים לעבוד (במקרה שלנו , ArrayList)
    וכך בעצם אנו שומרים וקוראים ArrayList מהSharedPreferences
     */

    private static final String LIST_KEY = "list_key";

    public static void writeListInPref(Context context, ArrayList<HighScoreObject> list) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list); // Convert our highscore list (ArrayList) to Json and saving the resulted string in variable 'jsonString'

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context); // Get shared preferences
        SharedPreferences.Editor editor = pref.edit(); // Get editor for shared preferences (since we want to write to shared preferences)
        editor.putString(LIST_KEY, jsonString); // inject our converted arraylist into shared preferences
        editor.apply();
    }

    public static ArrayList<HighScoreObject> readListFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context); // Get shared preferences
        String jsonString = pref.getString(LIST_KEY,""); // Get the highscore list (still in Json format)

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HighScoreObject>>(){}.getType(); // Set the conversion to be from Json to ArrayList (using getType on our list)
        ArrayList<HighScoreObject> list = gson.fromJson(jsonString,type); // Convert the extracted data from shared preferences to the above type (ArrayList) and store it in variable 'list'
        return list;
    }
}

