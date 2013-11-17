package server.auth;

import base.AccountService;
import base.MessageSystem;
import server.msgsystem.Address;
import server.UserData;
import utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 31.10.13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
public class AccountServiceImpl implements AccountService, Runnable {
    private static final int TICK_TIME = 20;
    private Address address;
    private MessageSystem messageSystem;

    private List<UserData> fakeAccount = new ArrayList<UserData>();

    public AccountServiceImpl(MessageSystem messageSystem) {
        this.address = new Address();
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAccountService().setAccountService(address);
        createFackeAccounts();
    }

    public void createFackeAccounts() {
        fakeAccount.add(new UserData(1L, "toha", "toha"));
        fakeAccount.add(new UserData(2L, "sonya", "sonya"));
        fakeAccount.add(new UserData(3L, "fill", "fill"));
        fakeAccount.add(new UserData(4L, "stepa", "stepa"));
    }

    public UserData getUserData(UserData user) {
        String name = user.getName();
        String password = user.getPassword();
        for(int i = 0; i < fakeAccount.size(); i++) {
            UserData userData = fakeAccount.get(i);
            if (userData.getName().equals(name) && userData.getPassword().equals(password)) {
                return userData;
            }
        }
        return null;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
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
