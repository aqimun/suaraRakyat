package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.DetailUser;
import com.tejasTanra.suaraRakyat.repository.DetailUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DetailUserService {

    @Autowired
    private DetailUserRepository detailUserRepository;

    public List<DetailUser> findAll() {
        return detailUserRepository.findAll();
    }

    public Optional<DetailUser> findById(UUID id) {
        return detailUserRepository.findById(id);
    }

    public DetailUser save(DetailUser detailUser) {
        return detailUserRepository.save(detailUser);
    }

    public void deleteById(UUID id) {
        detailUserRepository.deleteById(id);
    }
}
