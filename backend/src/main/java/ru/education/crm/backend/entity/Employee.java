package ru.education.crm.backend.entity;

import lombok.Getter;
import lombok.Setter;
import ru.education.crm.backend.dto.EmployeeDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Setter
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee(EmployeeDTO employeeDTO) {
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
        this.middleName = employeeDTO.getMiddleName();
        this.email = employeeDTO.getEmail();
        this.password = employeeDTO.getPassword();
        this.birthDate = employeeDTO.getBirthDate();
    }

    public Employee() {
    }
}
