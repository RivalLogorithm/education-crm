package ru.education.crm.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContractStatus {

    CREATED(1L),
    PAID(2L);

    private final long id;
}
