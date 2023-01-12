package ru.education.crm.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.education.crm.backend.dto.AccountingDTO;
import ru.education.crm.backend.entity.Accounting;
import ru.education.crm.backend.entity.Invoice;
import ru.education.crm.backend.repository.AccountingRepository;
import ru.education.crm.backend.repository.InvoiceRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/accounting")
@RequiredArgsConstructor
public class AccountingController {

    private final InvoiceRepository invoiceRepository;

    private final AccountingRepository accountingRepository;

    @PostMapping("/new_entity")
    public ResponseEntity<String> createAccountingEntity(@RequestBody AccountingDTO request) {
        Accounting debitEntity = new Accounting();
        Accounting creditEntity = new Accounting();

        invoiceRepository.findByCode(request.getDebit()).ifPresentOrElse(
                debitEntity::setInvoice,
                () -> {
                    Invoice invoice = new Invoice(request.getDebit(), request.getDebitName());
                    debitEntity.setInvoice(invoice);
                }
        );

        invoiceRepository.findByCode(request.getCredit()).ifPresentOrElse(
                creditEntity::setInvoice,
                () -> {
                    Invoice invoice = new Invoice(request.getCredit(), request.getCreditName());
                    creditEntity.setInvoice(invoice);
                }
        );

        if (request.getDebit().equals("10")) {
            debitEntity.setDebit(request.getValue());
        } else if (request.getDebit().equals("19")) {
            debitEntity.setDebit(request.getAdditionalValue());
        } else {
            debitEntity.setDebit(request.getValue() + request.getAdditionalValue());
        }
        debitEntity.setOperation(request.getOperation());

        creditEntity.setCredit(-request.getValue() - request.getAdditionalValue());
        creditEntity.setOperation(request.getOperation());

        accountingRepository.save(debitEntity);
        accountingRepository.save(creditEntity);

        return new ResponseEntity<>("Проводки выполнены", HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<AccountingDTO>> getEntities() {
        List<Accounting> accountingList = accountingRepository.findAll();

        List<AccountingDTO> response = new ArrayList<>();

        if (accountingList.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        accountingList.forEach(
                accounting -> {
                    AccountingDTO res = new AccountingDTO();
                    res.setInvoiceCode(accounting.getInvoice().getCode());
                    res.setInvoiceName(accounting.getInvoice().getName());
                    res.setValue(accounting.getCredit() != 0 ? accounting.getCredit() : accounting.getDebit());
                    res.setOperation(accounting.getOperation());
                    response.add(res);
                }
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
