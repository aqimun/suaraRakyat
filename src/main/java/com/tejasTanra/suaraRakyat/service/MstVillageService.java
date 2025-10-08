package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.MstVillage;
import com.tejasTanra.suaraRakyat.repository.MstVillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MstVillageService {

    @Autowired
    private MstVillageRepository mstVillageRepository;

    public List<MstVillage> findAll() {
        return mstVillageRepository.findAll();
    }

    public Optional<MstVillage> findById(UUID id) {
        return mstVillageRepository.findById(id);
    }

    public MstVillage save(MstVillage mstVillage) {
        return mstVillageRepository.save(mstVillage);
    }

    public void deleteById(UUID id) {
        mstVillageRepository.deleteById(id);
    }
}
