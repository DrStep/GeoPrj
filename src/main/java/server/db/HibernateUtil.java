package server.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import server.db.tables.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 23.11.13
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Users.class);
            configuration.addAnnotatedClass(Dialog.class);
            configuration.addAnnotatedClass(Messanger.class);

            configuration.addAnnotatedClass(Test.class);
            configuration.addAnnotatedClass(Test1.class);

            SessionFactory sessionFactory = createSessionFactory(configuration);
            return sessionFactory;

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory createSessionFactory(Configuration configuration){
        //"jdbc:mysql://localhost:3306/geo", "test", "test123"
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/geo");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test123");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}