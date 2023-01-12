package ru.education.crm.backend.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private long orderNumber;

    private String material;

    private String provider;

    private boolean isOriginal;

    private boolean isPaid;

    private String status;
}
