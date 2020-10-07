package com.example.translatorapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {


    String[] targetLanguagesArray = {"Choose a target language","Afrikaans","Amharic","Arabic","Azerbaijani","Belarusian","Bulgarian","Bengali","Bosnian","Catalan","Cebuano","Corsican","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Basque","Persian", "Finnish","French","Frisian","Irish","Scots Gaelic","Galician","Gujarati","Hausa","Hawaiian","Hindi","Hmong","Croatian","Haitian Creole","Hungarian","Armenian","Indonesian","Igbo","Icelandic","Italian","Hebrew","Japanese","Javanese","Georgian","Kazakh","Khmer","Kannada","Korean","Kurdish","Kyrgyz","Latin","Luxembourgish","Lao","Lithuanian","Latvian","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Marathi","Malay","Maltese","Myanmar","Nepali","Dutch","Norwegian","Nyanja","Punjabi","Polish","Pashto","Portuguese","Romanian","Russian","Sindhi","Sinhala","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tagalog","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Xhosa","Yiddish","Yoruba","Chinese","Chinese Traditional","Zulu"};
    String[] languagesArray = {"Choose a source language","Afrikaans","Amharic","Arabic","Azerbaijani","Belarusian","Bulgarian","Bengali","Bosnian","Catalan","Cebuano","Corsican","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Basque","Persian", "Finnish","French","Frisian","Irish","Scots Gaelic","Galician","Gujarati","Hausa","Hawaiian","Hindi","Hmong","Croatian","Haitian Creole","Hungarian","Armenian","Indonesian","Igbo","Icelandic","Italian","Hebrew","Japanese","Javanese","Georgian","Kazakh","Khmer","Kannada","Korean","Kurdish","Kyrgyz","Latin","Luxembourgish","Lao","Lithuanian","Latvian","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Marathi","Malay","Maltese","Myanmar","Nepali","Dutch","Norwegian","Nyanja","Punjabi","Polish","Pashto","Portuguese","Romanian","Russian","Sindhi","Sinhala","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tagalog","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Xhosa","Yiddish","Yoruba","Chinese","Chinese Traditional","Zulu"};
    boolean isSet;
    int source_lang;
    int target_lang;
    private View Settingsview;

    Spinner target;
    Spinner source;
    public SettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Settingsview = inflater.inflate(R.layout.fragment_settings, container, false);


        source = Settingsview.findViewById(R.id.source_lang);
        target = Settingsview.findViewById(R.id.target_lang);

        List<String> sourceLanguages = Arrays.asList(languagesArray);
        List<String> targetLanguages = Arrays.asList(targetLanguagesArray);

        class SpecialArrayAdapter<T> extends ArrayAdapter<T> {
            public SpecialArrayAdapter(Context context, int resource, List<T> objects) {
                super(context, resource, objects);
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                convertView = super.getDropDownView(position, convertView, parent);
                convertView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams p = convertView.getLayoutParams();
                p.height = 150; // set the height
                convertView.setLayoutParams(p);
                TextView tv = (TextView) convertView;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return tv;
            }
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
        }
        ArrayAdapter<String> sourceAdapter = new SpecialArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_item, sourceLanguages);
        ArrayAdapter<String> targetAdapter = new SpecialArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_item, targetLanguages);
        target.setAdapter(targetAdapter);
        source.setAdapter(sourceAdapter);

        SharedPreferences prefs = getActivity().getPreferences(MODE_PRIVATE);
        if(prefs != null) {
            boolean isSaved = prefs.getBoolean("is_set", false);
            if (isSaved) {
                int source_val = prefs.getInt("source_lang", -1);
                int target_val = prefs.getInt("target_lang", -1);
                source.setSelection(source_val);
                target.setSelection(target_val);
                //Toast.makeText(getContext(), "source: " + source_val + "target:" + target_val, Toast.LENGTH_LONG).show();

            }
        }

 //       Log.d("source", String.valueOf(source_lang));

        Button saveButton = Settingsview.findViewById(R.id.save);
        saveButton.setOnClickListener(this);


        // Inflate the layout for this fragment
        return Settingsview;
        //return inflater.inflate(R.layout.fragment_settings, container, false);
    }



    public void saveLanguages() {

        SharedPreferences prefs = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        source_lang = source.getSelectedItemPosition();
        target_lang = target.getSelectedItemPosition();



        prefsEditor.putInt("source_lang", source_lang);
        prefsEditor.putInt("target_lang", target_lang);
        prefsEditor.putBoolean("is_set", true);

        prefsEditor.commit();
    }

    public void swap_languages(View view){
        final Spinner source = (Spinner) Objects.requireNonNull(getActivity()).findViewById(R.id.source_lang);
        final Spinner target = (Spinner) Objects.requireNonNull(getActivity()).findViewById(R.id.target_lang);
        int source_id = source.getSelectedItemPosition();
        int target_id = target.getSelectedItemPosition();
        target.setSelection(source_id);
        source.setSelection(target_id);
    }

    @Override
    public void onClick(View view) {
        saveLanguages();
        Toast.makeText(getContext(),"Prefered Language Settings Saved", Toast.LENGTH_LONG).show();
    }

/*
    public boolean getIsSet() {
        SharedPreferences prefs = getActivity().getPreferences(MODE_PRIVATE);
        isSet = prefs.getBoolean("is_set", false);
        return isSet;
    }
    public int getSourceLanguage() {
        SharedPreferences prefs = getActivity().getPreferences(MODE_PRIVATE);
        source_lang = prefs.getInt("source_lang", -1);
        return source_lang;
     }
    public int getTargetLanguage() {
        SharedPreferences prefs = getActivity().getPreferences(MODE_PRIVATE);
        target_lang = prefs.getInt("target_lang", -1);
        return target_lang;
    }
 */
}
