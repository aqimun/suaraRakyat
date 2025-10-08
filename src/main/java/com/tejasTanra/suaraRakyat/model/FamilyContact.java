package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "family_contacts")
public class FamilyContact {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_user_id", nullable = false)
    private DetailUser detailUser;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 50)
    private String relationship; // contoh: Ayah, Ibu, Saudara, Suami/Istri

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String nik;

    @ManyToOne
    @JoinColumn(name = "uuid_address", referencedColumnName = "id")
    private Address address;

    public FamilyContact() {}

    public FamilyContact(DetailUser detailUser, String fullName, String relationship, String phoneNumber, String nik, Address address) {
        this.detailUser = detailUser;
        this.fullName = fullName;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.nik = nik;
        this.address = address;
    }

    // Getter & Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DetailUser getDetailUser() {
        return detailUser;
    }

    public void setDetailUser(DetailUser detailUser) {
        this.detailUser = detailUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
