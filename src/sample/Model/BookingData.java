package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by blackhatt on 25/09/2017.
 */
public class BookingData {

    //fuck you thats why
    public static ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    public ObservableList<Booking> getBookingList() {
        return bookingList;
    }
}
