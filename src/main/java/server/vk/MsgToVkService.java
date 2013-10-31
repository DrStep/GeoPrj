package server.vk;

import server.Abonent;
import server.Address;
import server.Msg;
import server.vk.AccountVkService;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
public abstract class MsgToVkService extends Msg {

    MsgToVkService(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof AccountVkService) {
            exec((AccountVkService) abonent);
        }
    }

    public abstract void exec(AccountVkService accountService);
}
