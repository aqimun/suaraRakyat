package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.MstCityRegency;
import com.tejasTanra.suaraRakyat.service.MstCityRegencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/city-regencies")
public class MstCityRegencyController {

    @Autowired
    private MstCityRegencyService mstCityRegencyService;

    @GetMapping
    public List<MstCityRegency> getAllCityRegencies() {
        return mstCityRegencyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MstCityRegency> getCityRegencyById(@PathVariable UUID id) {
        return mstCityRegencyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MstCityRegency createCityRegency(@RequestBody MstCityRegency mstCityRegency) {
        return mstCityRegencyService.save(mstCityRegency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MstCityRegency> updateCityRegency(@PathVariable UUID id, @RequestBody MstCityRegency mstCityRegency) {
        return mstCityRegencyService.findById(id)
                .map(existingCityRegency -> {
                    existingCityRegency.setName(mstCityRegency.getName());
                    existingCityRegency.setProvince(mstCityRegency.getProvince());
                    return ResponseEntity.ok(mstCityRegencyService.save(existingCityRegency));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityRegency(@PathVariable UUID id) {
        mstCityRegencyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
