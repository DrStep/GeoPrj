package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class MsgUpdateVkUserData extends MsgToFrontend {
    private UserData userData;
    private String sessionId;

    public MsgUpdateVkUserData(Address from, Address to, String sessionId, UserData userData) {
        super(from, to);
        this.userData = userData;
        this.sessionId = sessionId;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.setUserData(sessionId, userData);
    }
}
