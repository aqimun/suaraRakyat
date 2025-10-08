package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.model.Address;
import com.tejasTanra.suaraRakyat.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable UUID id) {
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.save(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable UUID id, @RequestBody Address address) {
        return addressService.findById(id)
                .map(existingAddress -> {
                    existingAddress.setAddress(address.getAddress());
                    existingAddress.setProvince(address.getProvince());
                    existingAddress.setCityRegency(address.getCityRegency());
                    existingAddress.setSubdistrict(address.getSubdistrict());
                    existingAddress.setVillage(address.getVillage());
                    existingAddress.setPostalCode(address.getPostalCode());
                    return ResponseEntity.ok(addressService.save(existingAddress));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
