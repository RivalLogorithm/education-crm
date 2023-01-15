package ru.education.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.crm.backend.entity.PurchaseOrder;
import ru.education.crm.backend.entity.StorageOperation;

import java.util.Optional;

@Repository
public interface StorageOperationsRepository extends JpaRepository<StorageOperation, Long> {

    public Optional<StorageOperation> getStorageOperationByOrderNumber(PurchaseOrder purchaseOrder);
}
