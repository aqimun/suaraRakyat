package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "detail_user")
public class DetailUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Full name cannot be blank")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "NIK cannot be blank")
    @Size(min = 16, max = 16, message = "NIK must be 16 characters long")
    @Column(name = "nik", nullable = false, unique = true)
    private String nik;

    // Relasi ke user
    @OneToOne
    @JoinColumn(name = "uuid_mst_user", referencedColumnName = "id")
    private User user;

    // Relasi ke alamat
    @ManyToOne
    @JoinColumn(name = "uuid_address", referencedColumnName = "id")
    private Address address;

    // Relasi ke family contact
    @OneToOne
    @JoinColumn(name = "uuid_family_contact", referencedColumnName = "id")
    private FamilyContact familyContact;

    public DetailUser() {}

    // Getter & Setter
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public FamilyContact getFamilyContact() { return familyContact; }
    public void setFamilyContact(FamilyContact familyContact) { this.familyContact = familyContact; }
}
