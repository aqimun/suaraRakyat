package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.MstCityRegency;
import com.tejasTanra.suaraRakyat.repository.MstCityRegencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class MstCityRegencyService {

    @Autowired
    private MstCityRegencyRepository mstCityRegencyRepository;

    public List<MstCityRegency> findAll() {
        return mstCityRegencyRepository.findAll();
    }

    public Optional<MstCityRegency> findById(UUID id) {
        return mstCityRegencyRepository.findById(id);
    }

    public MstCityRegency save(MstCityRegency mstCityRegency) {
        return mstCityRegencyRepository.save(mstCityRegency);
    }

    public void deleteById(UUID id) {
        mstCityRegencyRepository.deleteById(id);
    }
}
