package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


/**
 * Created by blackhatt on 19/09/2017.
 */
public class MainController {

    @FXML
    AnchorPane mainPane;
    @FXML
    Button manageButton;
    @FXML
    Button scheduleButton;

    public void scheduleAction(ActionEvent actionEvent) {

        updateWorkScreen("/sample/Views/schedule.fxml");
    }

    public void manageAction(ActionEvent actionEvent) {

        updateWorkScreen("/sample/Views/addMovie.fxml");
    }


    private void updateWorkScreen(String path) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource(path));
            mainPane.getChildren().setAll(wpAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
