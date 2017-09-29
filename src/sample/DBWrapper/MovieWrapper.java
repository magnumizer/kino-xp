package sample.DBWrapper;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jakub on 19.09.2017.
 */
public class MovieWrapper {

    private static final String TABLE = "movies";
    private static MovieWrapper thisWrapper;
    Connection conn = null;

    public MovieWrapper()
    {
    }


    public ObservableList<Movie> getAllMovies() {

        ObservableList<Movie> moviesOL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM movies";

        try {

            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getString(1),
                                        resultSet.getString(2),
                                        resultSet.getInt(3),
                                        resultSet.getString(4),
                                        resultSet.getInt(6));
                movie.setId(resultSet.getInt(5));
                moviesOL.add(movie);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moviesOL;
    }

    public String getMovieName(int movieId){
        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();
        String title = "";
        String sqlTxt = "SELECT title FROM movies WHERE id =" + movieId;

        try
        {
            PreparedStatement prepStmt = conn.prepareStatement(sqlTxt);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()){
                title = rs.getString(1);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return title;
    }

    public Movie getMovie(int _id) {

        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "SELECT * FROM " + TABLE +
                " WHERE `id` = '" + _id + "';";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            ResultSet rs = prepStmt.executeQuery();

            if (!rs.next())
            {
                return null;
            }

            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            int age = rs.getInt("age_restriction");
            String actors = rs.getString("actors");
            int duration = rs.getInt("duration");

            prepStmt.close();

            Movie movie = new Movie(title, description, age, actors, duration);
            movie.setId(id);
            conn.close();
            return movie;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateMovie(Movie movie) {
        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        int id = movie.getId();
        String title = movie.getTitle();
        String description = movie.getDescription();
        int ageRequirement = movie.getAgeRestriction();
        String actors = movie.getActors();
        int duration = movie.getDuration();

        String sqlTxt = "UPDATE " + TABLE + " SET title = ?, description = ?, age_restriction = ?," +
                "actors = ?, duration = ?" +
                " WHERE id = ?;";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sqlTxt);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, ageRequirement);
            ps.setString(4, actors);
            ps.setInt(6,id);
            ps.setInt(5, duration);
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

    public boolean deleteMovie(int id) {
        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "DELETE FROM " + TABLE +
                " WHERE `id` = '" + id + "';";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);
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

    public Movie saveMovie(String title, String description, int ageRequirement, String actors, int duration){

        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "INSERT INTO " + TABLE + " ( `title`, `description`, `age_restriction`, `actors`, `duration`) VALUES (?,?,?,?,?)";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sqlTxt);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, ageRequirement);
            ps.setString(4, actors);
            ps.setInt(5, duration);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return new Movie(title,description, ageRequirement,actors, duration);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }

    /*public Movie getDataFromString(String searchedName, String searchedColumn){

        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "SELECT * FROM " + TABLE +
                " WHERE `id` = '" + id + "';";

        Movie movie = new Movie();

        return movie;

    }

    public Movie getDataFromInt(int integer){



        Movie movie = new Movie();

        return movie;

    }*/

}
