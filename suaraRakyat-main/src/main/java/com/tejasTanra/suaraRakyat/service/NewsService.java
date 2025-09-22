package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.News;
import com.tejasTanra.suaraRakyat.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AuditLogService auditLogService;

    // Staff Admin / Super Admin creates a news draft
    public News createNews(Long authorId, String title, String body, Set<String> mediaRefs) {
        News news = new News();
        news.setAuthorId(authorId);
        news.setTitle(title);
        news.setBody(body);
        news.setMediaRefs(mediaRefs);
        news.setPublishedAt(null); // Initially a draft
        news.setDeleted(false);
        News savedNews = newsRepository.save(news);
        auditLogService.log(authorId, "NEWS_CREATE_DRAFT", "News", savedNews.getId(), null, savedNews);
        return savedNews;
    }

    // Staff Admin / Super Admin edits news
    public News updateNews(Long editorId, Long newsId, String title, String body, Set<String> mediaRefs) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty()) {
            throw new IllegalArgumentException("News not found.");
        }

        News existingNews = optionalNews.get();
        News oldNewsState = new News(existingNews.getAuthorId(), existingNews.getTitle(), existingNews.getBody(), existingNews.getMediaRefs(), existingNews.getVersion(), existingNews.getPublishedAt(), existingNews.isDeleted(), existingNews.getCreatedAt(), existingNews.getUpdatedAt());

        existingNews.setTitle(title);
        existingNews.setBody(body);
        existingNews.setMediaRefs(mediaRefs);
        existingNews.setUpdatedAt(LocalDateTime.now());
        News updatedNews = newsRepository.save(existingNews);
        auditLogService.log(editorId, "NEWS_UPDATE", "News", updatedNews.getId(), oldNewsState, updatedNews);
        return updatedNews;
    }

    // Super Admin publishes news
    public News publishNews(Long publisherId, Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty()) {
            throw new IllegalArgumentException("News not found.");
        }

        News existingNews = optionalNews.get();
        News oldNewsState = new News(existingNews.getAuthorId(), existingNews.getTitle(), existingNews.getBody(), existingNews.getMediaRefs(), existingNews.getVersion(), existingNews.getPublishedAt(), existingNews.isDeleted(), existingNews.getCreatedAt(), existingNews.getUpdatedAt());

        existingNews.setPublishedAt(LocalDateTime.now());
        existingNews.setUpdatedAt(LocalDateTime.now());
        News publishedNews = newsRepository.save(existingNews);
        auditLogService.log(publisherId, "NEWS_PUBLISH", "News", publishedNews.getId(), oldNewsState, publishedNews);
        return publishedNews;
    }

    // Super Admin unpublishes news
    public News unpublishNews(Long unpublisherId, Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty()) {
            throw new IllegalArgumentException("News not found.");
        }

        News existingNews = optionalNews.get();
        News oldNewsState = new News(existingNews.getAuthorId(), existingNews.getTitle(), existingNews.getBody(), existingNews.getMediaRefs(), existingNews.getVersion(), existingNews.getPublishedAt(), existingNews.isDeleted(), existingNews.getCreatedAt(), existingNews.getUpdatedAt());

        existingNews.setPublishedAt(null); // Set to null to unpublish
        existingNews.setUpdatedAt(LocalDateTime.now());
        News unpublishedNews = newsRepository.save(existingNews);
        auditLogService.log(unpublisherId, "NEWS_UNPUBLISH", "News", unpublishedNews.getId(), oldNewsState, unpublishedNews);
        return unpublishedNews;
    }

    // Super Admin soft-deletes news
    public News softDeleteNews(Long deleterId, Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty()) {
            throw new IllegalArgumentException("News not found.");
        }

        News existingNews = optionalNews.get();
        News oldNewsState = new News(existingNews.getAuthorId(), existingNews.getTitle(), existingNews.getBody(), existingNews.getMediaRefs(), existingNews.getVersion(), existingNews.getPublishedAt(), existingNews.isDeleted(), existingNews.getCreatedAt(), existingNews.getUpdatedAt());

        existingNews.setDeleted(true);
        existingNews.setUpdatedAt(LocalDateTime.now());
        News deletedNews = newsRepository.save(existingNews);
        auditLogService.log(deleterId, "NEWS_SOFT_DELETE", "News", deletedNews.getId(), oldNewsState, deletedNews);
        return deletedNews;
    }

    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    public List<News> findAllPublishedNews() {
        return newsRepository.findByPublishedAtBeforeAndIsDeletedFalse(LocalDateTime.now());
    }

    public List<News> findAllActiveNews() {
        return newsRepository.findByIsDeletedFalse();
    }

    public List<News> findNewsByAuthor(Long authorId) {
        return newsRepository.findByAuthorIdAndIsDeletedFalse(authorId);
    }
}
