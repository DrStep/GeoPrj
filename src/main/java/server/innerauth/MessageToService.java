package server.innerauth;

import server.Abonent;
import server.Address;
import server.Msg;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 31.10.13
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class MessageToService extends Msg {

    public MessageToService(Address from, Address to) {
        super(from, to);
    }

    @Override
    protected void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    protected abstract void exec(AccountService accountService);
}
