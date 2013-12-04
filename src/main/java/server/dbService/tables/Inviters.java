package server.dbService.tables;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 04.12.13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "inviters")
public class Inviters implements Serializable {
    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meet_id")
    private Meet meet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_from")
    private User userFrom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_to")
    private User userTo;

    @Column(name = "isInvite")
    private Boolean isInvite;

    @Column(name = "isAdmin")
    private Boolean isAdmin;

    @Column(name = "last_update")
    @Type(type="timestamp")
    private Date time;

    @Column(name = "isChange")
    private Boolean isChange;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public Boolean getInvite() {
        return isInvite;
    }

    public void setInvite(Boolean invite) {
        isInvite = invite;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getChange() {
        return isChange;
    }

    public void setChange(Boolean change) {
        isChange = change;
    }
}
