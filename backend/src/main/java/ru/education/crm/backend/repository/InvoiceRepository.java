package ru.education.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.crm.backend.entity.Invoice;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public Optional<Invoice> findByCode(String code);
}
