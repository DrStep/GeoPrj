package server.dbService.tables;

import java.io.Serializable;
import java.util.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 23.11.13
 * Time: 0:19
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer gender;

    @Column(name = "online")
    @Type(type="timestamp")
    private Date modifyDate;

    @Column(name = "token")
    private String token;

    @Column(name = "expires")
    private Long expires;

    @Column(name = "isExist")
    private Boolean isExist;

    @Column(name = "photo")
    private String photo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "dialog_user", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "dialog_id",
                    nullable = false, updatable = false) })
    private List<Dialog> dialogs = new ArrayList<Dialog>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_location", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "loc_id_extra",
                    nullable = false, updatable = false) })
    private List<Location> locations = new ArrayList<Location>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user1")
    private List<Friends> friendsList1 = new ArrayList<Friends>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user2")
    private List<Friends> friendsList2 = new ArrayList<Friends>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Meet> meets = new HashSet<Meet>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Messanger> msgList = new ArrayList<Messanger>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "participants")
    private List<Meet> participants = new ArrayList<Meet>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Set<Meet> getMeets() {
        return meets;
    }

    public void setMeets(Set<Meet> meets) {
        this.meets = meets;
    }

    public List<Friends> getFriendsList1() {
        return friendsList1;
    }

    public void setFriendsList1(List<Friends> friendsList1) {
        this.friendsList1 = friendsList1;
    }

    public List<Friends> getFriendsList2() {
        return friendsList2;
    }

    public void setFriendsList2(List<Friends> friendsList2) {
        this.friendsList2 = friendsList2;
    }

    public List<Messanger> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Messanger> msgList) {
        this.msgList = msgList;
    }

    public List<Meet> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Meet> participants) {
        this.participants = participants;
    }
}
