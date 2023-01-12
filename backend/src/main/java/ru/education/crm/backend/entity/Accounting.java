package ru.education.crm.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "accounting")
@Data
public class Accounting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code")
    private Invoice invoice;

    @Column(name = "operation")
    private String operation;

    @Column(name = "debit")
    private double debit;

    @Column(name = "credit")
    private double credit;



    public Accounting() {
    }
}
