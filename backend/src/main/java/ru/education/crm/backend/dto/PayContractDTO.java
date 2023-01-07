package ru.education.crm.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayContractDTO {

    private long contractNumber;

    private double paymentSum;
}
