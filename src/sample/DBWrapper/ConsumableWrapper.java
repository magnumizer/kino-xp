package sample.DBWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Consumable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumableWrapper {


    private static final String TABLE = "consumables";
    Connection conn = null;

    public ObservableList<Consumable> getAllConsumable() {

        ObservableList<Consumable> consumablesOL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM consumables";


        try {
            DBConn dbConn = new DBConn();
            conn = dbConn.getConn();


            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String name = rs.getString(1);
                int price = rs.getInt(2);

                Consumable consumable = new Consumable(name,price);
                consumablesOL.add(consumable);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumablesOL;

    }

    public boolean deleteFromDB(String name){

        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "DELETE FROM " + TABLE +
                " WHERE `name` = '" + name + "';";

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

    public boolean saveIntoDB(String name,double price){

        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String sqlTxt = "INSERT INTO " + TABLE + " ( `name`, `price`) VALUES (?,?)";

        try
        {
            PreparedStatement prepStmt = conn.prepareStatement(sqlTxt);
            prepStmt.setString(1,name);
            prepStmt.setDouble(2,price);
            prepStmt.executeUpdate();
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

    public boolean updateIntoDb(Consumable consumable){

        DBConn dbConn = new DBConn();
        conn = dbConn.getConn();

        String _name = consumable.getName();
        double _price = consumable.getPrice();

        String sqlTxt = "REPLACE INTO " + TABLE + " ( `name`, `price`) VALUES (?,?)";


        try
        {
            PreparedStatement ps = conn.prepareStatement(sqlTxt);
            ps.setString(1, _name);
            ps.setDouble(2, _price);

            ps.executeUpdate();
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
