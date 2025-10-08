package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.FamilyContact;
import com.tejasTanra.suaraRakyat.service.FamilyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/family-contacts")
public class FamilyContactController {

    @Autowired
    private FamilyContactService familyContactService;

    @GetMapping
    public List<FamilyContact> getAllFamilyContacts() {
        return familyContactService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyContact> getFamilyContactById(@PathVariable UUID id) {
        return familyContactService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FamilyContact createFamilyContact(@RequestBody FamilyContact familyContact) {
        return familyContactService.save(familyContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyContact> updateFamilyContact(@PathVariable UUID id, @RequestBody FamilyContact familyContact) {
        return familyContactService.findById(id)
                .map(existingFamilyContact -> {
                    existingFamilyContact.setFullName(familyContact.getFullName());
                    existingFamilyContact.setRelationship(familyContact.getRelationship());
                    existingFamilyContact.setPhoneNumber(familyContact.getPhoneNumber());
                    existingFamilyContact.setNik(familyContact.getNik());
                    existingFamilyContact.setAddress(familyContact.getAddress());
                    existingFamilyContact.setDetailUser(familyContact.getDetailUser());
                    return ResponseEntity.ok(familyContactService.save(existingFamilyContact));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyContact(@PathVariable UUID id) {
        familyContactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
