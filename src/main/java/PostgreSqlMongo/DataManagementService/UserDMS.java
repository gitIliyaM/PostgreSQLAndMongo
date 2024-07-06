package PostgreSqlMongo.DataManagementService;

import PostgreSqlMongo.Objects.Users;
import PostgreSqlMongo.SessionManagementService.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDMS {
    public Users findById(int id) {return Session.getSessionFactory().openSession().get(Users.class,id);}
    public void save(Users user){
        var session = Session.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
    public void update(Users user){
        var session = Session.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }
    public void delete(Users user){
        var session = Session.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }
    public static List<Users> findAll(){
        return (List<Users>) Session.getSessionFactory().openSession().createQuery("From User").list();
    }
}
