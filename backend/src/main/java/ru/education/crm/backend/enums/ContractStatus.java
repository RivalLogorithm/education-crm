package ru.education.crm.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContractStatus {

    CREATED(1L),
    READY_TO_PAY(2L),
    PAID(3L),
    CHECKED(4L),
    APPROVED(5L);

    private final long id;
}
