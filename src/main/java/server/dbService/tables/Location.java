package server.dbService.tables;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 29.11.13
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {
    @Id
    @GeneratedValue(generator="loc_id")
    @GenericGenerator(name="loc_id", strategy = "increment")
    private Integer id;

    @Column(name="latitude")
    private Float latitude;

    @Column(name="longitude")
    private Float longitude;

    @Column(name="datetime")
    @Type(type="timestamp")
    private Date time;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "locations")
    private Set<User> users = new HashSet<User>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
