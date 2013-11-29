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
    @GeneratedValue(generator="loc_id")
    @GenericGenerator(name="loc_id", strategy = "increment")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="loc_id_extra")
    private Long userLocId;
}
