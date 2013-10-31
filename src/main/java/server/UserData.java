package server;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 31.10.13
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
public class UserData {
    private Long id;

    /* OPTIONAL */
    private String name;
    private String password;
    private String lastName;
    private String urlPhoto;

    public UserData() { }

    public UserData(Long id) {
        this.id = id;
    }

    public UserData(Long id, String name, String password) {
        this(id);
        this.name = name;
        this.password = password;
    }

    public UserData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void createUser(UserData userData) {
        setId(userData.getId());
    }

    public void setOptionalData(String name) {
        this.name = name;
    }

    public void setOptionalData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setOptionalData(String name, String lastName, String urlPhoto) {
        this.name = name;
        this.lastName = lastName;
        this.urlPhoto = urlPhoto;
    }

    public boolean hasOptionalData() {
        if (name == null || lastName == null || urlPhoto == null) {
            return false;
        }
        return true;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
