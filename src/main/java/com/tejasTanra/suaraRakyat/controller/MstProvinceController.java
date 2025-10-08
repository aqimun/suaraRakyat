package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.MstProvince;
import com.tejasTanra.suaraRakyat.service.MstProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/provinces")
public class MstProvinceController {

    @Autowired
    private MstProvinceService mstProvinceService;

    @GetMapping
    public List<MstProvince> getAllProvinces() {
        return mstProvinceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MstProvince> getProvinceById(@PathVariable UUID id) {
        return mstProvinceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MstProvince createProvince(@RequestBody MstProvince mstProvince) {
        return mstProvinceService.save(mstProvince);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MstProvince> updateProvince(@PathVariable UUID id, @RequestBody MstProvince mstProvince) {
        return mstProvinceService.findById(id)
                .map(existingProvince -> {
                    existingProvince.setName(mstProvince.getName());
                    return ResponseEntity.ok(mstProvinceService.save(existingProvince));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable UUID id) {
        mstProvinceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
