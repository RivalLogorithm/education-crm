package ru.education.crm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.enums.ContractStatus;
import ru.education.crm.backend.dto.PayContractDTO;
import ru.education.crm.backend.entity.Contract;
import ru.education.crm.backend.entity.Payment;
import ru.education.crm.backend.repository.ContractRepository;
import ru.education.crm.backend.repository.PaymentRepository;
import ru.education.crm.backend.repository.StatusRepository;

import java.util.Optional;

@RestController
@RequestMapping("api/payment")
public class AccountantController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/get")
    public ResponseEntity<PayContractDTO> getPayment(@RequestBody PayContractDTO payContractDTO) {
        Optional<Contract> contract = contractRepository.findById(payContractDTO.getContractNumber());
        if (contract.isPresent()) {
            Optional<Payment> payment = paymentRepository.findByContract(contract.get());
            if (payment.isPresent()) {
                PayContractDTO response = new PayContractDTO();
                response.setPaymentSum(payment.get().getPaymentSum());
                response.setContractNumber(payContractDTO.getContractNumber());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/pay")
    public ResponseEntity<Contract> payContract(@RequestBody PayContractDTO payContractDTO) {
        Optional<Contract> contract = contractRepository.findById(payContractDTO.getContractNumber());
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
