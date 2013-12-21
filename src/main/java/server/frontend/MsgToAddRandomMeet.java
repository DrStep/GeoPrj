package server.frontend;

import base.Frontend;
import server.msgsystem.Address;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 23:32
 * To change this template use File | Settings | File Templates.
 */
public class MsgToAddRandomMeet extends MsgToFrontend {

    private Integer reqId;
    private Object data;

    public MsgToAddRandomMeet(Address from, Address to, Integer reqId, Object data) {
        super(from, to);
        this.reqId = reqId;
        this.data = data;
    }

    @Override
    protected void exec(Frontend frontendImpl) {
        frontendImpl.addRandomMeet(reqId, data);
    }
}
