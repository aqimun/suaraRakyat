package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByIsDeletedFalse();
    List<News> findByAuthorIdAndIsDeletedFalse(Long authorId);
    List<News> findByPublishedAtBeforeAndIsDeletedFalse(java.time.LocalDateTime now);
}
