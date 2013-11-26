package server.db.tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 25.11.13
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="test1")
public class Test1 implements Serializable {
    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Integer testId;

    @Column(name = "password")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
