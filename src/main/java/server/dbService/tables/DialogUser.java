package server.dbService.tables;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 25.11.13
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="dialog_user")
public class DialogUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dialog_user_id", unique = true, nullable = false)
    private Integer dialogUserId;

    private Dialog dialogId;

    private User userId;

    public Integer getDialogUserId() {
        return dialogUserId;
    }

    public void setDialogUserId(Integer dialogUserId) {
        this.dialogUserId = dialogUserId;
    }

    public Dialog getDialogId() {
        return dialogId;
    }

    public void setDialogId(Dialog dialogId) {
        this.dialogId = dialogId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
