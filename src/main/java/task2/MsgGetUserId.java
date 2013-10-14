package task2;

import handlers.Address;
import task2.AccountService;
import task2.MsgToAS;
import task2.MsgUpdateUserId;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class MsgGetUserId extends MsgToAS {
    private String login;
    private String sessionId;

    public MsgGetUserId(Address from, Address to, String sessionId, String login) {
        super(from, to);
        this.login = login;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        Long userId = accountService.getUserId(login);
        accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), sessionId, userId));
    }
}
