package server.dbService;

import base.DBService;
import base.MessageSystem;
import server.msgsystem.Address;
import utils.GeoPoint;
import utils.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.12.13
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class DBServiceImpl implements DBService, Runnable {
    private static final int TICK_TIME = 20;
    private Address address;
    private MessageSystem messageSystem;
    private DAO dao;

    public DBServiceImpl(MessageSystem messageSystem) {
        this.address = new Address();
        this.dao = new DAO();
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
    }

    /*  NW - North-West
        ES - East-South
    */
    public List getMeetsInLocation(GeoPoint nwPoint, GeoPoint esPoint, String fields) {
        return dao.getAllMeetsInCoordinates(new LocationRange(nwPoint.getLatitude(), nwPoint.getLongitude(),
                                                            esPoint.getLatitude(), esPoint.getLongitude()), fields);
    }

    public List getPlacesInLocation(GeoPoint nwPoint, GeoPoint esPoint, String fields) {
        return dao.getAllPlacesInCoordinates(new LocationRange(nwPoint.getLatitude(), nwPoint.getLongitude(),
                esPoint.getLatitude(), esPoint.getLongitude()), fields);
    }

    public List getUsersInLocation(GeoPoint nwPoint, GeoPoint esPoint, String fields) {
        return dao.getAllUsersInCoordinates(new LocationRange(nwPoint.getLatitude(), nwPoint.getLongitude(),
                esPoint.getLatitude(), esPoint.getLongitude()), fields);
    }

    public List getMeetById(Integer meetId) {
        return dao.getMeetById(meetId, "meet_id, title");
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return this.messageSystem;
    }

    @Override
    public void run() {
        while(true) {
            try {
                long startTime = System.currentTimeMillis();
                messageSystem.execForAbonent(this);
                long delta = System.currentTimeMillis() - startTime;
                if (delta < TICK_TIME) {
                    Thread.sleep(TICK_TIME - delta);
                }
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
            }
        }
    }
}
