package PostgreSqlMongo.ServicesObjects;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

public class LogsService implements Closeable {
    private final MongoCollection<Document> mongodbCollection;
    private final MongoClient client;

    public LogsService() {
        client = MongoClients.create("mongodb://192.168.99.100:27017");
        MongoDatabase database = client.getDatabase("Mongodb");
        mongodbCollection = database.getCollection("CollectionDB");
    }
    public void add (Map<String ,Object> data){
        var todoDocument = new Document(data);
        mongodbCollection.insertOne(todoDocument);

    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}

