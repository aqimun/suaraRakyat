package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.FamilyContact;
import com.tejasTanra.suaraRakyat.repository.FamilyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FamilyContactService {

    @Autowired
    private FamilyContactRepository familyContactRepository;

    public List<FamilyContact> findAll() {
        return familyContactRepository.findAll();
    }

    public Optional<FamilyContact> findById(UUID id) {
        return familyContactRepository.findById(id);
    }

    public FamilyContact save(FamilyContact familyContact) {
        return familyContactRepository.save(familyContact);
    }

    public void deleteById(UUID id) {
        familyContactRepository.deleteById(id);
    }
}
