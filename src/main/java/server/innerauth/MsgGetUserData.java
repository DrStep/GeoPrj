package server.innerauth;

import server.Address;
import server.UserData;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 31.10.13
 * Time: 23:06
 * To change this template use File | Settings | File Templates.
 */
public class MsgGetUserData extends MessageToService {
    private UserData userData;
    private String sessionId;

    public MsgGetUserData(Address from, Address to, String sessiondId, UserData userData) {
        super(from, to);
        this.sessionId = sessiondId;
        this.userData = userData;
    }

    @Override
    protected void exec(AccountService accountService) {
        UserData userData = accountService.getUserData(this.userData);
        accountService.getMessageSystem().sendMessage(new MsgUpdateUserData(getTo(), getFrom(), sessionId, userData));
    }
}
