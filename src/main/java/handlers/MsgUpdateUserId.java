package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public class MsgUpdateUserId extends MsgToFrontend {
    private String sessionId;
    private Long userId;

    MsgUpdateUserId(Address from, Address to, String sessionId, Long userId) {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.setUserId(sessionId, userId);
    }
}
