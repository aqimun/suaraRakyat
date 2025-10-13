package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "DetailUser cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_user_id", nullable = false)
    private DetailUser detailUser;

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Relationship cannot be blank")
    @Size(max = 50, message = "Relationship cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String relationship; // contoh: Ayah, Ibu, Saudara, Suami/Istri

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @NotBlank(message = "NIK cannot be blank")
    @Size(min = 16, max = 16, message = "NIK must be 16 characters long")
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
