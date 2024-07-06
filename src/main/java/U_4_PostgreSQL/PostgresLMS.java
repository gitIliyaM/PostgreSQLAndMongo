package U_4_PostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresLMS {
    static String sqlTables = "create table if not exists \"Employee\"(\n" +
            "employee_id integer,\n" +
            "first_name varchar(20) not null,\n" +
            "second_name varchar(20) not null,\n" +
            "email varchar(20) not null,\n" +
            "phone_number integer not null,\n" +
            "hire_date date not null,\n" +
            "job_id varchar(20) not null,\n" +
            "salary float not null,\n" +
            "commission_pct float not null,\n" +
            "primary key(employee_id)\n"+
            ");";
    static String pSql = "jdbc:postgresql://192.168.99.100:5432/dbp";
    static String user = "user";
    static String pass = "pass";

    public static void main(String[] args) throws SQLException {
        System.out.println("Это объектно-реляциооная СУБД в ней сохраняется модель из реляционной СУБД и дополняется из объектной СУБД, где данные " +
                "представляются в видео объектов, как у ООП программировании");
        System.out.println("Партиционирование это разделение одной таблицы на мелкие");
        Connection connection = DriverManager.getConnection(pSql,user,pass);
        //connection.createStatement().execute(sqlTables);
        System.out.println("Connection is successful");
        //setDataToTable();
        getTableForAll();
    }
    static void setDataToTable(){
        String toSqlInsert =
                "insert into \"Employee\"(employee_id,first_name,second_name,email,phone_number,hire_date,job_id,salary,commission_pct) values"+
                        "(100,'Миша','Булыгин','treemail',7926,'1980-11-12','IT_PROG',44000.00,0.00),(101,'Ольга','Рассомаха','homemail',8963,'1985-02-22','IT_PROG',24000.00,0.00)," +
                        "(102,'Паша','Бодрягин','treyyandex',7985,'1983-04-18','AD_VP',44000.00,0.00),(103,'Анна','Смирнова','homegoogle',8499,'1985-03-03','AD_VP',17000.00,0.00)," +
                        "(104,'Саша','Кулаженков','treegoogle',7926,'1990-01-12','AD_PRESS',30000.00,0.00),(105,'Даша','Гольц','hollslist',+7926,'1990-05-25','IT_PROG',34000.00,0.00)";
        try(Connection connection = DriverManager.getConnection(pSql,user,pass)){
            connection.createStatement().execute(toSqlInsert);
            System.out.println("База данных заполнена");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    static void getTableForAll() throws SQLException {
        String tableSql = "select employee_id AS \"Employee ID\",first_name AS \"Имя\",second_name AS \"Фамилия\",email AS \"Эл.почта\",phone_number AS \"Номер телефона\","+
                "hire_date AS \"Дата рождения\",job_id AS \"Специализация\",salary AS \"Зарплата\" from \"Employee\"";
        String descendingOrder = " order by \"Employee ID\" desc";

        Connection connection = DriverManager.getConnection(pSql,user,pass);
        ResultSet resultSet = connection.createStatement().executeQuery(tableSql);
        System.out.println("Получаем Имена и Employee ID");
        getNameEmployee(resultSet);
        System.out.println();

        System.out.println("Получаем Фамилии и Даты рождения");
        ResultSet resultSet1 = connection.createStatement().executeQuery(tableSql);
        getSecondNameBDate(resultSet1);
        System.out.println();

        System.out.println("Получаем Имена, Фамилии и Employee ID в порядке убывания");
        ResultSet resultSetDesc = connection.createStatement().executeQuery(tableSql+descendingOrder);
        getDescendingOrder(resultSetDesc);
        System.out.println();

    }
    static void getNameEmployee(ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            System.out.println(
                    resultSet.getInt("Employee ID")+" "+
                    resultSet.getString("Имя"));
        }
    }
    static void getSecondNameBDate(ResultSet resultSet) throws SQLException{
        while (resultSet.next()){
            System.out.println(
                    resultSet.getString("Фамилия")+" "+
                    resultSet.getDate("Дата рождения")
            );
        }
    }
    static void getDescendingOrder(ResultSet resultSet) throws SQLException{
        while (resultSet.next()){
            System.out.println(
                    resultSet.getInt("Employee ID")+" "+
                    resultSet.getString("Имя")+ " "+
                    resultSet.getString("Фамилия")
            );
        }
    }
}
