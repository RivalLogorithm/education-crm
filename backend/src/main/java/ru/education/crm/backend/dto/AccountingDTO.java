package ru.education.crm.backend.dto;

import lombok.Data;

@Data
public class AccountingDTO {

    private String invoiceCode;

    private String invoiceName;

    private String debitName;

    private String creditName;

    private String debit;

    private String credit;

    private String operation;

    private double value;

    private double additionalValue;
}
