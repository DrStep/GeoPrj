package server.dbService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 16.12.13
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */

public class InsertRequest {

    private Connection conn;

    public InsertRequest() {
        this.conn =  JDBCDriver.newInstance();
    }

    public boolean insertPlace(String table, String col, String val) {
        String sql = String.format(Locale.ENGLISH, "insert into %s (%s) values(%s)", table, col, val);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }
}

class JDBCDriver {

    private static Connection connection;

    public static Connection newInstance() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/geoV1", "root", "");
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("You made it, take control your database now!");
        return connection;
    }
}