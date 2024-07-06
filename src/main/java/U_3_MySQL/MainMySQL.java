package U_3_MySQL;

import java.sql.*;
import java.util.Scanner;
public class MainMySQL {
    static String connectionDB = "jdbc:mariadb://192.168.99.100:3306/db";
    static String userDB = "user";
    static String passDB = "pass";
    public static void main(String[] args) throws SQLException {

        if(getYN()){
            createTableSQL();
            setDataToTable();
        } else {
            getTableForAll();

        }
    }
    static boolean getYN(){
        System.out.println("Заполнить / обновить БД (Да или Нет)");
        Scanner scanner = new Scanner(System.in);
        String userScanner = scanner.nextLine();
        return getYesOrNo(userScanner);
    }
    static boolean getYesOrNo(String userScanner){
        return userScanner.equals("Да") || userScanner.equals("да");
    }
    static void createTableSQL() throws SQLException {
        String sqlTable = "create table Employee (\n" +
                "employee_id int(20),\n" +
                "first_name varchar(20) not null,\n" +
                "second_name varchar(20) not null,\n" +
                "email varchar(20) not null,\n" +
                "phone_number int(20) not null,\n" +
                "hire_date date not null,\n" +
                "job_id varchar(20) not null,\n" +
                "salary float(20,2) not null,\n" +
                "commission_pct float(20,2) not null,\n" +
                "primary key(employee_id)"+
                ")";
        Connection connection = DriverManager.getConnection(connectionDB,userDB,passDB);
        connection.createStatement().execute(sqlTable);
        System.out.println("Connection is successful");
    }
    static void setDataToTable(){
        String toSqlInsert =
            "insert into Employee(employee_id,first_name,second_name,email,phone_number,hire_date,job_id,salary,commission_pct) values"+
            "(100,'Миша','Булыгин','treemail',7926,'2000-11-12','IT_PROG',44000.00,0.00),(101,'Ольга','Рассомаха','homemail',8963,'2022-02-22','IT_PROG',24000.00,0.00)," +
            "(102,'Паша','Бодрягин','treyyandex',7985,'2000-04-18','AD_VP',44000.00,0.00),(103,'Анна','Смирнова','homegoogle',8499,'2023-03-03','AD_VP',17000.00,0.00)," +
            "(104,'Саша','Кулаженков','treegoogle',7926,'2022-01-12','AD_PRESS',30000.00,0.00),(105,'Даша','Гольц','hollslist',+7926,'2020-05-25','IT_PROG',34000.00,0.00)";
        try(Connection connection = DriverManager.getConnection(connectionDB,userDB,passDB)){
            connection.createStatement().execute(toSqlInsert);
            System.out.println("База данных заполнена");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    static void getTableForAll() throws SQLException {
        String tableSql = "select employee_id AS ID,first_name AS Имя,second_name AS Фамилия,email AS 'Эл.почта',phone_number AS 'Номер телефона',"+
        "hire_date AS 'Дата приема на работу',job_id AS Специализация,salary AS Зарплата from Employee";

        Connection connection = DriverManager.getConnection(connectionDB,userDB,passDB);
        System.out.println("Получаем Имена и Фамилии с буквами Б или б или С или с");
        getBAndC(connection,tableSql);
        System.out.println();
        System.out.println("Получаем определенную дату приема на работу");
        getDateHire(connection,tableSql);
        System.out.println();
        System.out.println("Получаем макс и мин зарплату");
        getMaxSalary(connection);
        getMinSalary(connection);
        System.out.println();
        System.out.println("Получаем четные записи");
        getEvenRecord(connection);
        System.out.println();
        System.out.println("Получаем нечетные записи");
        getOddRecord(connection);
    }
    static void viewTable(ResultSet resultSet) throws SQLException {
        System.out.println(
                resultSet.getInt("ID")+" "+
                resultSet.getString("Имя")+" "+
                resultSet.getString("Фамилия")+" "+
                resultSet.getString("Эл.почта")+" "+
                resultSet.getInt("Номер телефона")+" "+
                resultSet.getDate("Дата приема на работу")+" "+
                resultSet.getString("Специализация")+" "+
                resultSet.getFloat("Зарплата")
        );
    }
    static void getBAndC(Connection connection, String tableSql) throws SQLException {
        String bc = tableSql+" " +
                "where first_name like '%б%' or first_name like '%Б%' or " +
                      "first_name like '%c%' or first_name like '%С%' or " +
                      "second_name like '%б%' or second_name like '%Б%' or " +
                      "second_name like '%с%' or second_name like '%C%'";
        ResultSet resultSet = connection.createStatement().executeQuery(bc);
        while (resultSet.next()){
            viewTable(resultSet);
        }
    }
    static void getDateHire(Connection connection, String tableSql) throws SQLException {
        String dateHire = tableSql+" where hire_date like '%2022%'";
        ResultSet resultSet = connection.createStatement().executeQuery(dateHire);
        while (resultSet.next()){
            viewTable(resultSet);
        }
    }
    static void getMaxSalary(Connection connection) throws SQLException {
        String maxSalary = "select employee_id AS ID,first_name AS Имя,second_name AS Фамилия,email AS 'Эл.почта',phone_number AS 'Номер телефона'," +
                "hire_date AS 'Дата приема на работу',job_id AS Специализация,salary AS Зарплата from Employee where salary in (select max(salary) from Employee)";
        ResultSet resultSetMax = connection.createStatement().executeQuery(maxSalary);
        while (resultSetMax.next()){
            viewTable(resultSetMax);
        }
    }
    static void getMinSalary(Connection connection) throws SQLException {
        String minSalary = "select employee_id AS ID,first_name AS Имя,second_name AS Фамилия,email AS 'Эл.почта',phone_number AS 'Номер телефона'," +
                "hire_date AS 'Дата приема на работу',job_id AS Специализация,salary AS Зарплата from Employee where salary in (select min(salary) from Employee)";
        ResultSet resultSetMin = connection.createStatement().executeQuery(minSalary);
        while (resultSetMin.next()){
            viewTable(resultSetMin);
        }
    }
    static void getEvenRecord(Connection connection) throws SQLException {
        String evenNumbers = "select employee_id AS ID,first_name AS Имя,second_name AS Фамилия,email AS 'Эл.почта',phone_number AS 'Номер телефона'," +
                "hire_date AS 'Дата приема на работу',job_id AS Специализация,salary AS Зарплата from Employee where employee_id in (select employee_id from Employee where employee_id % 2 = 0)";
        ResultSet resultSetMin = connection.createStatement().executeQuery(evenNumbers);
        while (resultSetMin.next()){
            viewTable(resultSetMin);
        }
    }
    static void getOddRecord(Connection connection) throws SQLException {
        String oddNumbers = "select employee_id AS ID,first_name AS Имя,second_name AS Фамилия,email AS 'Эл.почта',phone_number AS 'Номер телефона'," +
                "hire_date AS 'Дата приема на работу',job_id AS Специализация,salary AS Зарплата from Employee where employee_id in (select employee_id from Employee where employee_id % 2 != 0)";
        ResultSet resultSetMin = connection.createStatement().executeQuery(oddNumbers);
        while (resultSetMin.next()){
            viewTable(resultSetMin);
        }
    }
}
