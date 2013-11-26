package server.db;

import org.hibernate.Session;
import server.db.tables.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 22.11.13
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class DBService {

    public DBService() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

       /* Users u = new Users();
        u.setName("Anton");
        u.setPassword("Hash+Solt");
        u.setGender(1);
        u.setToken("dsdsd");
        u.setExpires(0);
        u.setExist(true);
        session.save(u);

        Dialog d = new Dialog();
        d.setTitle("Hello");
        session.save(d);

        Messanger m = new Messanger();
        m.setDialogId(1L);
        m.setMsg("Hello World!");
        m.setDateTime(new Date());
        m.setRead(false);
        session.save(m);*/

        Test test = new Test();
        Test1 test1 = new Test1();
        test.setName("Hello");
        test1.setPassword("dsad");
        test1.setTestId(test1.getId());

        session.save(test);
        session.save(test1);


        session.getTransaction().commit();
        System.out.println("Done. Add User.");
    }
}
