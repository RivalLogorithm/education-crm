package ru.education.crm.backend.entity;

import lombok.Getter;
import lombok.Setter;
import ru.education.crm.backend.dto.PurchaseOrderDTO;

import javax.persistence.*;

@Entity
@Table(name = "purchase_order")
@Getter
@Setter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private long orderNumber;

    @Column(name = "material")
    private String material;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private double price;

    @Column(name = "order_details")
    private String orderDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_number")
    private Contract contract;

    public PurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
        this.material = purchaseOrderDTO.getMaterial();
        this.count = purchaseOrderDTO.getCount();
        this.price = purchaseOrderDTO.getPrice();
        this.orderDetails = purchaseOrderDTO.getOrderDetails();
    }

    public PurchaseOrder() {
    }
}
