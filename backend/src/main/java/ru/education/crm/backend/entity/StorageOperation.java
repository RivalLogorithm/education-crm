package ru.education.crm.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class StorageOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private long operationId;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "article")
    private Storage article;

    @OneToOne
    @JoinColumn(name = "order_number")
    private PurchaseOrder orderNumber;

    public StorageOperation(long operationId, LocalDate date, Storage article, PurchaseOrder orderNumber) {
        this.operationId = operationId;
        this.date = date;
        this.article = article;
        this.orderNumber = orderNumber;
    }

    public StorageOperation() {
    }
}
