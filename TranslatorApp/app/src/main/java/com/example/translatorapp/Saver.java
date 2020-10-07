package com.example.translatorapp;

import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Saver {


    static final String listName = "TRANSLATIONS";

    public void save(Translation t, Activity act) {
        SharedPreferences prefs = act.getPreferences(MODE_PRIVATE);
        ArrayList<Translation> list = getList(act);
        if (!list.contains(t)) {
            list.add(t);
        }
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String newListString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(list);
        prefsEditor.putString(listName, newListString);
        prefsEditor.commit();
    }


    public void remove(Translation t, Activity act) {
        SharedPreferences prefs = act.getPreferences(MODE_PRIVATE);
        ArrayList<Translation> list = getList(act);
        list.remove(t);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String newListString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(list);
        prefsEditor.putString(listName, newListString);
        prefsEditor.commit();
    }

    public ArrayList<Translation> getList(Activity act) {
        SharedPreferences prefs = act.getPreferences(MODE_PRIVATE);
        String listString = prefs.getString(listName, "");
        if (listString != "") {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            ArrayList<LinkedTreeMap<String, String>> A = gson.fromJson(listString, ArrayList.class);
            ArrayList<Translation> list = new ArrayList<>();
            for (LinkedTreeMap<String, String> a : A) {
                list.add(new Translation(a.get("text"), a.get("originalText")));
            }
            return list;
        }
        return new ArrayList<>();
    }
}
