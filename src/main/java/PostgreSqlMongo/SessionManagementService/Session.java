package PostgreSqlMongo.SessionManagementService;

import PostgreSqlMongo.Objects.Contacts;
import PostgreSqlMongo.Objects.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class Session {
    private static SessionFactory sessionFactory;

    private Session(){}

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try {
                sessionFactory = new Configuration().buildSessionFactory();
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Users.class);
                configuration.addAnnotatedClass(Contacts.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return sessionFactory;
    }

}
