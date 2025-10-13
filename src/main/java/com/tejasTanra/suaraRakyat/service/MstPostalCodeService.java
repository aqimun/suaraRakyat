package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.MstPostalCode;
import com.tejasTanra.suaraRakyat.repository.MstPostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class MstPostalCodeService {

    @Autowired
    private MstPostalCodeRepository mstPostalCodeRepository;

    public List<MstPostalCode> findAll() {
        return mstPostalCodeRepository.findAll();
    }

    public Optional<MstPostalCode> findById(UUID id) {
        return mstPostalCodeRepository.findById(id);
    }

    public MstPostalCode save(MstPostalCode mstPostalCode) {
        return mstPostalCodeRepository.save(mstPostalCode);
    }

    public void deleteById(UUID id) {
        mstPostalCodeRepository.deleteById(id);
    }
}
