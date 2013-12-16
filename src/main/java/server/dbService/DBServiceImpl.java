package server.dbService;

import base.DBService;
import base.MessageSystem;
import server.msgsystem.Address;
import utils.Logger;

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

    public DBServiceImpl(MessageSystem messageSystem) {
        this.address = new Address();
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
    }

    @Override
    public Address getAddress() {
        return address;
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
