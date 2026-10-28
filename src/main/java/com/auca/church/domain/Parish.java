package com.auca.church.domain;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "parish")
public class Parish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "parish_number")
    private int parishNumber;

    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getParishNumber() {
        return parishNumber;
    }

    public void setParishNumber(int parishNumber) {
        this.parishNumber = parishNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
