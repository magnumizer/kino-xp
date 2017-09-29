package sample.Model;

import java.util.ArrayList;

public class Movie {
    private int id;
    private int ageRestriction;
    private String title;
    private String description;
    private int duration;
    private String actors;

    private ArrayList<Movie> movies = new ArrayList<>();

    public Movie(String title, String description, int ageRestriction, String actors, int duration) {
        this.ageRestriction = ageRestriction;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.actors = actors;

    }
    //GayParade on your head

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getAgeRestriction() {

        return ageRestriction;

    }

    public void setAgeRestriction(int ageRestriction) {

        this.ageRestriction = ageRestriction;

    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public ArrayList<Movie> getMovies() {

        return movies;

    }

    public void setMovies(ArrayList<Movie> movies) {

        this.movies = movies;

    }

    public String getActors() {

        return actors;

    }

    public void setActors(String actors) {

        this.actors = actors;

    }

    /**
     * Method that find a movie, and return it if exists
     * @param name: name of the movie
     * @return movie if the movie it is found, null if the movie is not found
     */

    public Movie findMovie(String name){

        Movie find = null ;

        for (Movie movie :movies) {

            if(movie.getTitle().equals(name)){

                find = movie;

            }

        }

        return find;

    }

    @Override
    public String toString()
    {
        return title;
    }
}