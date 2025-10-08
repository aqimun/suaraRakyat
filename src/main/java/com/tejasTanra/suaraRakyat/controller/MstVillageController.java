package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.MstVillage;
import com.tejasTanra.suaraRakyat.service.MstVillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/villages")
public class MstVillageController {

    @Autowired
    private MstVillageService mstVillageService;

    @GetMapping
    public List<MstVillage> getAllVillages() {
        return mstVillageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MstVillage> getVillageById(@PathVariable UUID id) {
        return mstVillageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MstVillage createVillage(@RequestBody MstVillage mstVillage) {
        return mstVillageService.save(mstVillage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MstVillage> updateVillage(@PathVariable UUID id, @RequestBody MstVillage mstVillage) {
        return mstVillageService.findById(id)
                .map(existingVillage -> {
                    existingVillage.setName(mstVillage.getName());
                    existingVillage.setSubdistrict(mstVillage.getSubdistrict());
                    return ResponseEntity.ok(mstVillageService.save(existingVillage));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVillage(@PathVariable UUID id) {
        mstVillageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
