package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "mst_address")
public class Address {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "uuid_province", referencedColumnName = "id", nullable = false)
    private MstProvince province;

    @ManyToOne
    @JoinColumn(name = "uuid_city_regency", referencedColumnName = "id", nullable = false)
    private MstCityRegency cityRegency;

    @ManyToOne
    @JoinColumn(name = "uuid_subdistrict", referencedColumnName = "id", nullable = false)
    private MstSubdistrict subdistrict;

    @ManyToOne
    @JoinColumn(name = "uuid_village", referencedColumnName = "id", nullable = false)
    private MstVillage village;

    @ManyToOne
    @JoinColumn(name = "uuid_postal_code", referencedColumnName = "id", nullable = false)
    private MstPostalCode postalCode;

    public Address() {}

    public Address(String address, MstProvince province, MstCityRegency cityRegency, MstSubdistrict subdistrict, MstVillage village, MstPostalCode postalCode) {
        this.address = address;
        this.province = province;
        this.cityRegency = cityRegency;
        this.subdistrict = subdistrict;
        this.village = village;
        this.postalCode = postalCode;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MstProvince getProvince() {
        return province;
    }

    public void setProvince(MstProvince province) {
        this.province = province;
    }

    public MstCityRegency getCityRegency() {
        return cityRegency;
    }

    public void setCityRegency(MstCityRegency cityRegency) {
        this.cityRegency = cityRegency;
    }

    public MstSubdistrict getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(MstSubdistrict subdistrict) {
        this.subdistrict = subdistrict;
    }

    public MstVillage getVillage() {
        return village;
    }

    public void setVillage(MstVillage village) {
        this.village = village;
    }

    public MstPostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(MstPostalCode postalCode) {
        this.postalCode = postalCode;
    }
}
