package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.MstProvince;
import com.tejasTanra.suaraRakyat.repository.MstProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class MstProvinceService {

    @Autowired
    private MstProvinceRepository mstProvinceRepository;

    public List<MstProvince> findAll() {
        return mstProvinceRepository.findAll();
    }

    public Optional<MstProvince> findById(UUID id) {
        return mstProvinceRepository.findById(id);
    }

    public MstProvince save(MstProvince mstProvince) {
        return mstProvinceRepository.save(mstProvince);
    }

    public void deleteById(UUID id) {
        mstProvinceRepository.deleteById(id);
    }
}
