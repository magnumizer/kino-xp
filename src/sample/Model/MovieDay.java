package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by CIA on 20/09/2017.
 */
public class MovieDay {

//    Date dayOfSchedule;
    private ObservableList<MovieTableObject> movieTableObjects;
    private Calendar cal;
    public MovieDay(Calendar dayOfSchedule) {
        this.movieTableObjects = FXCollections.observableArrayList();
        this.cal = dayOfSchedule;
//        cal.setTime(dayOfSchedule);
//        cal.set(Calendar.MINUTE,0);
//        cal.set(Calendar.SECOND,0);
//        cal.set(Calendar.MILLISECOND,0);


        for (int i = 8; i < 23 ; i++) {
//            dayOfSchedule.setHours(i);
            cal.set(Calendar.HOUR_OF_DAY,i);
            java.util.Date d = cal.getTime();

            movieTableObjects.add(new MovieTableObject(d, ""));
        }
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public ObservableList<MovieTableObject> getMovieTableObjects() {
        return movieTableObjects;
    }

    public void setMovieTableObjects(ObservableList<MovieTableObject> movieTableObjects) {
        this.movieTableObjects = movieTableObjects;
    }
}
