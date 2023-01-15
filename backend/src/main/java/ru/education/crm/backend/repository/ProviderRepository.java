package ru.education.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.crm.backend.entity.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
