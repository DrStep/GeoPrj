package server.dbService.tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
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
    private Long dialogId;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dialogId")
    private List<Messanger> msgList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return dialogId;
    }

    public void setId(Long id) {
        this.dialogId = id;
    }

    public List<Messanger> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Messanger> msgList) {
        this.msgList = msgList;
    }
}
