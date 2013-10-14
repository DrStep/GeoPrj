package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 14.10.13
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class UserData {
    private Long id;
    private String accessToken;
    private String name;
    private String lastName;
    private String urlPhoto;
    private int expiresIn;

    public UserData() {

    }

    public boolean isEmpty() {
        if (accessToken == null) {
            return true;
        }
        return false;
    }

    public UserData(Long id, String accessToken, int expiresIn) {
        this.id = id;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public void copy(UserData userData) {
        setId(userData.getId());
        setAccessToken(userData.getAccessToken());
        setExpiresIn(userData.expiresIn);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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
