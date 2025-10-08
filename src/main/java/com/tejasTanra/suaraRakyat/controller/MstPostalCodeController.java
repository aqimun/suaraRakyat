package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.MstPostalCode;
import com.tejasTanra.suaraRakyat.service.MstPostalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/postal-codes")
public class MstPostalCodeController {

    @Autowired
    private MstPostalCodeService mstPostalCodeService;

    @GetMapping
    public List<MstPostalCode> getAllPostalCodes() {
        return mstPostalCodeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MstPostalCode> getPostalCodeById(@PathVariable UUID id) {
        return mstPostalCodeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MstPostalCode createPostalCode(@RequestBody MstPostalCode mstPostalCode) {
        return mstPostalCodeService.save(mstPostalCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MstPostalCode> updatePostalCode(@PathVariable UUID id, @RequestBody MstPostalCode mstPostalCode) {
        return mstPostalCodeService.findById(id)
                .map(existingPostalCode -> {
                    existingPostalCode.setNumber(mstPostalCode.getNumber());
                    existingPostalCode.setVillage(mstPostalCode.getVillage());
                    return ResponseEntity.ok(mstPostalCodeService.save(existingPostalCode));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostalCode(@PathVariable UUID id) {
        mstPostalCodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
