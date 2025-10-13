package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "mst_city_regency")
public class MstCityRegency {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "City/Regency name cannot be blank")
    @Size(max = 100, message = "City/Regency name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Province cannot be null")
    @ManyToOne
    @JoinColumn(name = "uuid_province", referencedColumnName = "id", nullable = false)
    private MstProvince province;

    public MstCityRegency() {}

    public MstCityRegency(String name, MstProvince province) {
        this.name = name;
        this.province = province;
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

    public MstProvince getProvince() {
        return province;
    }

    public void setProvince(MstProvince province) {
        this.province = province;
    }
}
