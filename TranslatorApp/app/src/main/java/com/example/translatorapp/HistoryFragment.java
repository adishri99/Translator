package com.example.translatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */


public class HistoryFragment extends Fragment {


    private ArrayList<Translation> translationList;
    private RecyclerView translationRecyclerView;
    private SavedTranslationAdapter translationAdapter;
    private RecyclerView.LayoutManager translationLayoutManager;
    private View savedView;
    private TextToSpeech textToSpeech;
    private Saver s;
    private Activity act;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        textToSpeechInit();
        s = new Saver();
        translationList = s.getList(getActivity());
        savedView = inflater.inflate(R.layout.fragment_history, container, false);
        translationRecyclerView = savedView.findViewById(R.id.saved_list);
        translationLayoutManager = new LinearLayoutManager(this.getContext());
        translationAdapter = new SavedTranslationAdapter(translationList, textToSpeech, getActivity());
        translationRecyclerView.setAdapter(translationAdapter);
        translationRecyclerView.setLayoutManager(translationLayoutManager);
        translationAdapter.setListener(new SavedTranslationAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
        return savedView;
    }

    private void removeItem(int position) {
        translationList.remove(position);
        translationAdapter.notifyDataSetChanged();
    }


    public void textToSpeechInit() {
        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getActivity() != null) {
            translationList = s.getList(getActivity());
            translationRecyclerView = savedView.findViewById(R.id.saved_list);
            translationLayoutManager = new LinearLayoutManager(this.getContext());
            translationAdapter = new SavedTranslationAdapter(translationList, textToSpeech, getActivity());
            translationRecyclerView.setAdapter(translationAdapter);
            translationAdapter.setListener(new SavedTranslationAdapter.OnItemClickListener() {
                @Override
                public void onDeleteClick(int position) {
                    removeItem(position);
                }
            });
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


}
