package server.frontend;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 08.11.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public enum UrlList {

    VKAUTH("vk-auth"), AUTH("auth"), AUTHFORM("authform"), CHECKUSERAUTH("checkuserjsonp"), ADMIN("admin"), API("api"), MAIN("main"),
                                                            AJAX("ajax"), OBJECTS_LOCATION("objects_location"), ERROR("error"), RANDOM_MEET("random_meet");
    private String url;

    UrlList(String url) {
        this.url = url;
    }

    public static UrlList getUrlListByPath(String urlPath) {
        for (UrlList path : UrlList.values()) {
            if (path.getPath().equals(urlPath)) {
                return path;
            }
        }
        return ERROR;
    }

    public String getPath() {
        return new StringBuffer("/").append(this.url).toString();
    }

    @Override
    public String toString() {
        return url;
    }
}
