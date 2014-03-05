package server.dbService.tables;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 06.12.13
 * Time: 0:37
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "participants")
public class Participants implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participant_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "meet_id")
    private Meet meet;

    @Column(name = "user_id")
    private User user;

    @Column(name = "last_update")
    @Type(type="timestamp")
    private Date lastUpdate;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
