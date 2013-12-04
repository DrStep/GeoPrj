package server.dbService.tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 25.11.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "dialog")
public class Dialog implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer dialogId = -1;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dialog")
    private List<Messanger> msgList = new ArrayList<Messanger>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dialogs")
    private Set<User> users = new HashSet<User>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDialogId() {
        return dialogId;
    }

    public void setDialogId(Integer dialogId) {
        this.dialogId = dialogId;
    }

    public List<Messanger> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Messanger> msgList) {
        this.msgList = msgList;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
