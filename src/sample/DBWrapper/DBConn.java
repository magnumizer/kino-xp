
package sample.DBWrapper;
import sample.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBConn {

    private final String URL = "jdbc:mysql://kinoxp.cezz2j7x3idi.eu-west-2.rds.amazonaws.com:3306/";
    private final String DB_NAME = "kinoXP";
    private final String USER = "anthelopes";
    private final String PASS = "anthelopes123";


    public Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    this.URL + this.DB_NAME,
                    this.USER,
                    this.PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}