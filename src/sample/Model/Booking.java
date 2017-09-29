package sample.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by blackhatt on 25/09/2017.
 */
public class Booking {

    private String name;
    private ArrayList<Integer> seatNumbers = null;
    private Calendar cal;
    private LocalDate date;
    private String time;
    private String movieTitle;
    private int id;

    public Booking(String name, ArrayList<Integer> seatNumbers, LocalDate date, String time, String movieTitle) {
        this.name = name;
        this.seatNumbers = seatNumbers;
        this.date = date;
        this.time = time;
        this.movieTitle = movieTitle;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(ArrayList<Integer> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}