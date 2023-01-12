package ru.education.crm.backend.dto;

import lombok.Data;

@Data
public class OrderDetailsDTO {

    private long orderNumber;

    private String material;

    private int count;

    private String details;

    private String provider;

    private String employee;

    private double paymentSum;

    private String status;

    private String storageStatus;
}
