package server.dbService;

import java.sql.*;
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

    public boolean insertFriends(int user1, int user2) {
        String sql1 = String.format(Locale.ENGLISH, "insert into friends  values(NULL, %d, %d);", user1, user2);
        String sql2 = String.format(Locale.ENGLISH, "insert into friends  values(NULL, %d, %d);", user2, user1);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql1);
            ps.executeUpdate();
            ps = conn.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }

    public boolean insertMeetString (Float latitude, Float longitude, String col, String val) {
        String sql1 = String.format(Locale.ENGLISH, "insert into location values(NULL, %f, %f, now());", latitude, longitude);
        String sql2 = String.format(Locale.ENGLISH, "insert into wall values(NULL, now(), 'Wall');");
        try {
            PreparedStatement ps = conn.prepareStatement(sql1);
            int locId = ps.executeUpdate();

            ps = conn.prepareStatement(sql2);
            int wallId = ps.executeUpdate();

            String sql3 = String.format(Locale.ENGLISH, "insert into meet(%s,loc_id, wall_id) values(%s,%d,%d);", col, val, locId, wallId);
            ps = conn.prepareStatement(sql3);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }

    public boolean updateLocation(int userId, String val) {
        String sql1 = String.format(Locale.ENGLISH, "select l.loc_id from `user` as u inner join `user_location` as ul inner join location as l  on u.id=ul.user_id and ul.loc_id_extra=l.loc_id and u.id=%d limit 0,1; ", userId);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery(sql1);
            rs.next();
            int res = rs.getInt("loc_id");
            String sql2 = String.format(Locale.ENGLISH, "update location set %s where loc_id=%d", val, res);
            ps = conn.prepareStatement(sql2);
            res = ps.executeUpdate();
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
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/geoV1", "root", "123456");
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("You made it, take control your database now!");
        return connection;
    }
}