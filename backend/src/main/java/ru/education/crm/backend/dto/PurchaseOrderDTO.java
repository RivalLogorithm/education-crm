package ru.education.crm.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderDTO {

    private String material;

    private int count;

    private double price;

    private String orderDetails;

    private long providerId;

    private long employeeId;
}
