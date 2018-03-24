package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BlueActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        Bundle bundle = getIntent().getExtras();
        if(bundle.get("last_activity").equals("red")) {
            Toast.makeText(this, "Login Successfully" + ".", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Backed to Search Page" + ".", Toast.LENGTH_LONG).show();
        }
        String msg = bundle.getString("message");
        if(msg != null && !"".equals(msg)){
            ((TextView)findViewById(R.id.last_page_msg_container)).setText(msg);
        }

    }


    public void goToGreen(View view){

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        EditText querytxt = (EditText)findViewById(R.id.query);

        String query = querytxt.getText().toString();
        if (query.equals("") || query == null){

            ((TextView) findViewById(R.id.nomessage)).setText("Please enter a title!");

        }
        else {
            String url = "http://13.58.198.173:8080/testandroid/androidsearch?title=";
            url += query + "&offset=1";

            System.out.println(url + " url");
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Intent goToIntent = new Intent(BlueActivity.this, GreenActivity.class);

                            goToIntent.putExtra("last_activity", "blue");
                            goToIntent.putExtra("message", response);

                            startActivity(goToIntent);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("security.error", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };


            // Add the request to the RequestQueue.
            queue.add(postRequest);

        }
        return ;

        //String msg = ((EditText)findViewById(R.id.blue_2_green_message)).getText().toString();


    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

}
