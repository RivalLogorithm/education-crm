package ru.education.crm.backend.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
        String hashPassword = BCrypt.withDefaults().hashToString(12, employeeDTO.getPassword().toCharArray());
        employeeDTO.setPassword(hashPassword);
        Employee employee = new Employee(employeeDTO);
        employee.setHireDate(LocalDate.now());

        Optional<Department> department = departmentRepository.findById(employeeDTO.getDepartment());

        if (department.isPresent()) {
            employee.setDepartment(department.get());
            try {
                employee = employeeRepository.save(employee);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            log.info("Пользователь зарегистрирован");
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        } else {
            log.error("Ошибка при регистрации");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> employee = employeeRepository.findByEmail(employeeDTO.getEmail());

        if (employee.isPresent()) {
            BCrypt.Result result = BCrypt.verifyer().verify(employeeDTO.getPassword().toCharArray(), employee.get().getPassword());
            if (result.verified) {
                return new ResponseEntity<>(employee.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
