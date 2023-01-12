package ru.education.crm.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "invoice")
@Data
public class Invoice {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public Invoice(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Invoice() {
    }
}
