package base;

import server.msgsystem.Abonent;
import server.UserData;
import server.vkauth.VkUserData;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 02.11.13
 * Time: 9:00
 * To change this template use File | Settings | File Templates.
 */
public interface Frontend extends Abonent {
    void setVkUserData(String sessionId, VkUserData vkUserData);
    void setUserData(String sessionId, UserData userData);
    void addDbData(Integer reqId, Object data);
    void addRandomMeet(Integer reqId, Object data);

    Map<String, VkUserData> getVkSessionIdToUserData();
    Map<String, UserData> getSessionIdToUserData();
    Map<String, UserData> getSessionIdToInvalidUserData();

    MessageSystem getMessageSystem();
}
