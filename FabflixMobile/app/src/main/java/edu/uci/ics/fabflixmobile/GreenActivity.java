package edu.uci.ics.fabflixmobile;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.*;
import android.support.v7.app.ActionBar.LayoutParams;
import java.util.ArrayList;
import android.widget.Button;


public class GreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);


        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "Search result!", Toast.LENGTH_LONG).show();

        String msg = bundle.getString("message");
        Movielist mymovies = new Gson().fromJson(msg,Movielist.class);
        TextView tq = (TextView)findViewById(R.id.resulthere);
        tq.setText("Result for :'"+mymovies.getTitleQuery()+"'");
        tq.setTextSize(15);
        tq.setTypeface(null, Typeface.BOLD);
        TextView tq1 = (TextView)findViewById(R.id.resultnum);
        tq1.setText("Number of results :"+mymovies.getNumberOfMovies());
        tq1.setTextSize(15);
        tq1.setTypeface(null, Typeface.BOLD);
        ArrayList<TextView> textList = new ArrayList<TextView>();
        TextView temp = (TextView) findViewById(R.id.resultmovies);
        if (mymovies.getListOfMovies().size() != 0) {

            String all = "--------------------------------\n";
            int num = mymovies.getNumberOfMovies();
            if (mymovies.getNumberOfMovies() >= 5){
                num = 5;
            }

            for (int i = 0; i < num; i++) {
                if (i >= mymovies.getListOfMovies().size()){
                    break;
                }
                String ID = "ID: ";
                ID += mymovies.getListOfMovies().get(i).getId();
                ID += "\n";
                String title = "Title: ";
                title += mymovies.getListOfMovies().get(i).getTitle();
                title += "\n";
                String Director = "Director: ";
                Director += mymovies.getListOfMovies().get(i).getDirector();
                Director += "\n";
                String year = "Year: ";
                year += mymovies.getListOfMovies().get(i).getYear();
                year += "\n";
                String stars = "Stars: ";
                for (String j : mymovies.getListOfMovies().get(i).getStars()) {
                    stars += j + " ";
                }
                stars += "\n";
                String genres = "Genres: ";
                for (String j : mymovies.getListOfMovies().get(i).getGenres()) {
                    genres += j + " ";
                }
                genres += "\n";

                all += ID + title + Director + year + stars + genres + "--------------------------------\n";

            }
            temp.setText(all);
            temp.setTextSize(20);

            Button nextButton = (Button) findViewById(R.id.nextButton);
            if (mymovies.getCurrentPageNumber() * 5 >= mymovies.getNumberOfMovies())
                nextButton.setVisibility(View.INVISIBLE);
            else
                nextButton.setOnClickListener(new Nextbutton(mymovies));

            Button previousButton = (Button) findViewById(R.id.previousButton);
            if (mymovies.getCurrentPageNumber() == 1)
                previousButton.setVisibility(View.INVISIBLE);
            else
                previousButton.setOnClickListener(new PrevButton(mymovies));



        }
        else{
            temp.setText("No result found please go back!");
            temp.setTextSize(20);


        }

        System.out.println(msg+" ther");

    }



    public void goToBlue(View view){


        Intent goToIntent = new Intent(this, BlueActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", "Welcome back!");

        startActivity(goToIntent);
    }

}
