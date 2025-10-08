package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.MstProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MstProvinceRepository extends JpaRepository<MstProvince, UUID> {
}
