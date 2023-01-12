package ru.education.crm.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StorageDTO {

    private String material;

    private String storageName;

    private int count;

    private int storageCount;

    private LocalDate date;

    private String provider;

    private String details;
}
