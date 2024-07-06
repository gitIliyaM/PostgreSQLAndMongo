package PostgreSqlMongo.Controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Closeable;
import java.io.IOException;

public class EntityController implements Closeable {
    private final EntityManagerFactory entityManagerFactory;
    public final EntityManager entityManager;
    public EntityController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("MainSql");
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println(entityManager);
    }

    @Override
    public void close() throws IOException {
        entityManager.close();
        entityManagerFactory.close();
    }
}
