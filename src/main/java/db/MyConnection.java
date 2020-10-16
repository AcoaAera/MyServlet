package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    private static String url = "jdbc:mysql://localhost:3306/Java?serverTimezone=Europe/Moscow";
    private static String username = "root";
    private static String pass = "root";

    public static Connection GetConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return DriverManager.getConnection(url, username, pass);
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            return null;
        }
    }
}
