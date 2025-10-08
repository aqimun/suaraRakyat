package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "mst_village")
public class MstVillage {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "uuid_subdistrict", referencedColumnName = "id", nullable = false)
    private MstSubdistrict subdistrict;

    public MstVillage() {}

    public MstVillage(String name, MstSubdistrict subdistrict) {
        this.name = name;
        this.subdistrict = subdistrict;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MstSubdistrict getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(MstSubdistrict subdistrict) {
        this.subdistrict = subdistrict;
    }
}
