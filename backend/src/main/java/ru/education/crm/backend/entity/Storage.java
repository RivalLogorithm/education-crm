package ru.education.crm.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "storage")
@Data
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article")
    private long article;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private int count;

    public Storage(long article, String name, int count) {
        this.article = article;
        this.name = name;
        this.count = count;
    }

    public Storage() {
    }
}
