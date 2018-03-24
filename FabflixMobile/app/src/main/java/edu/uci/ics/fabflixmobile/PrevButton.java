package edu.uci.ics.fabflixmobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PrevButton implements View.OnClickListener {

    Movielist movie;

    public PrevButton(Movielist movie){

        this.movie = new Movielist(movie.getListOfMovies(), movie.getNumberOfMovies(), movie.getTitleQuery(), movie.getCurrentPageNumber());

    }

    @Override
    public void onClick(View v){

        connectToFabflix(v);

    }

    public void connectToFabflix(final View view){

        final Map<String, String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        String url = "http://13.58.198.173:8080/testandroid/androidsearch?offset=" + (movie.getCurrentPageNumber() + -1)  + "&title=" + movie.getTitleQuery();

        System.out.println(url+" url");
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Intent goToIntent = new Intent(view.getContext(), GreenActivity.class);
                        goToIntent.putExtra("last_activity", "blue");
                        goToIntent.putExtra("message", response);

                        view.getContext().startActivity(goToIntent);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

        };

        queue.add(postRequest);


        return ;
    }

};