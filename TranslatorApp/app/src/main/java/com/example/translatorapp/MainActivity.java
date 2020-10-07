///*
//*REDUNDANT FILE ~ PREETAM
//*/
//
//package com.example.translatorapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.speech.tts.TextToSpeech;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.*;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//public class MainActivity extends AppCompatActivity {
//
//    //private ArrayList<String> translations_list;
//    private ArrayList<Translation> translationList;
//    private RecyclerView translationRecyclerView;
//    private RecyclerView.Adapter translationAdapter;
//    private RecyclerView.LayoutManager translationLayoutManager;
//    private ImageView button;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //button which will take us to image text detection page
//        button = findViewById(R.id.button_image_detect);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openimagedetectionpage();
//            }
//        });
//
//
//        // Setup ListView & Adapter
//        /*ListView listView = findViewById(R.id.translation_list);
//        translations_list = new ArrayList<>();
//
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, translations_list);
//        listView.setAdapter(adapter);*/
//
//        textToSpeechInit();
//
//        translationList = new ArrayList<>();
//        translationRecyclerView = findViewById(R.id.translation_list);
//        translationLayoutManager = new LinearLayoutManager(this);
//
//        translationAdapter = new TranslationAdapter(translationList,textToSpeech);
//        translationRecyclerView.setAdapter(translationAdapter);
//        translationRecyclerView.setLayoutManager(translationLayoutManager);
//
//
//
//
//
//        final Spinner source = findViewById(R.id.spinner);
//        final Spinner target = findViewById(R.id.spinner2);
//
//
//
//        Button translateButton = findViewById(R.id.button);
//
//
//
//        String[] targetLanguagesArray = {"Choose a target language","Afrikaans","Amharic","Arabic","Azerbaijani","Belarusian","Bulgarian","Bengali","Bosnian","Catalan","Cebuano","Corsican","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Basque","Persian", "Finnish","French","Frisian","Irish","Scots Gaelic","Galician","Gujarati","Hausa","Hawaiian","Hindi","Hmong","Croatian","Haitian Creole","Hungarian","Armenian","Indonesian","Igbo","Icelandic","Italian","Hebrew","Japanese","Javanese","Georgian","Kazakh","Khmer","Kannada","Korean","Kurdish","Kyrgyz","Latin","Luxembourgish","Lao","Lithuanian","Latvian","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Marathi","Malay","Maltese","Myanmar","Nepali","Dutch","Norwegian","Nyanja","Punjabi","Polish","Pashto","Portuguese","Romanian","Russian","Sindhi","Sinhala","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tagalog","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Xhosa","Yiddish","Yoruba","Chinese","Chinese Traditional","Zulu"};
//
//        String[] languagesArray = {"Choose a source language","Afrikaans","Amharic","Arabic","Azerbaijani","Belarusian","Bulgarian","Bengali","Bosnian","Catalan","Cebuano","Corsican","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Basque","Persian", "Finnish","French","Frisian","Irish","Scots Gaelic","Galician","Gujarati","Hausa","Hawaiian","Hindi","Hmong","Croatian","Haitian Creole","Hungarian","Armenian","Indonesian","Igbo","Icelandic","Italian","Hebrew","Japanese","Javanese","Georgian","Kazakh","Khmer","Kannada","Korean","Kurdish","Kyrgyz","Latin","Luxembourgish","Lao","Lithuanian","Latvian","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Marathi","Malay","Maltese","Myanmar","Nepali","Dutch","Norwegian","Nyanja","Punjabi","Polish","Pashto","Portuguese","Romanian","Russian","Sindhi","Sinhala","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tagalog","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Xhosa","Yiddish","Yoruba","Chinese","Chinese Traditional","Zulu"};
//        final EditText input = findViewById(R.id.editText);
//        List<String> sourceLanguages = Arrays.asList(languagesArray);
//        List<String> targetLanguages = Arrays.asList(targetLanguagesArray);
//
//
//        class SpecialArrayAdapter<T> extends ArrayAdapter<T> {
//
//            public SpecialArrayAdapter(Context context, int resource, List<T> objects) {
//                super(context, resource, objects);
//            }
//
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                convertView = super.getDropDownView(position, convertView,
//                        parent);
//
//                convertView.setVisibility(View.VISIBLE);
//                ViewGroup.LayoutParams p = convertView.getLayoutParams();
//                p.height = 150; // set the height
//                convertView.setLayoutParams(p);
//                TextView tv = (TextView) convertView;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                } else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return tv;
//            }
//
//            @Override
//            public boolean isEnabled(int position){
//                return position != 0;
//            }
//
//
//        }
//        ArrayAdapter<String> sourceAdapter = new SpecialArrayAdapter<>(this,android.R.layout.simple_spinner_item, sourceLanguages);
//        ArrayAdapter<String> targetAdapter = new SpecialArrayAdapter<>(this,android.R.layout.simple_spinner_item, targetLanguages);
//
//        target.setAdapter(targetAdapter);
//        source.setAdapter(sourceAdapter);
//        translateButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                sendTranslationRequest(input.getText().toString(), source.getSelectedItemPosition(), target.getSelectedItemPosition());
//                //Toast.makeText(getApplicationContext(), input.getText().toString()+" from "+source.getSelectedItem().toString()+" to "+target.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//
//
//    }
//
//
//    //this method is used to call image_detection_page
//    public void openimagedetectionpage() {
//        Intent intent = new Intent(this, image_detection_page.class);
//        startActivity(intent);
//    }
//
//
//
//    private void sendTranslationRequest(final String text, final int sourceIndex, final int targetIndex) {
//        //      q = StringEscapeUtils.escapeJava(q);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        //String response;
//
//        String url = "http://10.0.2.2:3000/translate";
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                        populateList(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("Error.Response", error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("q", text);
//                params.put("source", getCodeLanguageWithIndex(sourceIndex));
//                params.put("target", getCodeLanguageWithIndex(targetIndex));
//                return params;
//            }
//        };
//        queue.add(postRequest);
//    }
//
//
//    /*
//{
//        "source": "en",
//        "target": "es",
//        "text": "hi how are you",
//        "translations": [
//            {
//                "Google": "Je ne sais pas"
//            },
//            {
//               "Microsoft": "Je ne sais pas"
//            },
//			{
//                "translatedText": "Je ne sais pas"
//            }
//        ]
//}
//*/
//
//    // Puts Translations into ListView
//    private void populateList(String response) {
//        try {
//            translationList.clear();
//
//            JSONObject reader = new JSONObject(response);
//
//
//            JSONArray TranslationArray = reader.getJSONArray("translations");
//
//
//            for (int i = 0; i < TranslationArray.length(); i++) {
//                JSONObject cur = TranslationArray.getJSONObject(i);
//                String name = cur.getJSONObject("api").getString("name");
//                int likes = cur.getJSONObject("api").getInt("likes");
//                int dislikes = cur.getJSONObject("api").getInt("dislikes");
//
//                String translation = cur.getString("translation");
//
//
//                API api = new API(name,likes,dislikes,this);
//
//                translationList.add(new Translation(translation,api));
//            }
//            translationAdapter.notifyDataSetChanged();
//
//            //Toast.makeText(this, "GOT HERE", Toast.LENGTH_LONG).show();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getCodeLanguageWithIndex(int index) {
//        String[] languagesCodeArray = {"", "af", "am", "ar", "az", "be", "bg", "bn", "bs", "ca", "ceb", "co", "cs", "cy", "da", "de", "el", "en", "eo", "es", "et", "eu", "fa", "fi", "fr", "fy", "ga", "gd", "gl", "gu", "ha", "haw", "hi", "hmn", "hr", "ht", "hu", "hy", "id", "ig", "is", "it", "iw", "ja", "jw", "ka", "kk", "km", "kn", "ko", "ku", "ky", "la", "lb", "lo", "lt", "lv", "mg", "mi", "mk", "ml", "mn", "mr", "ms", "mt", "my", "ne", "nl", "no", "ny", "pa", "pl", "ps", "pt", "ro", "ru", "sd", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr", "st", "su", "sv", "sw", "ta", "te", "tg", "th", "tl", "tr", "uk", "ur", "uz", "vi", "xh", "yi", "yo", "zh", "zh-TW", "zu"};
//        return languagesCodeArray[index];
//    }
//
//    public void swap_languages(View view) {
//        final Spinner source = (Spinner) findViewById(R.id.spinner);
//        final Spinner target = (Spinner) findViewById(R.id.spinner2);
//
//        int source_id = source.getSelectedItemPosition();
//        int target_id = target.getSelectedItemPosition();
//
//        target.setSelection(source_id);
//        source.setSelection(target_id);
//
//    }
//
//    private TextToSpeech textToSpeech;
//    private Button btn;
//    private EditText editText;
//
//    public void textToSpeechInit() {
//        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    int ttsLang = textToSpeech.setLanguage(Locale.US);
//
//                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
//                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "The Language is not supported!");
//                    } else {
//                        Log.i("TTS", "Language Supported.");
//                    }
//                    Log.i("TTS", "Initialization success.");
//                } else {
//                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (textToSpeech != null) {
//            textToSpeech.stop();
//            textToSpeech.shutdown();
//        }
//    }
//
//
//}