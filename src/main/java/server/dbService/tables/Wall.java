package server.dbService.tables;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 04.12.13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "wall")
public class Wall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wall_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "date_time")
    @Type(type="timestamp")
    private Date dateTime;

    @Column(name = "msg")
    private String msg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
