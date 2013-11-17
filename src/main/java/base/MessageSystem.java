package base;

import server.msgsystem.Abonent;
import server.msgsystem.AddressService;
import server.msgsystem.Msg;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 06.11.13
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public interface MessageSystem {
    void addService(Abonent abonent);
    void sendMessage(Msg msg);
    void execForAbonent(Abonent abonent);
    AddressService getAccountService();
    AddressService getAccountVkService();
}
