package ru.education.crm.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private long statusId;

    @Column(name = "name")
    private String name;

    public Status(long statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public Status() {
    }
}
