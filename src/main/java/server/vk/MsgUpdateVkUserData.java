package server.vk;

import server.Address;
import server.Frontend;
import server.MsgToFrontend;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class MsgUpdateVkUserData extends MsgToFrontend {
    private VkUserData vkUserData;
    private String sessionId;

    public MsgUpdateVkUserData(Address from, Address to, String sessionId, VkUserData vkUserData) {
        super(from, to);
        this.vkUserData = vkUserData;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(Frontend frontend) {
        frontend.setVkUserData(sessionId, vkUserData);
    }
}
