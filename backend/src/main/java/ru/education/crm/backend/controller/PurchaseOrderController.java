package ru.education.crm.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.entity.*;
import ru.education.crm.backend.repository.*;
import ru.education.crm.backend.dto.PurchaseOrderDTO;
import ru.education.crm.backend.enums.ContractStatus;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/purchase_order")
public class PurchaseOrderController {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping
    private ResponseEntity<PurchaseOrder> getPurchaseOrder(@RequestBody Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);
        if (purchaseOrder.isPresent()) {
            return new ResponseEntity<>(purchaseOrder.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/new")
    public ResponseEntity<PurchaseOrder> newPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = new PurchaseOrder(purchaseOrderDTO);

        Optional<Employee> employee = employeeRepository.findById(purchaseOrderDTO.getEmployeeId());
        Optional<Provider> provider = providerRepository.findById(purchaseOrderDTO.getProviderId());

        if (!employee.isPresent() || !provider.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            purchaseOrder.setEmployee(employee.get());
            purchaseOrder.setProvider(provider.get());

            createContract(provider.get(), purchaseOrder);

            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
            return new ResponseEntity<>(purchaseOrder, HttpStatus.CREATED);
        }
    }

    private void createContract(Provider provider, PurchaseOrder purchaseOrder) {
        Status status = statusRepository.findById(ContractStatus.CREATED.getId()).get();
        Contract contract = new Contract(status, provider);
        contractRepository.save(contract);
        log.info("Контракт создан");
        createPayment(contract, purchaseOrder);
    }

    private void createPayment(Contract contract, PurchaseOrder purchaseOrder) {
        double paymentSum = purchaseOrder.getCount() * purchaseOrder.getPrice();
        Payment payment = new Payment(paymentSum, contract);
        payment.setDate(LocalDate.now());
        paymentRepository.save(payment);
        log.info("Платежная квитанция создана");
    }
}
