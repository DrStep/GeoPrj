package test;

import junit.framework.Assert;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import server.dbService.*;
import server.dbService.tables.*;
import server.frontend.FrontendImpl;
import server.resourceSystem.ResourceTest;
import server.resourceSystem.SAXP;
import server.resourceSystem.ServerData;
import utils.GeoPoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 05.12.13
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class DBServiceTest {

    private DAO dao;
    @org.junit.Before
    public void setUp() throws Exception {
        dao = new DAO();
    }

    @Test
    public void testInserts()
    {
        Map<String, Object> mapInsert = new HashMap<>();
        String key;
        String value;

        key = "title1";
        value = "mytitle";
        mapInsert.put(key, value);
        key = "photo";
        value = "qqq";
        mapInsert.put(key, value);
        key = "status";
        value = "perfect";
        mapInsert.put(key, value);
        key = "message";
        value = "message";
        mapInsert.put(key, value);

        key = "description";
        value = "qwerty";
        mapInsert.put(key, value);

        int userId=8;
        dao.insertMeet(userId,mapInsert);

        List list =  dao.getLastMeetsId();
        Map row = (Map)list.get(0);
        Integer str =  (Integer) row.get("max");

        System.out.println(row.get("max"));

        String fields = "title as title, description as description, status as status";
        list = dao.getMeetById( str, fields);

        row = (Map)list.get(0);
        Assert.assertTrue(row.get("title").equals("mytitle"));
        Assert.assertTrue(row.get("description").equals("qwerty"));
        Assert.assertTrue(row.get("status").equals( "perfect"));
    }

    @Test
    public void getAllLocations() {
        int meetRange = 179904;
        int placeRange = 100000;
        int usersRange = 15000;

        List meets = dao.getAllMeetsInCoordinates(new LocationRange(-180D, -180D, 180D, 180D), "meet_id,title");

        String place = FrontendImpl.getJSONByList(dao.getAllPlacesInCoordinates(new LocationRange(-180D, -180D, 180D, 180D), "place_id, title"));
        String users = FrontendImpl.getJSONByList(dao.getAllUsersInCoordinates(new LocationRange(-180D, -180D, 180D, 180D), "user.id,name"));

        for (int i = 0; i < meets.size(); i++) {
            Map map = (HashMap) meets.get(i);
            Integer meetId = Integer.parseInt(map.get("meet_id").toString());
            Assert.assertTrue(meetId <= meetRange);
        }

        List places = dao.getAllPlacesInCoordinates(new LocationRange(0D, 0D, 180D, 180D), "place_id, title");
        for (int i = 0; i < places.size(); i++) {
            Map map = (HashMap) places.get(i);
            Integer placeId = Integer.parseInt(map.get("place_id").toString());
            Assert.assertTrue(placeId <= placeRange);
        }

        List user = dao.getAllUsersInCoordinates(new LocationRange(0D, 0D, 180D, 180D), "user.id,name");
        for (int i = 0; i < user.size(); i++) {
            Map map = (HashMap) user.get(i);
            Integer userId = Integer.parseInt(map.get("id").toString());
            Assert.assertTrue(userId <= usersRange);
        }

        Assert.assertTrue(users.length() > 0);
        Assert.assertTrue(place.length() > 0);

    }

}
