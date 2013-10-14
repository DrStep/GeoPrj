package task2;

import handlers.Abonent;
import handlers.Address;
import handlers.Msg;
import task2.AccountService;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class MsgToAS extends Msg {

    MsgToAS(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService accountService);
}
