package server.dbService;

import org.hibernate.Session;
import java.util.Date;
import server.dbService.example.Stock;
import server.dbService.example.StockDailyRecord;
import server.dbService.tables.Dialog;
import server.dbService.tables.Messanger;
import server.dbService.tables.Users;

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

        /*Users u = new Users();
        u.setName("Anton");
        u.setPassword("Hash+Solt");
        u.setGender(1);
        u.setToken("dsdsd");
        u.setExpires(0);
        u.setExist(true);
        session.save(u);*/

        Dialog d = new Dialog();
        d.setTitle("Hello");
        session.save(d);

        Messanger m = new Messanger();
        m.setDialog(d);
        m.setMsg("Hello World!");
        m.setDateTime(new Date());
        m.setRead(false);
        session.save(m);

       /*System.out.println("Hibernate one to many (Annotation)");


        Stock stock = new Stock();
        stock.setStockCode("7052");
        stock.setStockName("PADINI");
        session.save(stock);

        StockDailyRecord stockDailyRecords = new StockDailyRecord();
        stockDailyRecords.setPriceOpen(new Float("1.2"));
        stockDailyRecords.setPriceClose(new Float("1.1"));
        stockDailyRecords.setPriceChange(new Float("10.0"));
        stockDailyRecords.setVolume(3000000L);
        stockDailyRecords.setDate(new Date());

        stockDailyRecords.setStock(stock);
        stock.getStockDailyRecords().add(stockDailyRecords);

        session.save(stockDailyRecords);*/


        session.getTransaction().commit();
        System.out.println("Done. Add User.");
    }
}
