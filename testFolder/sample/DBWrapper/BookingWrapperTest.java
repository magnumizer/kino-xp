package sample.DBWrapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingWrapperTest {


    BookingWrapper br = new BookingWrapper();


    @Test
    void getAllBookings()
    {

        assertNotEquals(null,br.getAllBookings());

    }

    @Test
    void deleteBooking() {

        assertNotEquals(null,br.deleteBooking(1));

    }

}