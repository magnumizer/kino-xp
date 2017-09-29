package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DBWrapper.DBConn;

/**
 * Created by blackhatt on 19/09/2017.
 */
public class MovieData {


    private ObservableList<Movie> movieList = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovieList() {
        return movieList;
    }

}
