package U_5_Mongo;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Aggregates.*;

import java.util.Arrays;

import java.util.Map;
import java.util.function.Consumer;


public class MongoLMS {
    public static void main(String[] args) {
        MongoClient mongoClients = MongoClients.create("mongodb://192.168.99.100:27017");
        MongoDatabase mongoDatabase = mongoClients.getDatabase("mongodb");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("mongodbCol");

        System.out.println("запрос MongoDB для отображения всех данных из представленной таблицы");
        setMongodbDoc(mongoCollection);
        mongoCollection.find().forEach((Consumer<Document>) System.out::println);
        System.out.println("-------------------------------------------------------------");

        System.out.println("запрос MongoDB для отображения ФИО и даты рождения всех лиц из представленной таблицы");
        // Включить поле в поиск и показ
        Bson projectionsInc = Projections.include("fName");
        // Исключить поля
        Bson projectionsEx = Projections.exclude("_id","job_id","phone","email","id","salary");
        mongoCollection.find().projection(projectionsInc).projection(projectionsEx).forEach((Consumer<Document>) System.out::println);
        System.out.println("-------------------------------------------------------------");

        Bson sortAsc = Sorts.ascending("salary");
        Bson sortDesc = Sorts.descending("salary");
        System.out.println("запрос MongoDB для отображения всех работников сортирую их в порядке уменьшения заработной платы");
        mongoCollection.find().sort(sortAsc).forEach((Consumer<Document>) System.out::println);
        mongoCollection.find().sort(sortDesc).forEach((Consumer<Document>) System.out::println);
        System.out.println("-------------------------------------------------------------");

        System.out.println("запрос MongoDB для отображения средней зарплаты всех работников");
        /* aggregate - нужно использовать Массив List.of или Arrays.asList как фабрику с данными
        мето group группирует одинаковые значения, принимает id как указатель для Accumulators, который должен выполнить действия по id, если id будет как столбец(ключ)
        $salary в документе, то Accumulators.sum создает новую переменную fieldName: average value с значением expression: ......
        - id: "$salary" , значение expression может суммировать одинаковые строки, если указать 1, expression: 1
        указываем Accumulators.sum нужно суммировать все одинаковые строки по id: "$salary", то есть expression будет показывать кол-во одинаковых
        строк
        - id: "$salary", значение expression может суммировать числа в одинаковых строках, если указать expression: "$salary"
        указываем Accumulators.sum нужно суммировать все одинаковые числа по столбцу(ключу) "$salary"
        - id: null, группироваться одинаковые значения не будут, так как null - нет указателя столбца для группировки
        указываем Accumulators.sum по какому столбцу expression: ....  получить сумму или среднее значение Accumulators.avg
        */
        // считать кол-во одинаковых строк
        mongoCollection.aggregate(Arrays.asList(group("$salary", Accumulators.sum("average value",1)))).forEach((Consumer<Document>)System.out::println);
        System.out.println("-------------------------------------------------------------");
        // считать кол-во одинаковых строк и суммировать их одинаковые значения, не общее значение всех строк, а только одинаковые
        mongoCollection.aggregate(Arrays.asList(group("$salary", Accumulators.sum("average value","$salary")))).forEach((Consumer<Document>)System.out::println);
        System.out.println("-------------------------------------------------------------");
        // не считать одинаковые строки - указывается null, а посчитать общую сумму по стольцу(ключу) $salary
        mongoCollection.aggregate(Arrays.asList(group(null, Accumulators.sum("average value","$salary")))).forEach((Consumer<Document>)System.out::println);
        System.out.println("-------------------------------------------------------------");
        // а тут среднее значение по столбцу(ключу) $salary
        mongoCollection.aggregate(Arrays.asList(group(null, Accumulators.avg("average value","$salary")))).forEach((Consumer<Document>)System.out::println);
        System.out.println("-------------------------------------------------------------");

        System.out.println("MongoDB для отображения только имени и номера телефона сотрудников из представленной таблицы");

        Bson projectionsI = Projections.include("fName","phone");
        Bson projectionsE = Projections.exclude("_id","job_id","email","id","salary","DBirth","sName");
        mongoCollection.find().projection(projectionsI).projection(projectionsE).forEach((Consumer<Document>) System.out::println);

        mongoClients.close();
    }
    static void setMongodbDoc(MongoCollection<Document> mongoCollection){
        Document docMisha = new Document(Map.of(
                "id", new ObjectId(),"fName","Миша","sName","Булыгин","email","@mail.ru","phone",7926,"DBirth","1980-11-12","job_id","IT_Program","salary",44000.00
        ));
        Document docOlga = new Document(Map.of(
                "id", new ObjectId(),"fName","Ольга","sName","Рассомаха","email","@hot.ru","phone",7926,"DBirth","1981-10-23","job_id","IT_Program","salary",24000.00
        ));
        Document docPasha = new Document(Map.of(
                "id", new ObjectId(),"fName","Паша","sName","Бодрягин","email","@yandex.ru","phone",7985,"DBirth","1986-01-11","job_id","AD_VP","salary",44000.00
        ));
        Document docAnna = new Document(Map.of(
                "id", new ObjectId(),"fName","Анна","sName","Смирнова","email","@google.ru","phone",8499,"DBirth","1990-09-15","job_id","AD_VP","salary",17000.00
        ));
        Document docSasha = new Document(Map.of(
                "id", new ObjectId(),"fName","Саша","sName","Кулаженков","email","@google.ru","phone",8963,"DBirth","1979-07-11","job_id","AD_PRESS","salary",30000.00
        ));
        Document docDasha = new Document(Map.of(
                "id", new ObjectId(),"fName","Даша","sName","Гольц","email","@list.ru","phone",8999,"DBirth","1990-11-11","job_id","IT_PROG","salary",34000.00
        ));

        mongoCollection.insertOne(docMisha);
        mongoCollection.insertOne(docOlga);
        mongoCollection.insertOne(docPasha);
        mongoCollection.insertOne(docAnna);
        mongoCollection.insertOne(docSasha);
        mongoCollection.insertOne(docDasha);

    }
}
