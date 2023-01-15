package ru.education.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.crm.backend.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
