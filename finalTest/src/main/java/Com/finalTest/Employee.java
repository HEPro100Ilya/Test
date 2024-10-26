package Com.finalTest;

import java.time.LocalDate;
import java.time.Period;

public class Employee {
    private String fullName;
    private LocalDate birthDate;
    private String gender;

    public Employee(String fullName, LocalDate birthDate, String gender) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public int calculateAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public void saveToDatabase() {
        AddEmployee.saveEmployee(this);
    }
}
