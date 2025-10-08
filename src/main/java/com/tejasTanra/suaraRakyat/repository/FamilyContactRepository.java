package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.FamilyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FamilyContactRepository extends JpaRepository<FamilyContact, UUID> {
}
