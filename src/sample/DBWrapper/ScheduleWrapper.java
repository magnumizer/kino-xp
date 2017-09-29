package sample.DBWrapper;

import com.mysql.jdbc.*;
import sample.Model.Movie;
import sample.Model.MovieDay;
import sample.Model.MovieTableObject;
import sample.Model.Schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by CIA on 20/09/2017.
 */
public class ScheduleWrapper {

    MovieWrapper movieWrapper = new MovieWrapper();
    final String TABLE = "schedule";

    public void removeMovieSchedule(int scheduleID){

        String sql = "DELETE FROM schedule WHERE ID='" + scheduleID + "'";

        DBConn dbConn = new DBConn();
        Connection conn = dbConn.getConn();

        try
        {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.execute();
            prepStmt.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public Schedule getSchedule(int roomNo) {
        System.out.println("Loading schedule " + roomNo + "............................................");
        Schedule schedule = null;
        ArrayList<MovieDay> movieDays = new ArrayList<>();
        String sql = "SELECT * FROM schedule WHERE room=" + roomNo + " ORDER BY `date` ASC";
        Connection conn;
        try {

            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Calendar cal = Calendar.getInstance();
            Calendar caleTemp = Calendar.getInstance();
            caleTemp.set(Calendar.YEAR,1);
            caleTemp.set(Calendar.DAY_OF_YEAR, 1);
            caleTemp.set(Calendar.HOUR,1);


            MovieDay movieDay = null;
            while (resultSet.next()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tempD = sdf.format(resultSet.getTimestamp(3).getTime());
                Calendar currentDateCal = Calendar.getInstance();

                try {
                    currentDateCal.setTime(sdf.parse(tempD));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (currentDateCal.get(Calendar.YEAR) > caleTemp.get(Calendar.YEAR)) {
                    caleTemp.set(Calendar.DAY_OF_YEAR, 1);
                }

                if (currentDateCal.get(Calendar.DAY_OF_YEAR) > caleTemp.get(Calendar.DAY_OF_YEAR)) {

                    Calendar movCal = (Calendar) currentDateCal.clone();
                    movieDay = new MovieDay(movCal);

                }

                for (int i = 0; i < movieDay.getMovieTableObjects().size(); i++) {
                    Calendar currentHour = Calendar.getInstance();
                    currentHour.setTime(movieDay.getMovieTableObjects().get(i).getMovieBeginTimeUtil());
                    if (currentDateCal.get(Calendar.HOUR_OF_DAY) == currentHour.get(Calendar.HOUR_OF_DAY)){
                        Movie movie = movieWrapper.getMovie(resultSet.getInt(2));
                        movieDay.getMovieTableObjects().get(i).setMovieTitle(movie.getTitle());
                        movieDay.getMovieTableObjects().get(i).setId(resultSet.getInt(1));
                        double duration = movie.getDuration() + 30;
                        double hours = round(duration/60, 0);
                        for (int j = 1; j <= hours ; j++) {
                            if ((i + j) < movieDay.getMovieTableObjects().size()) {
                                movieDay.getMovieTableObjects().get(i + j).setMovieTitle("--");
                            }
                        }

                    }

                }

                if (currentDateCal.get(Calendar.DAY_OF_YEAR) > caleTemp.get(Calendar.DAY_OF_YEAR)) {
                    movieDays.add(movieDay);
                    caleTemp = currentDateCal;

                }


            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        schedule = new Schedule(movieDays);
        System.out.println("Schedule " + roomNo + " load complete.");
        return schedule;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void saveMovieToSchedule (int id, java.util.Date date, int room){

        DBConn db = new DBConn();

        Connection connection = db.getConn();
        String sql = "INSERT INTO schedule (movie_id, date, room) " +
                "VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = null;

        java.sql.Timestamp name = new java.sql.Timestamp(date.getTime());


        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setTimestamp(2, name);
            preparedStatement.setInt(3, room);

            preparedStatement.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public java.util.Date getScheduleTime(int scheduleId){
        String query = "SELECT date FROM schedule WHERE id = " + scheduleId ;
        Connection conn;
        Date date = null;
        try {

            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                date = new Date(timestamp.getTime());
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

   /* public void cancelMovieFromSchedule(MovieDay movieDay, MovieTableObject mto) {


        String query = "DELETE FROM 'schedule' WHERE 'date' = ?, 'room ' = ?";

        Connection conn;
        try {

            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, (Date) movieDay.getCal().getTime());
            preparedStatement.setString(2,);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
