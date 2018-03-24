

import java.util.ArrayList;

public class Movies
{
    private String id;

    private String title;

    private ArrayList<String> genres;

    private ArrayList<String> stars;

    private String year;

    private String director;
    public Movies(String id, String title) {

        this.id = id;
        this.title = title;

    }
    public Movies(String id, String title, String year, String director, ArrayList<String> genres, ArrayList<String> stars) {

        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.genres = new ArrayList<String>(genres);
        this.stars = new ArrayList<String>(stars);

    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getStars() {
        return stars;
    }


    public String getYear ()
    {
        return year;
    }

    public void setYear (String year)
    {
        this.year = year;
    }

    public String getDirector ()
    {
        return director;
    }

    public void setDirector (String director)
    {
        this.director = director;
    }


}
