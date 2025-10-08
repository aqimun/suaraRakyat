package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "mst_subdistrict")
public class MstSubdistrict {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "uuid_city_regency", referencedColumnName = "id", nullable = false)
    private MstCityRegency cityRegency;

    public MstSubdistrict() {}

    public MstSubdistrict(String name, MstCityRegency cityRegency) {
        this.name = name;
        this.cityRegency = cityRegency;
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

    public MstCityRegency getCityRegency() {
        return cityRegency;
    }

    public void setCityRegency(MstCityRegency cityRegency) {
        this.cityRegency = cityRegency;
    }
}
