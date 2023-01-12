package ru.education.crm.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.dto.OrderDTO;
import ru.education.crm.backend.dto.OrderDetailsDTO;
import ru.education.crm.backend.dto.PayContractDTO;
import ru.education.crm.backend.entity.*;
import ru.education.crm.backend.repository.*;
import ru.education.crm.backend.dto.PurchaseOrderDTO;
import ru.education.crm.backend.enums.ContractStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private StorageOperationsRepository storageOperationsRepository;

    @GetMapping
    private ResponseEntity<PurchaseOrder> getPurchaseOrder(@RequestBody Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);
        if (purchaseOrder.isPresent()) {
            return new ResponseEntity<>(purchaseOrder.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/new")
    public ResponseEntity<PayContractDTO> newPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = new PurchaseOrder(purchaseOrderDTO);

        Optional<Employee> employee = employeeRepository.findById(purchaseOrderDTO.getEmployeeId());
        Optional<Provider> provider = providerRepository.findById(purchaseOrderDTO.getProviderId());

        if (!employee.isPresent() || !provider.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            purchaseOrder.setEmployee(employee.get());
            purchaseOrder.setProvider(provider.get());

            PayContractDTO response = new PayContractDTO();

            Contract contract = createContract(provider.get(), purchaseOrder, response);
            purchaseOrder.setContract(contract);
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @PutMapping("/submit/{contractNumber}")
    private ResponseEntity<String> submitForPayment(@PathVariable Long contractNumber) {
        Optional<Contract> contract = contractRepository.findById(contractNumber);

        if (contract.isPresent()) {
            Status status = statusRepository.findById(ContractStatus.READY_TO_PAY.getId()).get();
            contract.get().setStatus(status);
            contractRepository.save(contract.get());
            return new ResponseEntity<>("Передано на оплату", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get_orders")
    private ResponseEntity<List<OrderDTO>> getOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        List<OrderDTO> ordersResponse = new ArrayList<>();

        if (purchaseOrders.isEmpty()) {
            return new ResponseEntity<>(ordersResponse, HttpStatus.NO_CONTENT);
        }

        purchaseOrders.forEach(
                purchaseOrder -> {
                    OrderDTO order = new OrderDTO();
                    order.setOrderNumber(purchaseOrder.getOrderNumber());
                    order.setMaterial(purchaseOrder.getMaterial());
                    order.setProvider(purchaseOrder.getProvider().getName());
                    ordersResponse.add(order);
                }
        );
        return new ResponseEntity<>(ordersResponse, HttpStatus.OK);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);

        OrderDetailsDTO orderDetails = new OrderDetailsDTO();
        if (purchaseOrder.isPresent()) {
            orderDetails.setOrderNumber(orderNumber);
            orderDetails.setMaterial(purchaseOrder.get().getMaterial());
            orderDetails.setCount(purchaseOrder.get().getCount());
            orderDetails.setDetails(purchaseOrder.get().getOrderDetails().isEmpty() ? "-" : purchaseOrder.get().getOrderDetails());
            orderDetails.setProvider(purchaseOrder.get().getProvider().getName());
            orderDetails.setEmployee(String.join(" ",
                            purchaseOrder.get().getEmployee().getLastName(),
                            purchaseOrder.get().getEmployee().getFirstName(),
                            purchaseOrder.get().getEmployee().getMiddleName(),
                            "<" + purchaseOrder.get().getEmployee().getEmail() + ">"
                    ));
            Optional<Payment> payment = paymentRepository.findByContract(purchaseOrder.get().getContract());
            payment.ifPresent(value -> orderDetails.setPaymentSum(value.getPaymentSum()));
            Optional<StorageOperation> storageOperation = storageOperationsRepository.getStorageOperationByOrderNumber(purchaseOrder.get());
            storageOperation.ifPresentOrElse(value -> orderDetails.setStorageStatus(value.getStatus().getName()),
                    () -> orderDetails.setStorageStatus("-"));
            orderDetails.setStatus(purchaseOrder.get().getContract().getStatus().getName());
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{orderNumber}")
    public void approveContract(@PathVariable Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);
        purchaseOrder.ifPresentOrElse((value) -> {
            Contract contract = value.getContract();
            Status status = statusRepository.findById(ContractStatus.APPROVED.getId()).get();
            contract.setStatus(status);
            contractRepository.save(contract);
        },
                () -> log.error("Не удалось подтвердить получение"));
    }

    private Contract createContract(Provider provider, PurchaseOrder purchaseOrder, PayContractDTO response) {
        Status status = statusRepository.findById(ContractStatus.CREATED.getId()).get();
        Contract contract = new Contract(status, provider);
        contractRepository.save(contract);
        response.setContractNumber(contract.getContractNumber());
        log.info("Контракт создан");
        createPayment(contract, purchaseOrder, response);
        return contract;
    }

    private void createPayment(Contract contract, PurchaseOrder purchaseOrder, PayContractDTO response) {
        double paymentSum = purchaseOrder.getCount() * purchaseOrder.getPrice();
        Payment payment = new Payment(paymentSum, contract);
        payment.setDate(LocalDate.now());
        paymentRepository.save(payment);
        response.setPaymentSum(paymentSum);
        log.info("Платежная квитанция создана");
    }
}
