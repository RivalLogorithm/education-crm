package ru.education.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.crm.backend.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
