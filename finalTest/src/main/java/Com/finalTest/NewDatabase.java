package Com.finalTest;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NewDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "52797aa_";

    // Подключение к базе данных
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    // Метод для создания таблицы
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id SERIAL PRIMARY KEY, " +
                "full_name VARCHAR(255) NOT NULL, " +
                "birth_date DATE NOT NULL, " +
                "gender VARCHAR(10) NOT NULL" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'employees' created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create table: " + e.getMessage());
        }
    }
    public static void createIndex() {
        // SQL-запрос для создания частичного индекса
        String sql = "CREATE INDEX idx_male_f_names ON employees(full_name) WHERE gender = 'Male' AND full_name LIKE 'F%';";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            
            // Установка maintenance_work_mem для ускорения создания индекса
            stmt.execute("SET maintenance_work_mem = '512MB';");
            
            // Создание частичного индекса
            stmt.execute(sql);
            System.out.println("Partial index 'idx_male_f_names' created successfully.");

            // Сброс maintenance_work_mem к стандартному значению
            stmt.execute("RESET maintenance_work_mem;");

        } catch (SQLException e) {
            System.out.println("Failed to create partial index: " + e.getMessage());
        }
    }



}

