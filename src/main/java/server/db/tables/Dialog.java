package server.db.tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Long dialogId;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
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
}
