package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.Seats;
import sample.DBWrapper.ScheduleWrapper;
import sample.Model.MovieDay;
import sample.Model.Schedule;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/first.fxml"));
        primaryStage.setTitle("Hello Bogdan");
        primaryStage.setScene(new Scene(root,700,500));
        primaryStage.show();

        //load from db
        ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
        Schedule.scheduleRoomA = scheduleWrapper.getSchedule(1);
        Schedule.scheduleRoomB = scheduleWrapper.getSchedule(2);
    }


    public static void main(String[] args) {

        launch(args);
//        MovieWrapper mw = new MovieWrapper();
//        System.out.println(mw.getAllMovies().toString());



    }


}
