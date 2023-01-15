package ru.education.crm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.dto.AccountingDTO;
import ru.education.crm.backend.dto.OrderDTO;
import ru.education.crm.backend.entity.*;
import ru.education.crm.backend.enums.ContractStatus;
import ru.education.crm.backend.dto.PayContractDTO;
import ru.education.crm.backend.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AccountingRepository accountingRepository;

    @GetMapping("/get_orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        List<OrderDTO> responseOrders = new ArrayList<>();

        if (purchaseOrders.isEmpty()) {
            return new ResponseEntity<>(responseOrders, HttpStatus.NO_CONTENT);
        }

        purchaseOrders.forEach(purchaseOrder -> {
            OrderDTO order = new OrderDTO();
            order.setOrderNumber(purchaseOrder.getOrderNumber());
            if (contractRepository.findById(purchaseOrder.getOrderNumber()).isPresent()) {
                order.setOriginal(true);
            } else {
                order.setOriginal(false);
            }
            if (ContractStatus.PAID.getId() == purchaseOrder.getContract().getStatus().getStatusId()) {
                order.setPaid(true);
            } else {
                order.setPaid(false);
            }
            order.setStatus(purchaseOrder.getContract().getStatus().getName());
            responseOrders.add(order);
        });

        return new ResponseEntity<>(responseOrders, HttpStatus.OK);
    }

    @GetMapping("/get/{orderNumber}")
    public ResponseEntity<PayContractDTO> getPayment(@PathVariable Long orderNumber) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(orderNumber);
        PayContractDTO response = new PayContractDTO();

        if (purchaseOrder.isPresent()) {
            response.setOrderNumber(orderNumber);
            response.setContractNumber(purchaseOrder.get().getContract().getContractNumber());
            response.setProvider(purchaseOrder.get().getProvider().getName());
            Optional<Payment> payment = paymentRepository.findByContract(purchaseOrder.get().getContract());
            payment.ifPresent(value -> {
                response.setPaymentSum(value.getPaymentSum());
                if (purchaseOrder.get().getMaterial().equals("Материал 2") || purchaseOrder.get().getMaterial().equals("Материал 3")) {
                    response.setNds(value.getPaymentSum() / 1.2 * 0.2);
                    response.setPrice(value.getPaymentSum() / 1.2);
                } else {
                    response.setNds(0);
                    response.setPrice(value.getPaymentSum());
                }
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/pay/{contractNumber}")
    public ResponseEntity<Contract> payContract(@PathVariable Long contractNumber) {
        Optional<Contract> contract = contractRepository.findById(contractNumber);
        if (contract.isPresent()) {
            Contract currentContract = contract.get();
            currentContract.setStatus(statusRepository.findById(ContractStatus.PAID.getId()).get());
            currentContract = contractRepository.save(currentContract);
            return new ResponseEntity<>(currentContract, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
