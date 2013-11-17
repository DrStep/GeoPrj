package server.vkauth;

import server.UserData;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class VkUserData extends UserData {
    /* Authorization */
    private String accessToken;
    private int expiresIn;

    public VkUserData() {
        super();
    }

    public VkUserData(Long id, String accessToken, int expiresIn) {
        super(id);
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public void createUser(VkUserData userData) {
        setId(userData.getId());
        setAccessToken(userData.getAccessToken());
        setExpiresIn(userData.expiresIn);
    }

    public boolean isTokenExist() {
        if (accessToken == null) {
            return false;
        }
        return true;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
