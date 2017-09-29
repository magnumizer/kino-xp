package sample.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.DBWrapper.BookingWrapper;
import sample.DBWrapper.MovieWrapper;
import sample.DBWrapper.ScheduleWrapper;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    //region FXML
    @FXML
    private TextField name;
    @FXML
    private DatePicker date;
    @FXML
    private ChoiceBox<String> availableMovies;
    @FXML
    private Label currentSeatsValue;

    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, String> nameCol;
    @FXML
    private TableColumn<Booking, String> dateCol;
    @FXML
    private TableColumn<Booking, String> timeCol;
    @FXML
    private TableColumn<Booking, String> movieCol;
    //endregion

    private ObservableList<Movie> allMovies;

    public AnchorPane staffAnchor;

    private String currentTime;
    private String currentMovieTitle;

    private Schedule scheduleRoom1;
    private Schedule scheduleRoom2;

    private ArrayList<Integer> currentSeatsSelected;
    private ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
    private BookingWrapper bookingWrapper = new BookingWrapper();


    private void updateWorkScreen(String path) {

        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource(path));
            staffAnchor.getChildren().setAll(wpAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addBookingBtn(ActionEvent actionEvent) {
        if (!name.getText().equals("")) {
            if (date.getValue() != null) {
                if (!availableMovies.getSelectionModel().getSelectedItem().equals("")) {
                    if (currentSeatsSelected != null && currentSeatsSelected.size() > 0) {
                        Booking newBooking = new Booking(name.getText(), currentSeatsSelected, date.getValue(), currentTime, currentMovieTitle);
                        BookingData.bookingList.add(newBooking);
                        //dirty cheat
                        int currMovieId = 0;
                        String[] scheduleStringId = availableMovies.getSelectionModel().getSelectedItem().split("-");
                        int scheduleID = Integer.parseInt(scheduleStringId[1]);
                        for (int i = 0; i < allMovies.size() ; i++) {
                            if (allMovies.get(i).getTitle().equals(currentMovieTitle)){
                                currMovieId = allMovies.get(i).getId();
                            }
                        }
                        Date movieDate = scheduleWrapper.getScheduleTime(scheduleID);
                        bookingWrapper.saveBooking(newBooking.getName(),
                                newBooking.getSeatNumbers(),
                                currMovieId,
                                movieDate,
                                scheduleID
                        );
                        bookingTable.refresh();
                        currentSeatsSelected.clear();
                        name.setText("");
                        fillTable(scheduleID);
                        clearFields();
                    }
                    else {
                        System.out.println("select a seat pls");
                    }
                }
                else {
                    System.out.println("select a movie pls");
                }
            }
            else {
                System.out.println("put a date pls");
            }
        }
        else {
            System.out.println("put a name pls");
        }
    }

    public void onDateSelect(ActionEvent actionEvent) {
        if (date.getValue() == null)
            return;

        ObservableList<String> movieList = FXCollections.observableArrayList();

        BookingData.bookingList.clear();
        bookingTable.refresh();

        for (int i = 0; i < scheduleRoom1.getMovieDays().size() ; i++) {
            Date currD = scheduleRoom1.getMovieDays().get(i).getCal().getTime();
            LocalDate currentDate = currD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.getValue().isEqual(currentDate)){
                for (MovieTableObject mto : scheduleRoom1.getMovieDays().get(i).getMovieTableObjects()) {
                    String title = mto.getMovieTitle();
                    if (title.equals("--") || title.equals(""))
                        continue;
                    String time = mto.getMovieBeginTime();
                    String formattedString = title + " ( " + time + ") | Schedule ID-" + mto.getId();
                    movieList.add(formattedString);
                }
            }
        }
        for (int i = 0; i < scheduleRoom2.getMovieDays().size() ; i++) {
            Date currD = scheduleRoom2.getMovieDays().get(i).getCal().getTime();
            LocalDate currentDate = currD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.getValue().isEqual(currentDate)){
                for (MovieTableObject mto : scheduleRoom2.getMovieDays().get(i).getMovieTableObjects()) {
                    String title = mto.getMovieTitle();
                    if (title.equals("--") || title.equals(""))
                        continue;
                    String time = mto.getMovieBeginTime();
                    String formattedString = title + " ( " + time + ") | Schedule ID-" + mto.getId();
                    movieList.add(formattedString);
                }
            }
        }

        availableMovies.getItems().setAll(movieList);
    }

    public void chooseSeatsBtn(ActionEvent actionEvent) {
        if (date.getValue() != null && availableMovies.getValue() != null) {
            ArrayList allseatsTaken = new ArrayList();
            for (int i = 0; i < BookingData.bookingList.size() ; i++) {
                Booking booking = BookingData.bookingList.get(i);
                for (int j = 0; j < booking.getSeatNumbers().size() ; j++) {
                    allseatsTaken.add(booking.getSeatNumbers().get(j));
                }
            }

            Seats seats = new Seats(allseatsTaken);
            ArrayList<Integer> selSeats = seats.getSeatsSelected();
            for (int i = 0; i < selSeats.size() ; i++) {
                System.out.println("Seat taken: " + selSeats.get(i));
            }
            currentSeatsSelected = selSeats;
            currentSeatsValue.setText("Current seats: " + selSeats.size());
        }
        else {
            System.out.println("select a date and movie pls ");
        }
    }

    public void goBackBtn(ActionEvent actionEvent) {

        updateWorkScreen("/sample/Views/first.fxml");

    }

    public void updateBookingBtn(ActionEvent actionEvent) {
        if (bookingTable.getSelectionModel().isEmpty())
            return;

        Booking booking = bookingTable.getSelectionModel().getSelectedItem();
        booking.setName(name.getText());
        booking.setDate(date.getValue());
        booking.setTime(currentTime);
        booking.setMovieTitle(currentMovieTitle);

        int currMovieId = 0;
        String[] scheduleStringId = availableMovies.getSelectionModel().getSelectedItem().split("-");
        int scheduleID = Integer.parseInt(scheduleStringId[1]);
        for (int i = 0; i < allMovies.size() ; i++) {
            if (allMovies.get(i).getTitle().equals(currentMovieTitle)){
                currMovieId = allMovies.get(i).getId();
            }
        }

        //Update to DB
        bookingWrapper.updateBooking(booking, currMovieId, scheduleID);

        bookingTable.getItems().setAll(BookingData.bookingList);
        clearFields();
    }

    public void cancelBookingBtn(ActionEvent actionEvent) {
        if (bookingTable.getSelectionModel().isEmpty())
            return;

        Booking booking = bookingTable.getSelectionModel().getSelectedItem();
        BookingData.bookingList.remove(booking);
        bookingTable.getItems().setAll(BookingData.bookingList);
        //Removing from DB
        bookingWrapper.deleteBooking(booking.getId());
        clearFields();
    }

    public void onTableClick(MouseEvent mouseEvent) {
        if (bookingTable.getSelectionModel().isEmpty())
            return;

        Booking booking = bookingTable.getSelectionModel().getSelectedItem();
        name.setText(booking.getName());
        date.setValue(booking.getDate());
        currentSeatsValue.setText("Current seats: " + booking.getSeatNumbers().size());
    }

    void clearFields() {
        name.setText("");
        date.setValue(null);
        availableMovies.getItems().clear();
        currentSeatsValue.setText("Current seats: 0");
    }

    void setupTableColumns() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        movieCol.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
    }

    void addChangeListener()
    {
        availableMovies.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2)
            {
                try {
                    if (availableMovies.getItems().size() >= 1) {

                        String[] strings = availableMovies.getItems().get((Integer) number2).split(" \\( | \\)");
                        currentMovieTitle = strings[0];
                        currentTime = strings[1];

                    }

                    if (!availableMovies.getSelectionModel().isEmpty()){

                        String[] scheduleStringId = availableMovies.getItems().get((Integer) number2).split("-");
                        int scheduleID = Integer.parseInt(scheduleStringId[1]);
                        fillTable(scheduleID);

                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    //gotta catch em all
                }

            }
        });
    }

    private void fillTable(int scheduleID){
        ObservableList<Booking> bookings = bookingWrapper.getBookingsForSchedule(scheduleID);
        System.out.println("Booking count: " + bookings.size());
        BookingData.bookingList.clear();
        BookingData.bookingList.addAll(bookings);
        bookingTable.setItems(BookingData.bookingList);
        bookingTable.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListener();
        setupTableColumns();

        scheduleRoom1 = Schedule.scheduleRoomA;
        scheduleRoom2 = Schedule.scheduleRoomB;

        BookingWrapper bookingWrapper = new BookingWrapper();
        BookingData.bookingList =  bookingWrapper.getAllBookings();
        bookingTable.getItems().setAll(BookingData.bookingList);

        MovieWrapper movieWrapper = new MovieWrapper();
        allMovies = movieWrapper.getAllMovies();
        movieWrapper = null;

    }
}
