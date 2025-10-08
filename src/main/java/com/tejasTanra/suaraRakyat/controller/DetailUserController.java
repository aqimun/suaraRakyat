package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.DetailUser;
import com.tejasTanra.suaraRakyat.service.DetailUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/detail-users")
public class DetailUserController {

    @Autowired
    private DetailUserService detailUserService;

    @GetMapping
    public List<DetailUser> getAllDetailUsers() {
        return detailUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailUser> getDetailUserById(@PathVariable UUID id) {
        return detailUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetailUser createDetailUser(@RequestBody DetailUser detailUser) {
        return detailUserService.save(detailUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailUser> updateDetailUser(@PathVariable UUID id, @RequestBody DetailUser detailUser) {
        return detailUserService.findById(id)
                .map(existingDetailUser -> {
                    existingDetailUser.setFullName(detailUser.getFullName());
                    existingDetailUser.setPhoneNumber(detailUser.getPhoneNumber());
                    existingDetailUser.setNik(detailUser.getNik());
                    existingDetailUser.setUser(detailUser.getUser());
                    existingDetailUser.setAddress(detailUser.getAddress());
                    existingDetailUser.setFamilyContact(detailUser.getFamilyContact());
                    return ResponseEntity.ok(detailUserService.save(existingDetailUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailUser(@PathVariable UUID id) {
        detailUserService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
