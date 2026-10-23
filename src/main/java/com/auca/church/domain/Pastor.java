package com.auca.church.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "pastor")
public class Pastor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "parish_id")
    private Parish parish;

    @ManyToMany
    @JoinTable(
        name = "pastor_ministry",
        joinColumns = @JoinColumn(name = "pastor_id"),
        inverseJoinColumns = @JoinColumn(name = "ministry_id")
    )
    private List<Ministry> ministries = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Parish getParish() { return parish; }
    public void setParish(Parish parish) { this.parish = parish; }

    public List<Ministry> getMinistries() { return ministries; }
    public void setMinistries(List<Ministry> ministries) { this.ministries = ministries; }
}
