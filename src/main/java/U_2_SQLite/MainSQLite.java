package U_2_SQLite;

import java.sql.*;
import java.util.Scanner;

public class MainSQLite {
    public static void main(String[] args) throws SQLException {
        if(connectYesNo()){
           insertToDB();
        } else {
            selectFromDB();
        }

    }
    // создать / обновить / получить БД (Да или Нет)
    static boolean connectYesNo(){
        System.out.println("Заполнить / обновить БД (Да или Нет)");
        Scanner scanner = new Scanner(System.in);
        String userScanner = scanner.nextLine();
        return getYesNo(userScanner);
    }
    // проверка Да или Нет
    static boolean getYesNo(String userScanner){
        return userScanner.equals("Да") || userScanner.equals("да");
    }
    // создаем: соединениес Базой Данных, таблицу БД
    static Connection connection(){
        String connectionDB = "jdbc:sqlite:D:\\Java\\JavaSynergy\\Основы синтаксиса Java\\Базы Данных\\DB_database\\sqlitedb.db";
        String sqlDB = "CREATE table if not exists \"Employs\"( \n" +
                "employs_id integer primary key,\n" +
                "first_name varchar(20) not null,\n" +
                "second_name varchar(20) not null\n" +
                ");";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionDB);
            connection.createStatement().execute(sqlDB);

            /*Statement statement = connection.createStatement();
            statement.execute(sqlDB);*/
            System.out.println("Connection is successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    // Вставляем / заполняем таблицу данными
    static void insertToDB() throws SQLException {
        int id = 100;
        String insertToDB = "insert into \"Employs\"(employs_id,first_name,second_name) values (?,?,?)";
        // создаем безопасное соединение try, но его не надо закрывать в конце, оно само закроется
        try(Connection conn = connection();
            PreparedStatement stmt = conn.prepareStatement(insertToDB);){
            System.out.println("Необходимо заполнить базу данных");
            System.out.println("Остановиться - напишите любую цифру, продолжить любую букву");
            Scanner scanner;
            //String updateId = "insert into \"Employs\"(employs_id) values (100)";
            do {
                /*
                можно вне цикла запустить сканер, вставить в таблицу 100,имя,фамилия. внутри цикла сделать обновление таблицы
                указать, чтобы обновление было больше значения 100.
                Fdnj
                 */
                // update Employs set first_name=?,second_name=? where employs_id>100;

                scanner = new Scanner(System.in);
                System.out.println("напишите имя");
                String nameF = scanner.nextLine();
                System.out.println("напишите фамилию");
                String nameL = scanner.nextLine();

                /* Можно так, но тогда нужно переделать insertToDB, в нее вставлять переменные от Сканнера
                conn.createStatement().execute(insertToDB);
                или
                Statement statement = conn.createStatement();
                statement.execute(insertToDB);*/

                stmt.setInt(1,id++);
                stmt.setString(2, nameF);
                stmt.setString(3, nameL);
                stmt.executeUpdate();
            } while (!scanner.hasNextInt());
        }
    }
    static void selectFromDB() throws SQLException {
        getNames();
        getUpperCase();
        getEmploysId();
        getFirst3Word();
        getFirst5Id();
    }
    static void getNames() throws SQLException {
        String selectNameFL = "select first_name AS Имя,second_name AS Фамилия from \"Employs\"";
        // создаем безопасное соединение без try, но его закрываем в конце
        Connection connection = connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectNameFL);
        while (resultSet.next()){
            System.out.println(resultSet.getString("Имя")+" "+
                               resultSet.getString("Фамилия"));
        }
        connection.close();
    }
    static void getUpperCase() throws SQLException {
        String selectNameFL = "select first_name AS Имя,second_name AS Фамилия from \"Employs\"";
        // создаем безопасное соединение без try, но его закрываем в конце
        Connection connection = connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectNameFL);
        while (resultSet.next()){
            System.out.println(resultSet.getString("Имя").toUpperCase()+" "+
                    resultSet.getString("Фамилия").toUpperCase());
        }
        connection.close();
    }
    static void getEmploysId() throws SQLException {
        String selectNameFL = "select employs_id AS id,first_name AS Имя,second_name AS Фамилия from \"Employs\"";
        // создаем безопасное соединение без try, но его закрываем в конце
        Connection connection = connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectNameFL);
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id") +" "+
                    resultSet.getString("Имя").toUpperCase()+" "+
                    resultSet.getString("Фамилия").toUpperCase());
        }
        connection.close();
    }
    static void getFirst3Word() throws SQLException {
        String selectNameFL = "select first_name AS Имя from \"Employs\"";
        // создаем безопасное соединение без try, но его закрываем в конце
        Connection connection = connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectNameFL);
        while (resultSet.next()){
            System.out.println(resultSet.getString("Имя").substring(0,3));
        }
        connection.close();
    }
    static void getFirst5Id() throws SQLException {
        String selectNameFL = "select employs_id AS id,first_name AS Имя,second_name AS Фамилия from \"Employs\" where employs_id<105";
        // создаем безопасное соединение без try, но его закрываем в конце
        Connection connection = connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectNameFL);
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id") +" "+
                    resultSet.getString("Имя").toUpperCase()+" "+
                    resultSet.getString("Фамилия").toUpperCase());
        }
        connection.close();
    }
}