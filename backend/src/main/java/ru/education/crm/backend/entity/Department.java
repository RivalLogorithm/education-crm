package ru.education.crm.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "department")
@Setter
@Getter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private long departmentId;

    @Column(name = "name")
    private String name;

    public Department(long departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public Department() {
    }
}
