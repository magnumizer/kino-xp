package sample.DBWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Booking;
import sample.Model.Movie;

import java.awt.print.Book;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by blackhatt on 25/09/2017.
 */
public class BookingWrapper {

    private static final String TABLE = "bookings";
    Connection conn = null;
    ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
    MovieWrapper movieWrapper = new MovieWrapper();
    public BookingWrapper(){

    }

    public ObservableList<Booking> getBookingsForSchedule(int scheduleId){
        System.out.println("Getting bookings for current selected movie............................................");
        ObservableList<Booking> scheduleBookings = FXCollections.observableArrayList();
        String sql = "SELECT * FROM bookings WHERE schedule_id = " + scheduleId;
        try{
            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ArrayList<Integer> seatsTakenForBooking = new ArrayList();
                String seatsBooked[] = rs.getString(2).split(",");
                for (int i = 0; i < seatsBooked.length ; i++) {
                    seatsTakenForBooking.add(Integer.parseInt(seatsBooked[i]));
                }
                Date date = scheduleWrapper.getScheduleTime(scheduleId);
                LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
                String time = rs.getTimestamp(3).toLocalDateTime().toLocalTime().toString();


                String title = movieWrapper.getMovieName(rs.getInt(4));
                Booking booking = new Booking(rs.getString(1),
                        seatsTakenForBooking,
                        ld,
                        time,
                        title
                        );
                booking.setId(rs.getInt(6));
                scheduleBookings.add(booking);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scheduleBookings;
    }

    public ObservableList<Booking> getAllBookings() {
        System.out.println("Loading all bookings............................................");
        ObservableList<Booking> bookingOL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM bookings";

        try{
            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ArrayList<Integer> seats = new ArrayList<>();
                String name = rs.getString(1);
                String[] str = rs.getString(2).split(",");
                for (int i = 0; i < str.length; i++) {
                    seats.add(Integer.parseInt(str[i]));
                }
                LocalDate date = rs.getTimestamp(3).toLocalDateTime().toLocalDate();
                String time = rs.getTimestamp(3).toLocalDateTime().toLocalTime().toString();
                String title = "";

                String sqlTxt = "SELECT * FROM movies" +
                        " WHERE `id` = '" + rs.getInt(4) + "';";
                try {
                    DBConn dbConn2 = new DBConn();
                    Connection conn2 = dbConn2.getConn();
                    PreparedStatement ps2 = conn.prepareStatement(sqlTxt);
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        title = rs2.getString(1);
                    }
                    conn2.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                int id = rs.getInt(6);
                Booking book = new Booking(name, seats, date, time, title);
                book.setId(id);
                bookingOL.add(book);

            }

            conn.close();
            System.out.println("Booking load complete.");
            return bookingOL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteBooking(int id){
        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "DELETE FROM " + TABLE +
                " WHERE `id` = '" + id + "';";

        try
        {
            PreparedStatement prepStmt = conn.prepareStatement(sqlTxt);
            prepStmt.execute();
            prepStmt.close();
            conn.close();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }
    public Booking saveBooking(String name, ArrayList<Integer> seatNumbers, int movieID, Date date, int scheduleId){

        String seats = "";
        for (int i = 0; i < seatNumbers.size(); i++) {
            seats += seatNumbers.get(i) + ",";
        }
        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "INSERT INTO " + TABLE + " ( `name`, `seats`, `movie_id`, `time`,  `schedule_id`) VALUES (?,?,?,?,?)";
        try
        {
            java.sql.Timestamp sq = new java.sql.Timestamp(date.getTime());

            PreparedStatement ps = conn.prepareStatement(sqlTxt);
            ps.setString(1, name);
            ps.setString(2, seats);
            ps.setInt(3, movieID );
            ps.setTimestamp(4, sq);
            ps.setInt(5, scheduleId);

            ps.executeUpdate();
            ps.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }

    public boolean updateBooking(Booking booking, int movieID, int scheduleID) {
        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String name = booking.getName();
        ArrayList<Integer> seatNumbers = booking.getSeatNumbers();
        String seats = "";
        for (int i = 0; i < seatNumbers.size(); i++) {
            seats += seatNumbers.get(i) + ",";
        }
        LocalDate localDate = booking.getDate();
        int id = booking.getId();

        String sqlTxt = "UPDATE " + TABLE + " SET name = ?, seats = ?, movie_id = ?," +
                "time = ?, schedule_id = ?" +
                " WHERE id = ?;";
        try
        {
            Timestamp sq = Timestamp.valueOf(localDate.atStartOfDay());

            PreparedStatement ps = conn.prepareStatement(sqlTxt);
            ps.setString(1, name);
            ps.setString(2, seats);
            ps.setInt(3, movieID );
            ps.setTimestamp(4, sq);
            ps.setInt(5, scheduleID);
            ps.setInt(6,id);
            ps.execute();
            conn.close();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            return false;
        }
    }
}
