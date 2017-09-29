package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.DBWrapper.MovieWrapper;
import sample.Model.Movie;
import sample.Model.MovieData;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by blackhatt on 19/09/2017.
 */
public class AddMovieController implements Initializable {
    //region FXML
    @FXML
    AnchorPane adminPane;
    @FXML
    TextField movieName;
    @FXML
    TextField movieDuration;
    @FXML
    ChoiceBox<Integer> ageChoice;
    @FXML
    TextArea movieDescription;
    @FXML
    TextArea movieActors;
    @FXML
    TableView<Movie> movieTable;
    @FXML
    TableColumn<Movie, String> nameCol;
    @FXML
    TableColumn<Movie, Integer> ageCol;
    //endregion


    MovieWrapper movieWrapper;
    ObservableList<Movie> movieList;
    Integer[] ageNumbers;

    public void onBackBtnPressed(ActionEvent actionEvent) {
        updateWorkScreen("/sample/Views/main.fxml");
    }

    public void onCreateMovieBtnPressed(ActionEvent actionEvent) {
        movieWrapper.saveMovie(movieName.getText(), movieDescription.getText(), ageChoice.getValue(), movieActors.getText(), Integer.parseInt(movieDuration.getText()));
        loadMoviesFromDB();
    }

    public void onUpdateBtnPressed(ActionEvent actionEvent) {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            selectedMovie.setTitle(movieName.getText());
            selectedMovie.setDuration(Integer.parseInt(movieDuration.getText()));
            selectedMovie.setAgeRestriction(ageChoice.getValue());
            selectedMovie.setDescription(movieDescription.getText());
            selectedMovie.setActors(movieActors.getText());
            movieWrapper.updateMovie(selectedMovie);
            loadMoviesFromDB();
        }
        else {
            //no item selected.. throw exception?
        }
    }

    public void onRemoveBtnPressed(ActionEvent actionEvent) {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieWrapper.deleteMovie(selectedMovie.getId());
            loadMoviesFromDB();
        }
    }

    public void onMovieTableClick(MouseEvent mouseEvent) {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieName.setText(selectedMovie.getTitle());
            movieDuration.setText(selectedMovie.getDuration() + "");
            ageChoice.setValue(selectedMovie.getAgeRestriction());
            movieDescription.setText(selectedMovie.getDescription());
            movieActors.setText(selectedMovie.getActors());
        }
    }

    private void updateWorkScreen(String path) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource(path));
            adminPane.getChildren().setAll(wpAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void loadMoviesFromDB() {
        movieList = movieWrapper.getAllMovies();
        movieTable.getItems().setAll(movieList);
    }

    void setupTableColumns() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("ageRestriction"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieWrapper = new MovieWrapper();
        ageNumbers = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        ageChoice.getItems().setAll(ageNumbers);
        setupTableColumns();
        loadMoviesFromDB();
    }


}
