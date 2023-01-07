package ru.education.crm.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.dto.EmployeeDTO;
import ru.education.crm.backend.entity.Department;
import ru.education.crm.backend.entity.Employee;
import ru.education.crm.backend.repository.DepartmentRepository;
import ru.education.crm.backend.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        employee.setHireDate(LocalDate.now());

        Optional<Department> department = departmentRepository.findById(employeeDTO.getDepartment());

        if (department.isPresent()) {
            employee.setDepartment(department.get());
            employee = employeeRepository.save(employee);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
