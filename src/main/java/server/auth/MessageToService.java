package server.auth;

import base.AccountService;
import server.msgsystem.Abonent;
import server.msgsystem.Address;
import server.msgsystem.Msg;

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
        if (abonent instanceof AccountServiceImpl) {
            exec((AccountServiceImpl) abonent);
        }
    }

    protected abstract void exec(AccountService accountService);
}
