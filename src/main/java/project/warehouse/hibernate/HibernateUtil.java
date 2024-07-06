package project.warehouse.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Component;
import project.warehouse.entity.*;

import java.io.File;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
//            return new Configuration().configure().buildSessionFactory();

            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(ComponentEntity.class);
            configuration.addAnnotatedClass(OrderEntity.class);
            configuration.addAnnotatedClass(ProductEntity.class);
            configuration.addAnnotatedClass(ProviderEntity.class);
            configuration.addAnnotatedClass(ShipmentEntity.class);
            configuration.addAnnotatedClass(WarehouseEntity.class);

            return configuration.buildSessionFactory();
        }
        catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null){
            sessionFactory = buildSessionFactory();
        }

        return sessionFactory;
    }

}