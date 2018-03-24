package edu.uci.ics.fabflixmobile;

import java.util.ArrayList;



public class Movielist {

    private ArrayList<Movies> listOfMovies;
    private int numberOfMovies;
    private String titleQuery;
    private int currentPageNumber;
    public Movielist(ArrayList<Movies> listOfMovies, int numberOfMovies, String titleQuery, int currentPageNumber){

        this.listOfMovies = new ArrayList<Movies>(listOfMovies);
        this.numberOfMovies = numberOfMovies;
        this.titleQuery = titleQuery;
        this.currentPageNumber = currentPageNumber;

    }

    public void setCurrentPageNumber(int currentPageNumber) { this.currentPageNumber = currentPageNumber; }

    public ArrayList<Movies> getListOfMovies() { return listOfMovies; }
    public int getNumberOfMovies() { return numberOfMovies; }
    public String getTitleQuery() { return titleQuery; }
    public int getCurrentPageNumber() { return currentPageNumber; }
}
