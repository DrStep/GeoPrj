package server.db.tables;

import javax.persistence.Entity;
import javax.persistence.Table;
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
    private Long dialogUserId;

}
