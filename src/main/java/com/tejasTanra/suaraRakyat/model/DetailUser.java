package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
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

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "nik", nullable = false, unique = true)
    private String nik;

    // Relasi ke user
    @OneToOne
    @JoinColumn(name = "uuid_mst_user", referencedColumnName = "id")
    private User user;

    // Relasi ke role
    @ManyToOne
    @JoinColumn(name = "uuid_mst_role", referencedColumnName = "id")
    private Role role;

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

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public FamilyContact getFamilyContact() { return familyContact; }
    public void setFamilyContact(FamilyContact familyContact) { this.familyContact = familyContact; }
}
