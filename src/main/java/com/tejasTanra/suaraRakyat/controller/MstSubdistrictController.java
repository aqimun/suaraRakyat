package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.MstSubdistrict;
import com.tejasTanra.suaraRakyat.service.MstSubdistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subdistricts")
public class MstSubdistrictController {

    @Autowired
    private MstSubdistrictService mstSubdistrictService;

    @GetMapping
    public List<MstSubdistrict> getAllSubdistricts() {
        return mstSubdistrictService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MstSubdistrict> getSubdistrictById(@PathVariable UUID id) {
        return mstSubdistrictService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MstSubdistrict createSubdistrict(@RequestBody MstSubdistrict mstSubdistrict) {
        return mstSubdistrictService.save(mstSubdistrict);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MstSubdistrict> updateSubdistrict(@PathVariable UUID id, @RequestBody MstSubdistrict mstSubdistrict) {
        return mstSubdistrictService.findById(id)
                .map(existingSubdistrict -> {
                    existingSubdistrict.setName(mstSubdistrict.getName());
                    existingSubdistrict.setCityRegency(mstSubdistrict.getCityRegency());
                    return ResponseEntity.ok(mstSubdistrictService.save(existingSubdistrict));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubdistrict(@PathVariable UUID id) {
        mstSubdistrictService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
