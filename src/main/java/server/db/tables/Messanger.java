package server.db.tables;

import java.util.Date;

import com.sun.istack.internal.NotNull;
import com.thoughtworks.selenium.condition.Not;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 25.11.13
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "messanger")
public class Messanger implements Serializable {
    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Long id;

    //@ManyToOne(targetEntity = Dialog.class)
    //@JoinColumn(name="id", insertable = false, updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Long dialogId;

    @NotNull
    @Column(name = "msg")
    private String msg;

    @Column(name = "date_time")
    @Type(type="timestamp")
    private Date dateTime;

    @Column(name = "is_read")
    private Boolean isRead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
