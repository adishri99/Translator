package com.example.translatorapp;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class API {
    String name;
    int likes;
    int dislikes;
    Context context;

    public API(String name, int likes, int dislikes,Context cont) {
        this.name = name;
        this.likes = likes;
        this.dislikes = dislikes;
        this.context = cont;
    }

    public String getName() {
        return name;
    }


    public void addLike() {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://10.0.2.2:3000/rating/like";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };
        queue.add(postRequest);
    }


    public void removeLike() {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        //String response;

        String url = "http://10.0.2.2:3000/rating/like";
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };
        queue.add(postRequest);
    }




    public void addDislike() {
        //      q = StringEscapeUtils.escapeJava(q);

        RequestQueue queue = Volley.newRequestQueue(this.context);
        //String response;

        String url = "http://10.0.2.2:3000/rating/dislike";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void removeDislike() {
        //      q = StringEscapeUtils.escapeJava(q);

        RequestQueue queue = Volley.newRequestQueue(this.context);
        //String response;

        String url = "http://10.0.2.2:3000/rating/dislike";
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };
        queue.add(postRequest);
    }

}
