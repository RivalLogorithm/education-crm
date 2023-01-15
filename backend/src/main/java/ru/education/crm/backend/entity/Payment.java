package ru.education.crm.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long paymentId;

    @Column(name = "payment_sum")
    private double paymentSum;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_number")
    private Contract contract;

    public Payment(double paymentSum, Contract contract) {
        this.paymentSum = paymentSum;
        this.contract = contract;
    }

    public Payment() {
    }
}
