package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class FirstController {

    public AnchorPane firstPane;
    @FXML
    Button adminButton;
    @FXML
    Button customerButton;
   


    private void updateWorkScreen(String path) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource(path));
            firstPane.getChildren().setAll(wpAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void staffWindow(ActionEvent actionEvent) {

        updateWorkScreen("/sample/Views/booking.fxml");

    }

    public void adminWindow(ActionEvent actionEvent) {
        updateWorkScreen("/sample/Views/main.fxml");
    }

    public void consumableWindow(ActionEvent actionEvent) {
        updateWorkScreen("/sample/Views/consumable.fxml");
    }
}
