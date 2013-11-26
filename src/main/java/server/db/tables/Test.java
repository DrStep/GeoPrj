package server.db.tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 25.11.13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="test")
public class Test implements Serializable {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
    private List<Test1> testList = new ArrayList<Test1>();

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy = "increment")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Test1> getTestList() {
        return testList;
    }

    public void setTestList(List<Test1> testList) {
        this.testList = testList;
    }
}
