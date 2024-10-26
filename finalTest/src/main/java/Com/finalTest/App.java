package Com.finalTest;

import java.sql.Connection;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar finalTest-0.0.1-SNAPSHOT.jar <mode> [parameters]");
            return;
        }

        int mode = Integer.parseInt(args[0]);

        // Загрузка драйвера PostgreSQL
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver not found: " + e.getMessage());
            return;
        }

        // Подключение к базе данных
        Connection conn = NewDatabase.connect();
        NewDatabase.createTable();

        switch (mode) {
        case 1:
            // Режим создания записи
            if (args.length != 4) {
                System.out.println("Использование: java -jar finalTest-0.0.1-SNAPSHOT.jar 1 <ФИО> <дата_рождения> <пол>");
                return;
            }
            String fullName = args[1];
            LocalDate birthDate = LocalDate.parse(args[2]);
            String gender = args[3];

            Employee employee = new Employee(fullName, birthDate, gender);
            employee.saveToDatabase();
            System.out.println("Сотрудник добавлен. Возраст: " + employee.calculateAge() + " лет");
            break;

        case 2:
            // Вывод всех сотрудников
            AddEmployee.allEmployee();
            break;

        case 3:
            // Генерация сотрудников
        	long startTime = System.currentTimeMillis();
            EmployeeGenerator.generateAndSaveEmployees();
            long endTime = System.currentTimeMillis();
            System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
            break;

        case 4:
            // Выборка по критериям
            long startTime1 = System.currentTimeMillis();
            AddEmployee.retrieveMaleEmployeesWithF();
            long endTime1 = System.currentTimeMillis();
            System.out.println("Время выполнения: " + (endTime1 - startTime1) + " мс");
            break;

        case 5:
            long startTime11 = System.currentTimeMillis();
            NewDatabase.createIndex();
            long endTime11 = System.currentTimeMillis();
            System.out.println("Execution time for partial index creation: " + (endTime11 - startTime11) + " ms");
            break;

        case 6:
        	AddEmployee.deleteAllEmployees();
        	break;
        default:
            System.out.println("Неизвестный режим: " + mode);
            break;
    }
    }
}
