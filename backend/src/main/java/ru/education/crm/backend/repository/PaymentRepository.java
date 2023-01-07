package ru.education.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.crm.backend.entity.Contract;
import ru.education.crm.backend.entity.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public Optional<Payment> findByContract(Contract contract);
}
