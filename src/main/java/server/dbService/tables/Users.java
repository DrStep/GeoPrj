package server.dbService.tables;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 23.11.13
 * Time: 0:19
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "user")
public class Users implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Long id;

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
    private Integer expires;

    @Column(name = "isExist")
    private Boolean isExist;

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

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }
}
