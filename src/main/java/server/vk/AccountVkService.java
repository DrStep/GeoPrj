package server.vk;

import server.*;
import utils.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class AccountVkService implements Abonent, Runnable {
    private static final int TICK_TIME = 20;

    private MessageSystem ms;
    private Address address;

    public AccountVkService(MessageSystem messageSystem) {
        this.ms = messageSystem;
        address = new Address();
        ms.addService(this);
        ms.getAccountVkService().setAccountService(address);
    }

    public VkUserData getUserData(String code) {
        AuthVkByCodeCmd authVk = new AuthVkByCodeCmd(code);
        authVk.onExecute();
        return authVk.getVkUserData();
    }

    public MessageSystem getMessageSystem() {
        return ms;
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
                ms.execForAbonent(this);
                long deltaTime = System.currentTimeMillis() - startTime;
                if (deltaTime < TICK_TIME) {
                    Thread.sleep(TICK_TIME - deltaTime);
                }
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
            }
        }
    }
}
