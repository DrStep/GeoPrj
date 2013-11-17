package base;

import server.msgsystem.Abonent;
import server.UserData;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 06.11.13
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public interface AccountService extends Abonent {
    MessageSystem getMessageSystem();
    UserData getUserData(UserData user);
}
