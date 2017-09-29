package sample.DBWrapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by blackhatt on 25/09/2017.
 */
class DBConnTest {

    DBConn dbConn = new DBConn();
    @Test
    void getConn() {
        assertNotEquals(null,dbConn.getConn());
        assertNotEquals(null, dbConn.getConn());

    }

}