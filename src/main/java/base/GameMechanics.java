package base;

import server.UserData;
import server.msgsystem.Abonent;
import server.vkauth.VkUserData;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public interface GameMechanics extends Abonent {
    public MessageSystem getMessageSystem();
}
