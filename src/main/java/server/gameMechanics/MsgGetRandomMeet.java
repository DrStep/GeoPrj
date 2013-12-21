package server.gameMechanics;

import server.dbService.DAO;
import server.frontend.MsgToAddRandomMeet;
import server.msgsystem.Address;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 */
public class MsgGetRandomMeet extends MsgToGameMechanics {

    private Integer reqId;
    private Integer nMeet;

    public MsgGetRandomMeet(Address from, Address to, Integer newReqId, Integer nMeet) {
        super(from, to);
        this.nMeet = nMeet;
        this.reqId = newReqId;
    }

    @Override
    protected void exec(GameMechanicsImpl gameMechanics) {
        int random = gameMechanics.getRandomMeet(nMeet);
        gameMechanics.getMessageSystem().sendMessage(new MsgToAddRandomMeet(getTo(), getFrom(), reqId, random));
    }
}
