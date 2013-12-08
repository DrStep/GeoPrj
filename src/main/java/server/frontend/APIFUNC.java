package server.frontend;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 07.12.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public enum APIFUNC {
    GET_USERS("users.get"), GET_USERS_LOCATION("users.location"), GET_PLACE_LOCATION("place.location"), GET_MEET_LOCATION("meet.location"),
                GET_USERS_FRIENDS("users.friends"), GET_USERS_DIALOGS("users.dialogs"), GET_USERS_MEET("users.meet"), GET_DIALOGS("dialog.get"),
                    GET_MESSANGER("messanger.get"), GET_MEETS_FOR_USERID("meets.for_userid"), GET_MEET("meet.get"), DEFAULT("error");

    private String method;
    APIFUNC(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public static APIFUNC getApiFuncByMethod(String method) {
        for (APIFUNC apiFunc : APIFUNC.values()) {
            if (apiFunc.getMethod().equalsIgnoreCase(method)) {
                return apiFunc;
            }
        }
        return DEFAULT;
    }
}
