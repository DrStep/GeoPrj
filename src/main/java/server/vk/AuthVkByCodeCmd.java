package server.vk;

import json.JSONObject;
import server.Command;
import utils.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class AuthVkByCodeCmd extends Command {
    private final static String ACCESS_TOKEN = "https://oauth.vk.com/access_token?";
    private final static String CLIENT_SECRET = "RWLmNUUpg2HfW01LjhNf";
    private final static String CLIENT_ID = "3913319";
    private final static String REDIRECT_URI = "http://localhost:8090";
    private final static String AUTH_URI = "vk-auth";

    private final static String ATTR_ACCESS_TOKEN = "access_token";
    private final static String ATTR_USER_ID = "user_id";
    private final static String ATTR_EXPIRES_IN = "expires_in";

    private String code;
    private VkUserData vkUserData;

    public AuthVkByCodeCmd(String code) {
        super();
        this.code = code;
    }

    @Override
    public String createUrl() {
        StringBuilder sb = new StringBuilder(ACCESS_TOKEN);
        sb.append("client_id=" + CLIENT_ID);
        sb.append("&code=" + code);
        sb.append("&client_secret=" + CLIENT_SECRET);
        sb.append("&redirect_uri=" + REDIRECT_URI + "/" + AUTH_URI);
        return sb.toString();
    }

    @Override
    public void onResponseOk(String data) {
        JSONObject jsonData = new JSONObject(data);

        String accessToken = jsonData.getString(ATTR_ACCESS_TOKEN);
        Long userId = jsonData.getLong(ATTR_USER_ID);
        int expiresIn = jsonData.getInt(ATTR_EXPIRES_IN);

        this.vkUserData = new VkUserData(userId, accessToken, expiresIn);
        Logger.network("token: " + accessToken + " userId:" + userId + " expires_in:" + expiresIn);
    }

    public VkUserData getVkUserData() {
        return vkUserData;
    }
}
