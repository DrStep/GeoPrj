package server.frontend;

import base.Frontend;
import server.msgsystem.Address;
import server.UserData;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 31.10.13
 * Time: 23:38
 * To change this template use File | Settings | File Templates.
 */
public class MsgUpdateUserData extends MsgToFrontend {
    private UserData userData;
    private String sessionId;

    public MsgUpdateUserData(Address from, Address to, String sessionId, UserData userData) {
        super(from, to);
        this.userData = userData;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(Frontend frontend) {
        frontend.setUserData(sessionId, userData);
    }
}
