package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 20:13
 * To change this template use File | Settings | File Templates.
 */
public class MsgGetVkUserData extends MsgToVkService {
    private String code;
    private String sessionId;

    public MsgGetVkUserData(Address from, Address to, String sessionId, String code) {
        super(from, to);
        this.code = code;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountVkService accountService) {
        UserData userData = accountService.getUserData(code);
        accountService.getMessageSystem().sendMessage(new MsgUpdateVkUserData(getTo(), getFrom(), sessionId, userData));
    }
}
