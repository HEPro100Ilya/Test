package Com.finalTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class EmployeeGenerator {
    private static final String[] firstNames = {"Ivan", "Petr", "Alexey", "Dmitry", "Sergey", "Nikolay", "Ilya", "Oleg"};
    private static final String[] lastNames = {"Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov", "Popov","Sokolov"};
    private static final String[] fLastNames = {"Fedorov", "Filatov", "Fomin", "Frolov", "Fadeev"};
    private static final String[] patronymics = {"Ivanovich", "Petrovich", "Alexeyevich", "Dmitrievich", "Sergeyevich"};
    private static final String[] genders = {"Male", "Female"};

    // Метод для генерации одного случайного сотрудника без фамилии на "F"
    public static Employee generateRandomEmployeeWithoutF() {
        Random rand = new Random();
        String gender = genders[rand.nextInt(genders.length)];
        String firstName = firstNames[rand.nextInt(firstNames.length)];
        String lastName;

        // Генерация фамилии, которая не начинается на "F"
        do {
            lastName = lastNames[rand.nextInt(lastNames.length)];
        } while (lastName.startsWith("F"));

        String patronymic = patronymics[rand.nextInt(patronymics.length)];
        String fullName = lastName + " " + firstName + " " + patronymic;

        // Генерация случайной даты рождения
        int year = 1960 + rand.nextInt(40);
        int month = 1 + rand.nextInt(12);
        int day = 1 + rand.nextInt(28);

        LocalDate birthDate = LocalDate.of(year, month, day);

        return new Employee(fullName, birthDate, gender);
    }

    // Метод для генерации сотрудников с фамилией на "F" и полом "Male"
    public static Employee generateEmployeeWithF() {
        Random rand = new Random();
        String firstName = firstNames[rand.nextInt(firstNames.length)];
        String lastName = fLastNames[rand.nextInt(fLastNames.length)]; // Выбираем случайную фамилию на "F"
        String patronymic = patronymics[rand.nextInt(patronymics.length)];
        String fullName = lastName + " " + firstName + " " + patronymic;

        // Генерация случайной даты рождения
        int year = 1970 + rand.nextInt(40);
        int month = 1 + rand.nextInt(12);
        int day = 1 + rand.nextInt(28);

        LocalDate birthDate = LocalDate.of(year, month, day);

        return new Employee(fullName, birthDate, "Male");
    }

    // Генерация и сохранение сотрудников
    public static void generateAndSaveEmployees() {
        Set<Employee> employees = new HashSet<>();

        // Генерация 100 сотрудников с фамилией на "F" и полом "Male"
        for (int i = 0; i < 100; i++) {
            employees.add(generateEmployeeWithF());
        }

        // Генерация оставшихся сотрудников без фамилий на "F"
        while (employees.size() < 1000000) {
            employees.add(generateRandomEmployeeWithoutF());
        }

        // Сохранение уникальных сотрудников в базу данных
        AddEmployee.saveEmployeesBatch(new ArrayList<>(employees));
    }
}
