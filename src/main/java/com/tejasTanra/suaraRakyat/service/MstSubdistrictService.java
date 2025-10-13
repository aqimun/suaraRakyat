package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.MstSubdistrict;
import com.tejasTanra.suaraRakyat.repository.MstSubdistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class MstSubdistrictService {

    @Autowired
    private MstSubdistrictRepository mstSubdistrictRepository;

    public List<MstSubdistrict> findAll() {
        return mstSubdistrictRepository.findAll();
    }

    public Optional<MstSubdistrict> findById(UUID id) {
        return mstSubdistrictRepository.findById(id);
    }

    public MstSubdistrict save(MstSubdistrict mstSubdistrict) {
        return mstSubdistrictRepository.save(mstSubdistrict);
    }

    public void deleteById(UUID id) {
        mstSubdistrictRepository.deleteById(id);
    }
}
