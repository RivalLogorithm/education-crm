package ru.education.crm.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "provider")
@Getter
@Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private long providerId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public Provider() {
    }
}

