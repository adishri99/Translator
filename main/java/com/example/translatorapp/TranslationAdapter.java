package com.example.translatorapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {
    ArrayList<Translation> translationList;

    public TranslationAdapter(ArrayList<Translation> translationList) {
        this.translationList = translationList;
    }

    @Override
    public TranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation, parent, false);
        TranslationViewHolder tvh = new TranslationViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationViewHolder holder, int position) {
        Translation t = translationList.get(position);
        holder.text.setText(t.getText());
    }

    @Override
    public int getItemCount() {
        return translationList.size();
    }

    public static class TranslationViewHolder extends RecyclerView.ViewHolder {
        TextView text;


        public TranslationViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
