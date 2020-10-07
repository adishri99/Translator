package com.example.translatorapp;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedTranslationAdapter extends RecyclerView.Adapter<SavedTranslationAdapter.SavedTranslationViewHolder> {
    private TextToSpeech textToSpeech;
    ArrayList<Translation> translationList;
    Activity act;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public SavedTranslationAdapter(ArrayList<Translation> translationList, TextToSpeech t, Activity act) {
        this.translationList = translationList;//10:23
        this.textToSpeech = t;
        this.act = act;
    }


    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public SavedTranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_translation, parent, false);
        SavedTranslationViewHolder tvh = new SavedTranslationViewHolder(v, translationList, this.textToSpeech, this.act, mListener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedTranslationViewHolder holder, int position) {
        Translation t = translationList.get(position);
        holder.originalText.setText(t.getOriginalText());
        holder.text.setText(t.getText());

    }

    @Override
    public int getItemCount() {
        return translationList.size();
    }


    public static class SavedTranslationViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView originalText;
        ImageView remove;

        final ArrayList<Translation> translations;
        private TextToSpeech textToSpeech;
        ImageView speech;
        Activity act;
        boolean saved;

        public SavedTranslationViewHolder(@NonNull View itemView, final ArrayList<Translation> translations, TextToSpeech t, final Activity act, final OnItemClickListener listener) {
            super(itemView);
            this.translations = translations;
            text = itemView.findViewById(R.id.text);
            speech = itemView.findViewById(R.id.speech_button);
            remove = itemView.findViewById(R.id.remove);
            originalText = itemView.findViewById(R.id.originalText);

            this.textToSpeech = t;
            saved = false;
            this.act = act;

            speech.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Translation t = translations.get(getAdapterPosition());
                    doSpeech(t);
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Saver saver = new Saver();
                    Translation t = translations.get(getAdapterPosition());
                    if (listener != null) {
                        listener.onDeleteClick(getAdapterPosition());
                        ArrayList<Translation> li = saver.getList(act);
                        saver.remove(t, act);
                        li = saver.getList(act);
                    }
                }
            });


        }

        public void doSpeech(Translation t) {

            int speechStatus = this.textToSpeech.speak((String) t.getText(), TextToSpeech.QUEUE_FLUSH, null);

            if (speechStatus == TextToSpeech.ERROR) {
                Log.e("TTS", "Error in converting Text to Speech!");
            }

        }
    }


}
