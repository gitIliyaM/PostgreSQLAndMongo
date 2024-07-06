package PostgreSqlMongo;


import PostgreSqlMongo.Controllers.Controller;
import PostgreSqlMongo.Objects.Contacts;
import PostgreSqlMongo.Objects.Users;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.bson.Document;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainDB {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();

        Users user1 = new Users();
        user1.setIds(1);
        user1.setName("Eric");

        Contacts contacts =new Contacts();
        contacts.setUser(user1);
        contacts.setContactName("Eric Air i fik");
        contacts.setPhone("+79265935091");
        contacts.setEmail("79265935091@mail.ru");

        Map<String, Object> meta = new HashMap<>();
        meta.put("traceID","qwerty321Qwert543");
        meta.put("userId",1);
        meta.put("time",new Date().getTime());

        controller.addContact(user1, contacts, meta);
        controller.close();
    }

    /*
    подключает все аннотации в классе Users
        <dependency>
            <groupId>javax.persistence</groupId>
            <artifactId>javax.persistence-api</artifactId>
            <version>2.2</version>
            <scope>compile</scope>
        </dependency>
    подключает hibernate в классе UserDMS
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.2.3.Final</version>
            <scope>compile</scope>
        </dependency>
   импортирует
   import javax.persistence.EntityManager;
   import javax.persistence.Persistence;
        <dependency>
            <groupId>javax.persistence</groupId>
            <artifactId>javax.persistence-api</artifactId>
            <version>2.2</version>
            <scope>compile</scope>
        </dependency>
    */
}
