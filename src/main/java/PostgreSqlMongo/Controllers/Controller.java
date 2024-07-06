package PostgreSqlMongo.Controllers;

import PostgreSqlMongo.Objects.Contacts;
import PostgreSqlMongo.Objects.Users;
import PostgreSqlMongo.ServicesObjects.LogsService;
import com.mongodb.client.MongoCollection;
import jakarta.persistence.EntityManager;
import org.bson.Document;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

public class Controller implements Closeable {
    LogsService logsService;
    EntityController entityController;
    public Controller() {
        logsService = new LogsService();
        entityController = new EntityController();
    }
    public void addContact(Users user1, Contacts contacts, Map<String, Object> meta){
        contacts.setUser(user1);

        entityController.entityManager.getTransaction().begin();
        entityController.entityManager.persist(contacts);
        entityController.entityManager.getTransaction().commit();

        meta.put("action","createContact");
        meta.put("newContactId", contacts.getIds());

        logsService.add(meta);
    }
    @Override
    public void close() throws IOException {
        entityController.close();
        logsService.close();
    }
}
