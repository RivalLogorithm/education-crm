package ru.education.crm.backend.dto;

import lombok.Data;

@Data
public class PayContractDTO {

    private long orderNumber;

    private long contractNumber;

    private double paymentSum;

    private double price;

    private double nds;

    private String provider;
}
