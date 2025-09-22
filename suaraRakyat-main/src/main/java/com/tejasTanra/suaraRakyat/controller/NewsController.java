package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.dto.NewsRequest;
import com.tejasTanra.suaraRakyat.model.News;
import com.tejasTanra.suaraRakyat.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // Staff Admin / Super Admin creates a news draft
    @PostMapping
    public ResponseEntity<?> createNews(@Valid @RequestBody NewsRequest request) {
        // TODO: Get actual authorId from authenticated user context
        Long authorId = 2L; // Placeholder for Staff Admin / Super Admin ID

        try {
            News newNews = newsService.createNews(authorId, request.getTitle(), request.getBody(), request.getMediaRefs());
            return new ResponseEntity<>(newNews, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin / Super Admin edits news
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequest request) {
        // TODO: Get actual editorId from authenticated user context
        Long editorId = 2L; // Placeholder for Staff Admin / Super Admin ID

        try {
            News updatedNews = newsService.updateNews(editorId, id, request.getTitle(), request.getBody(), request.getMediaRefs());
            return new ResponseEntity<>(updatedNews, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin publishes news
    @PutMapping("/{id}/publish")
    public ResponseEntity<?> publishNews(@PathVariable Long id) {
        // TODO: Get actual publisherId from authenticated user context
        Long publisherId = 4L; // Placeholder for Super Admin ID

        try {
            News publishedNews = newsService.publishNews(publisherId, id);
            return new ResponseEntity<>(publishedNews, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin unpublishes news
    @PutMapping("/{id}/unpublish")
    public ResponseEntity<?> unpublishNews(@PathVariable Long id) {
        // TODO: Get actual unpublisherId from authenticated user context
        Long unpublisherId = 4L; // Placeholder for Super Admin ID

        try {
            News unpublishedNews = newsService.unpublishNews(unpublisherId, id);
            return new ResponseEntity<>(unpublishedNews, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin soft-deletes news
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteNews(@PathVariable Long id) {
        // TODO: Get actual deleterId from authenticated user context
        Long deleterId = 4L; // Placeholder for Super Admin ID

        try {
            News deletedNews = newsService.softDeleteNews(deleterId, id);
            return new ResponseEntity<>(deletedNews, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all published news (for User Rakyat, User Penjabat)
    @GetMapping("/published")
    public ResponseEntity<List<News>> getAllPublishedNews() {
        return new ResponseEntity<>(newsService.findAllPublishedNews(), HttpStatus.OK);
    }

    // Get all active news (for Staff Admin, Super Admin)
    @GetMapping("/active")
    public ResponseEntity<List<News>> getAllActiveNews() {
        return new ResponseEntity<>(newsService.findAllActiveNews(), HttpStatus.OK);
    }

    // Get news by ID
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        return newsService.findById(id)
                .map(news -> new ResponseEntity<>(news, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
