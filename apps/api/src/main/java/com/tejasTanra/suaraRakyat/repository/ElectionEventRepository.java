package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.ElectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElectionEventRepository extends JpaRepository<ElectionEvent, Long> {
    List<ElectionEvent> findByIsActiveTrueAndStartTimeBeforeAndEndTimeAfter(LocalDateTime now1, LocalDateTime now2);
    List<ElectionEvent> findByIsActiveTrue();
    List<ElectionEvent> findByIsActiveFalse();
}
