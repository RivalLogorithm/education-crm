package ru.education.crm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.dto.OrderDTO;
import ru.education.crm.backend.dto.OrderDetailsDTO;
import ru.education.crm.backend.dto.StorageDTO;
import ru.education.crm.backend.entity.PurchaseOrder;
import ru.education.crm.backend.entity.Status;
import ru.education.crm.backend.entity.StorageOperation;
import ru.education.crm.backend.enums.ContractStatus;
import ru.education.crm.backend.repository.PurchaseOrderRepository;
import ru.education.crm.backend.repository.StatusRepository;
import ru.education.crm.backend.repository.StorageOperationsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/storage")
public class StorageController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private StorageOperationsRepository storageOperationsRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/get")
    private ResponseEntity<List<OrderDTO>> getStorageList() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        List<OrderDTO> response = new ArrayList<>();

        if (purchaseOrders.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        purchaseOrders.forEach(
                purchaseOrder -> {
                    OrderDTO order = new OrderDTO();
                    order.setOrderNumber(purchaseOrder.getOrderNumber());
                    order.setProvider(purchaseOrder.getProvider().getName());
                    Optional<StorageOperation> storageOperation = storageOperationsRepository.getStorageOperationByOrderNumber(purchaseOrder);
                    if (storageOperation.isPresent() && storageOperation.get().getStatus() != null) {
                        order.setStatus(storageOperation.get().getStatus().getName());
                    } else {
                        order.setStatus("-");
                    }
                    response.add(order);
                }
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{orderNumber}")
    private ResponseEntity<StorageDTO> getStorageDetails(@PathVariable Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);

        if (purchaseOrder.isPresent()) {
            StorageDTO response = new StorageDTO();
            response.setProvider(purchaseOrder.get().getProvider().getName());
            response.setDetails(purchaseOrder.get().getOrderDetails());
            response.setMaterial(purchaseOrder.get().getMaterial());
            response.setCount(purchaseOrder.get().getCount());
            Optional<StorageOperation> storageOperation = storageOperationsRepository.getStorageOperationByOrderNumber(purchaseOrder.get());
            if (storageOperation.isPresent()) {
                response.setStorageName(storageOperation.get().getArticle().getName());
                response.setStorageCount(storageOperation.get().getArticle().getCount());
                response.setDate(storageOperation.get().getDate());
            } else {
                response.setStorageName("-");
                response.setStorageCount(0);
                response.setDate(null);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/approve/{orderNumber}")
    private void approveStorage(@PathVariable Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);
        if (purchaseOrder.isPresent()) {
            Optional<StorageOperation> storageOperationOpt = storageOperationsRepository.getStorageOperationByOrderNumber(purchaseOrder.get());
            if (storageOperationOpt.isPresent()) {
                Optional<Status> status = statusRepository.findById(ContractStatus.CHECKED.getId());
                StorageOperation storageOperation = storageOperationOpt.get();
                storageOperation.setStatus(status.get());
                storageOperationsRepository.save(storageOperation);
            }
        }
    }
}
