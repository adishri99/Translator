package com.example.translatorapp;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Objects;

public class Translation implements Serializable {

    @Expose
    String text;

    @Expose
    String originalText;

    API api;

    public Translation(String text, API api, String originalText) {
        this.text = text;
        this.originalText = originalText;
        this.api = api;
    }


    public Translation(String text, String originalText) {
        this.text = text;
        this.originalText = originalText;
    }


    public void setText(String text) {
        this.text = text;
    }

    public API getApi() {
        return api;
    }


    public String getText() {
        return text;
    }


    public String getOriginalText() {
        return originalText;
    }

    public void addLike(){
        api.addLike();
    }


    public void addDislike(){
        api.addDislike();
    }

    public void removeLike(){
        api.removeLike();
    }


    @Override
    public boolean equals(Object o) {
        Translation that = (Translation) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "Translation{" +
                "text='" + text + "\', " +
                "original text='" + originalText + '\'' +
                '}';
    }

    public void removeDislike(){
        api.removeDislike();
    }

}
