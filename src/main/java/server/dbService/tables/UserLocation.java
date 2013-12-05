package server.dbService.tables;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 29.11.13
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "user_location")
public class UserLocation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loc_id", unique = true, nullable = false)
    private Integer id;

    @Column(name="user_id")
    private User userId;

    @Column(name="loc_id_extra")
    private Location userLocId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Location getUserLocId() {
        return userLocId;
    }

    public void setUserLocId(Location userLocId) {
        this.userLocId = userLocId;
    }
}
