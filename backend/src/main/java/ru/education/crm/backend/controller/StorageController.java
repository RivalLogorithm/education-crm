package ru.education.crm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.education.crm.backend.entity.PurchaseOrder;
import ru.education.crm.backend.entity.Storage;
import ru.education.crm.backend.repository.PurchaseOrderRepository;
import ru.education.crm.backend.repository.StorageOperationsRepository;
import ru.education.crm.backend.entity.StorageOperation;

import java.util.Optional;

@RestController
@RequestMapping("api/storage")
public class StorageController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private StorageOperationsRepository storageOperationsRepository;

    @GetMapping("/get")
    private ResponseEntity<Storage> getStorageByOrderNumber(@RequestBody Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);
        if (purchaseOrder.isPresent()) {
            Optional<StorageOperation> storageOperation = storageOperationsRepository.getStorageOperationByOrderNumber(purchaseOrder.get());
            if (storageOperation.isPresent()) {
                Storage storage = storageOperation.get().getArticle();
                if (storage != null) {
                    return new ResponseEntity<>(storage, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
