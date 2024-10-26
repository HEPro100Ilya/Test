package Com.finalTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class AddEmployee {
    
    // Метод для добавления одного сотрудника в базу данных
    public static void saveEmployee(Employee employee) {
        String sql = "INSERT INTO employees(full_name, birth_date, gender) VALUES (?, ?, ?)";

        try (Connection conn = NewDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getFullName());
            pstmt.setDate(2, java.sql.Date.valueOf(employee.getBirthDate()));
            pstmt.setString(3, employee.getGender());
            pstmt.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add employee: " + e.getMessage());
        }
    }

    // Метод для пакетной отправки сотрудников в БД
    public static void saveEmployeesBatch(List<Employee> employees) {
        String sql = "INSERT INTO employees(full_name, birth_date, gender) VALUES (?, ?, ?)";

        try (Connection conn = NewDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Employee employee : employees) {
                pstmt.setString(1, employee.getFullName());
                pstmt.setDate(2, java.sql.Date.valueOf(employee.getBirthDate()));
                pstmt.setString(3, employee.getGender());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Batch of employees added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add batch of employees: " + e.getMessage());
        }
    }

    // Метод для выборки сотрудников по критериям
    public static void retrieveMaleEmployeesWithF() {
        String sql = "SELECT full_name, birth_date, gender FROM employees WHERE gender = 'Male' AND full_name LIKE 'F%' ORDER BY full_name";

        try (Connection conn = NewDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("List of male employees with surname starting with 'F':");
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
                String gender = rs.getString("gender");
                int age = Period.between(birthDate, LocalDate.now()).getYears();

                System.out.printf("Full Name: %s, Birth Date: %s, Gender: %s, Age: %d years%n",
                        fullName, birthDate, gender, age);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve employees: " + e.getMessage());
        }
    }
    
    public static void allEmployee() {
        String sql = "SELECT full_name, birth_date, gender FROM employees ORDER BY full_name";

        try (Connection conn = NewDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("List of all employees:");
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
                String gender = rs.getString("gender");
                int age = Period.between(birthDate, LocalDate.now()).getYears();

                System.out.printf("Full Name: %s, Birth Date: %s, Gender: %s, Age: %d years%n",
                        fullName, birthDate, gender, age);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve employees: " + e.getMessage());
        }
    }
    public static void deleteAllEmployees() {
        String sql = "DELETE FROM employees";

        try (Connection conn = NewDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int affectedRows = pstmt.executeUpdate();
            System.out.println("All employees deleted successfully. Number of rows affected: " + affectedRows);
        } catch (SQLException e) {
            System.out.println("Failed to delete employees: " + e.getMessage());
        }
    }

}
