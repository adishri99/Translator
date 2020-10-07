package com.example.translatorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static android.content.Context.MODE_PRIVATE;

public class TranslateFragment extends Fragment {

    private ArrayList<Translation> translationList;
    private RecyclerView translationRecyclerView;
    private RecyclerView.Adapter translationAdapter;
    private RecyclerView.LayoutManager translationLayoutManager;
    private ImageView button;
    private View translateView;
    boolean swap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        translateView = inflater.inflate(R.layout.fragment_translate_activity, container, false);
        super.onCreate(savedInstanceState);
        //button which will take us to image text detection page
        button = translateView.findViewById(R.id.button_image_detect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimagedetectionpage();
            }
        });
        textToSpeechInit();
        translationList = new ArrayList<>();
        translationRecyclerView = translateView.findViewById(R.id.translation_list);
        translationLayoutManager = new LinearLayoutManager(this.getContext());
        translationAdapter = new TranslationAdapter(translationList, textToSpeech, getActivity());
        translationRecyclerView.setAdapter(translationAdapter);
        translationRecyclerView.setLayoutManager(translationLayoutManager);
        final Spinner source = translateView.findViewById(R.id.spinner);
        final Spinner target = translateView.findViewById(R.id.spinner2);
        Button translateButton = translateView.findViewById(R.id.button);
        final Button swapButton = translateView.findViewById(R.id.btn_swap);
        String[] targetLanguagesArray = {"Choose a target language","Afrikaans","Amharic","Arabic","Azerbaijani","Belarusian","Bulgarian","Bengali","Bosnian","Catalan","Cebuano","Corsican","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Basque","Persian", "Finnish","French","Frisian","Irish","Scots Gaelic","Galician","Gujarati","Hausa","Hawaiian","Hindi","Hmong","Croatian","Haitian Creole","Hungarian","Armenian","Indonesian","Igbo","Icelandic","Italian","Hebrew","Japanese","Javanese","Georgian","Kazakh","Khmer","Kannada","Korean","Kurdish","Kyrgyz","Latin","Luxembourgish","Lao","Lithuanian","Latvian","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Marathi","Malay","Maltese","Myanmar","Nepali","Dutch","Norwegian","Nyanja","Punjabi","Polish","Pashto","Portuguese","Romanian","Russian","Sindhi","Sinhala","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tagalog","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Xhosa","Yiddish","Yoruba","Chinese","Chinese Traditional","Zulu"};
        String[] languagesArray = {"Choose a source language","Afrikaans","Amharic","Arabic","Azerbaijani","Belarusian","Bulgarian","Bengali","Bosnian","Catalan","Cebuano","Corsican","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Basque","Persian", "Finnish","French","Frisian","Irish","Scots Gaelic","Galician","Gujarati","Hausa","Hawaiian","Hindi","Hmong","Croatian","Haitian Creole","Hungarian","Armenian","Indonesian","Igbo","Icelandic","Italian","Hebrew","Japanese","Javanese","Georgian","Kazakh","Khmer","Kannada","Korean","Kurdish","Kyrgyz","Latin","Luxembourgish","Lao","Lithuanian","Latvian","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Marathi","Malay","Maltese","Myanmar","Nepali","Dutch","Norwegian","Nyanja","Punjabi","Polish","Pashto","Portuguese","Romanian","Russian","Sindhi","Sinhala","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tagalog","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Xhosa","Yiddish","Yoruba","Chinese","Chinese Traditional","Zulu"};
        final EditText input = translateView.findViewById(R.id.editText);
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
        ArrayAdapter<String> sourceAdapter = new SpecialArrayAdapter<>(TranslateFragment.this.getContext() ,android.R.layout.simple_spinner_item, sourceLanguages);
        ArrayAdapter<String> targetAdapter = new SpecialArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_item, targetLanguages);
        target.setAdapter(targetAdapter);
        source.setAdapter(sourceAdapter);
        translateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendTranslationRequest(input.getText().toString(), source.getSelectedItemPosition(), target.getSelectedItemPosition());
            }
        });
        swap=false;
        swapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                swap_languages(v);
                if (!swap){
                    swap = true;
                    swapButton.setBackground(ContextCompat.getDrawable(translateView.getContext(), R.drawable.swap_button2));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swapButton.setBackground(ContextCompat.getDrawable(translateView.getContext(), R.drawable.swap_button));
                            swap=false;
                        }
                    }, 100);
                }
            }
        });



        /* IF WE HAVE SAVED LANGUAGE PREFERENCE, Then isSet variable is true then LOAD THEM */
        SharedPreferences prefs = getActivity().getPreferences(MODE_PRIVATE);
        if(prefs != null) {
            boolean isSaved = prefs.getBoolean("is_set", false);
            if (isSaved) {
                int source_val = prefs.getInt("source_lang", -1);
                int target_val = prefs.getInt("target_lang", -1);
                source.setSelection(source_val);
                target.setSelection(target_val);
                //Toast.makeText(getContext(), "source: " + source_val + "target:" + target_val , Toast.LENGTH_LONG).show();

            }
        }
        return translateView;
    }
    //this method is used to call image_detection_page
    public void openimagedetectionpage() {
        Intent intent = new Intent(this.getContext(), image_detection_page.class);
        startActivity(intent);
    }
    private void sendTranslationRequest(final String text, final int sourceIndex, final int targetIndex) {
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = "http://10.0.2.2:3000/translate";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        populateList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("q", text);
                params.put("source", getCodeLanguageWithIndex(sourceIndex));
                params.put("target", getCodeLanguageWithIndex(targetIndex));
                return params;
            }
        };
        queue.add(postRequest);
    }

    // Puts Translations into ListView
    private void populateList(String response) {
        try {
            translationList.clear();
            JSONObject reader = new JSONObject(response);
            String text = reader.getString("text");

            JSONArray TranslationArray = reader.getJSONArray("translations");
            for (int i = 0; i < TranslationArray.length(); i++) {
                JSONObject cur = TranslationArray.getJSONObject(i);
                String name = cur.getJSONObject("api").getString("name");
                int likes = cur.getJSONObject("api").getInt("likes");
                int dislikes = cur.getJSONObject("api").getInt("dislikes");
                String translation = cur.getString("translation");

                API api = new API(name,likes,dislikes,this.getContext());
                translationList.add(new Translation(translation, api, text));
            }
            translationAdapter.notifyDataSetChanged();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String getCodeLanguageWithIndex(int index) {
        String[] languagesCodeArray = {"", "af", "am", "ar", "az", "be", "bg", "bn", "bs", "ca", "ceb", "co", "cs", "cy", "da", "de", "el", "en", "eo", "es", "et", "eu", "fa", "fi", "fr", "fy", "ga", "gd", "gl", "gu", "ha", "haw", "hi", "hmn", "hr", "ht", "hu", "hy", "id", "ig", "is", "it", "iw", "ja", "jw", "ka", "kk", "km", "kn", "ko", "ku", "ky", "la", "lb", "lo", "lt", "lv", "mg", "mi", "mk", "ml", "mn", "mr", "ms", "mt", "my", "ne", "nl", "no", "ny", "pa", "pl", "ps", "pt", "ro", "ru", "sd", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr", "st", "su", "sv", "sw", "ta", "te", "tg", "th", "tl", "tr", "uk", "ur", "uz", "vi", "xh", "yi", "yo", "zh", "zh-TW", "zu"};
        return languagesCodeArray[index];
    }
    public void swap_languages(View view){
        final Spinner source = (Spinner) Objects.requireNonNull(getActivity()).findViewById(R.id.spinner);
        final Spinner target = (Spinner) Objects.requireNonNull(getActivity()).findViewById(R.id.spinner2);
        int source_id = source.getSelectedItemPosition();
        int target_id = target.getSelectedItemPosition();
        target.setSelection(source_id);
        source.setSelection(target_id);
    }

    private TextToSpeech textToSpeech;
    private Button btn;
    private EditText editText;

    public void textToSpeechInit() {
        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    }
                    else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    
}
