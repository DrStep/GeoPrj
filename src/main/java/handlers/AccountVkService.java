package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class AccountVkService implements Abonent, Runnable {
    private MessageSystem ms;
    private Address address;

    public AccountVkService(MessageSystem messageSystem) {
        this.ms = messageSystem;
        address = new Address();
        ms.addService(this);
        ms.getAccountVkService().setAccountService(address);
    }

    public UserData getUserData(String code) {
        AuthVkByCodeCmd authVk = new AuthVkByCodeCmd(code);
        authVk.onExecute();
        return authVk.getUserData();
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
