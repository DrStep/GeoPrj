package task2;

import handlers.Abonent;
import handlers.Address;
import handlers.MessageSystem;
import handlers.TimeHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public class AccountService implements Abonent, Runnable {
    private MessageSystem ms;
    private Address address;

    private Map<String, Long> fakeAccounter = new HashMap<String, Long>();

    public AccountService(MessageSystem ms) {
        this.ms = ms;
        address = new Address();
        ms.addService(this);
        ms.getAccountService().setAccountService(address);

        fakeAccounter.put("user1", 1L);
        fakeAccounter.put("user2", 2L);
        fakeAccounter.put("user3", 3L);
        fakeAccounter.put("user4", 4L);
    }

    public Long getUserId(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fakeAccounter.get(name);
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
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }
}
