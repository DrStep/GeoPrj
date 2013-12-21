package server.gameMechanics;

import base.Frontend;
import server.msgsystem.Abonent;
import server.msgsystem.Address;
import server.msgsystem.Msg;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class MsgToGameMechanics extends Msg {

    public MsgToGameMechanics(Address from, Address to) {
        super(from, to);
    }

    @Override
    protected void exec(Abonent abonent) {
        if (abonent instanceof GameMechanicsImpl) {
            exec((GameMechanicsImpl) abonent);
        }
    }

    protected abstract void exec(GameMechanicsImpl gameMechanics);
}
