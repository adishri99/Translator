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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {
    private TextToSpeech textToSpeech;
    ArrayList<Translation> translationList;
    Activity act;

    public TranslationAdapter(ArrayList<Translation> translationList, TextToSpeech t, Activity act) {
        this.translationList = translationList;//10:23
        this.textToSpeech = t;
        this.act = act;
    }


    @Override
    public TranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation, parent, false);
        TranslationViewHolder tvh = new TranslationViewHolder(v, translationList, this.textToSpeech, this.act);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationViewHolder holder, int position) {
        Translation t = translationList.get(position);
        //holder.text.setText(t.getApi().getName() + " : " + t.getText());
        holder.text.setText( t.getText());

        if (t.getApi() != null) {
            String api = t.getApi().getName().toString();

            ImageView logo = (ImageView) holder.itemView.findViewById(R.id.api_logo);

            if (api.contains("microsoft"))
                logo.setImageResource(R.drawable.small_microsoft_logo);
            if (api.contains("google"))
                logo.setImageResource(R.drawable.google_logo);
            if (api.contains("myMemory"))
                logo.setImageResource(R.drawable.mymemory);
            if (api.contains("yandex"))
                logo.setImageResource(R.drawable.yandex_logo);
        }
    }

    @Override
    public int getItemCount() {
        return translationList.size();
    }


    public static class TranslationViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView likeButton;
        boolean isLikeSent;
        ImageView dislikeButton;
        boolean isDislikeSent;
        final ArrayList<Translation> translations;
        private TextToSpeech textToSpeech;
        ImageView speech;
        ImageView save;
        Activity act;
        boolean saved;

        ImageView logo;

        public TranslationViewHolder(@NonNull View itemView, final ArrayList<Translation> translations, TextToSpeech t, final Activity act) {
            super(itemView);
            this.translations = translations;
            isLikeSent = false;
            isDislikeSent = false;
            text = itemView.findViewById(R.id.text);
            speech = itemView.findViewById(R.id.speech_button);
            save = itemView.findViewById(R.id.save_button);
            likeButton = itemView.findViewById(R.id.like_button);
            dislikeButton = itemView.findViewById(R.id.dislike_button);
            this.textToSpeech = t;
            saved = false;
            this.act = act;

            speech.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Translation t = translations.get(getAdapterPosition());
                    doSpeech(t);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Saver saver = new Saver();
                    Translation t = translations.get(getAdapterPosition());
                    ArrayList<Translation> li = saver.getList(act);
                    System.out.println(li);
                    if (!saved) {
                        saver.save(t, act);
                        li = saver.getList(act);
                        System.out.println(li);
                        saved = true;
                    } else {
                        saver.remove(t, act);
                        li = saver.getList(act);
                        System.out.println(li);
                        saved = false;
                    }
                }
            });


            likeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Translation t = translations.get(getAdapterPosition());
                    if (!isLikeSent) {
                        t.addLike();
                        if (isDislikeSent) {
                            t.removeDislike();
                            isDislikeSent = false;
                            dislikeButton.setImageDrawable(ContextCompat.getDrawable(t.getApi().context, R.drawable.dislike_unclicked));
                        }
                        isLikeSent = true;
                        likeButton.setImageDrawable(ContextCompat.getDrawable(t.getApi().context, R.drawable.like));
                    } else {
                        t.removeLike();
                        isLikeSent = false;
                        likeButton.setImageDrawable(ContextCompat.getDrawable(t.getApi().context, R.drawable.like_unclicked));
                    }
                }
            });


            dislikeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Translation t = translations.get(getAdapterPosition());

                    if (!isDislikeSent) {
                        t.addDislike();
                        if (isLikeSent) {
                            t.removeLike();
                            isLikeSent = false;

                            likeButton.setImageDrawable(ContextCompat.getDrawable(t.getApi().context, R.drawable.like_unclicked));
                        }
                        isDislikeSent = true;
                        dislikeButton.setImageDrawable(ContextCompat.getDrawable(t.getApi().context, R.drawable.dislike));
                    } else {
                        t.removeDislike();
                        isDislikeSent = false;
                        dislikeButton.setImageDrawable(ContextCompat.getDrawable(t.getApi().context, R.drawable.dislike_unclicked));
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
