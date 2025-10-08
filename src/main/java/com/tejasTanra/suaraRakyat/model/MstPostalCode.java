package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "mst_postal_code")
public class MstPostalCode {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 10)
    private String number;

    @ManyToOne
    @JoinColumn(name = "uuid_village", referencedColumnName = "id", nullable = false)
    private MstVillage village;

    public MstPostalCode() {}

    public MstPostalCode(String number, MstVillage village) {
        this.number = number;
        this.village = village;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public MstVillage getVillage() {
        return village;
    }

    public void setVillage(MstVillage village) {
        this.village = village;
    }
}
