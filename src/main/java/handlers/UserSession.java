package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class UserSession {
    private Address accountService;

    private Long userId;
    private String name;
    private String sessionId;

    UserSession(String sessionId, String name, AddressService addressService) {
        this.sessionId = sessionId;
        this.name = name;
        this.accountService = addressService.getAccountService();
    }

    public Address getAccountService() {
        return accountService;
    }

    public String getName() {
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
