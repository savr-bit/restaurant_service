package org.example.util;

import java.util.Properties;

import org.example.entity.FoodOrder;
import org.example.entity.MenuItem;
import org.example.entity.OrderMenuItem;
import org.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties

                Properties settings = new Properties();



                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://0.0.0.0:5432/restaurant_service");
                settings.put(Environment.USER, "savr_kubanov");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");


                // c3p0 configuration
                settings.put(Environment.C3P0_MIN_SIZE, 5);         //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, 21);        //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);//Number of connections acquired at a time when pool is exhausted
                settings.put(Environment.C3P0_TIMEOUT, 1800);       //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size

                settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(FoodOrder.class);
                configuration.addAnnotatedClass(MenuItem.class);
                configuration.addAnnotatedClass(OrderMenuItem.class);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}