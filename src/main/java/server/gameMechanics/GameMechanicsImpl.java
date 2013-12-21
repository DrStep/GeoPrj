package server.gameMechanics;

import base.GameMechanics;
import base.MessageSystem;
import server.msgsystem.Address;
import utils.Logger;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class GameMechanicsImpl implements GameMechanics, Runnable {
    private static final int TICK_TIME = 20;

    private Address address;
    private MessageSystem messageSystem;

    private Random random = new Random();

    public GameMechanicsImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.address = new Address();
        this.messageSystem.addService(this);
        this.random = new Random();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public int getRandomMeet(int nMeet) {
        return random.nextInt(nMeet);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long startTime = System.currentTimeMillis();
                messageSystem.execForAbonent(this);
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
