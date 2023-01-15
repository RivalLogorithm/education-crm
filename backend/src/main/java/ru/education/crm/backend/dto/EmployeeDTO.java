package ru.education.crm.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String password;

    private LocalDate birthDate;

    private long department;
}
