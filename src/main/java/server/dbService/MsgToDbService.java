package server.dbService;

import server.dbService.DBServiceImpl;
import server.gameMechanics.GameMechanicsImpl;
import server.msgsystem.Abonent;
import server.msgsystem.Address;
import server.msgsystem.Msg;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public abstract class MsgToDbService extends Msg {

    public MsgToDbService(Address from, Address to) {
        super(from, to);
    }

    @Override
    protected void exec(Abonent abonent) {
        if (abonent instanceof DBServiceImpl) {
            exec((DBServiceImpl) abonent);
        }
    }

    protected abstract void exec(DBServiceImpl dbService);
}
