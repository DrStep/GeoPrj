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
    public void getAllLocations() {
        int meetRange = 1000;

        List<Map> meets = dao.getAllMeetsInCoordinates(new LocationRange(0D, 0D, 180D, 180D), "meet_id,title");
        String place = FrontendImpl.getJSONByList(dao.getAllPlacesInCoordinates(new LocationRange(0D, 0D, 180D, 180D), "place_id, title"));
        String users = FrontendImpl.getJSONByList(dao.getAllUsersInCoordinates(new LocationRange(0D, 0D, 180D, 180D), "user.id,name"));

        for (int i = 0; i < meets.size(); i++) {
            Map map = (HashMap) meets.get(i);
            Integer meetId = Integer.parseInt(map.get("meet_id").toString());
            Assert.assertTrue(meetId <= meetRange);
            //String title = map.get("title").toString();
        }

        Assert.assertTrue(users.length() > 0);
    }

    @Test
    public void resourceSystemTest() {
        ResourceTest resourceTest = (ResourceTest) SAXP.readServerData("restest.xml");
        Assert.assertTrue(resourceTest.getId() == 10);
        Assert.assertTrue(resourceTest.getName().equals("Anton"));
        Assert.assertTrue(resourceTest.getLat() == 100);
        Assert.assertTrue(resourceTest.getLng() == 180);

        System.out.println(resourceTest.getId() + " " + resourceTest.getName() + " " + resourceTest.getLat() + resourceTest.getLng());
    }
}
