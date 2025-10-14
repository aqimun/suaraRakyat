package com.tejasTanra.suaraRakyat.modules.news;

import com.tejasTanra.suaraRakyat.dto.NewsRequest;
import com.tejasTanra.suaraRakyat.exception.BadRequestException; // Import custom exceptions
import com.tejasTanra.suaraRakyat.exception.ResourceNotFoundException; // Already exists
import com.tejasTanra.suaraRakyat.model.News;
import com.tejasTanra.suaraRakyat.model.User; // Import User model
import com.tejasTanra.suaraRakyat.modules.news.NewsService;
import com.tejasTanra.suaraRakyat.modules.users.UserService; // Import UserService
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import PreAuthorize
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news") // Changed to /api/news for consistency
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService; // Inject UserService

    // Staff Admin / Super Admin creates a news draft
    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF_ADMIN', 'SUPER_ADMIN')") // Only STAFF_ADMIN or SUPER_ADMIN can create news
    public ResponseEntity<?> createNews(@Valid @RequestBody NewsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long authorId = currentUser.getId();

        try {
            News newNews = newsService.createNews(authorId, request.getTitle(), request.getBody(), request.getMediaRefs());
            return new ResponseEntity<>(newNews, HttpStatus.CREATED);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin / Super Admin edits news
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF_ADMIN', 'SUPER_ADMIN')") // Only STAFF_ADMIN or SUPER_ADMIN can update news
    public ResponseEntity<?> updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long editorId = currentUser.getId();

        try {
            News updatedNews = newsService.updateNews(editorId, id, request.getTitle(), request.getBody(), request.getMediaRefs());
            return new ResponseEntity<>(updatedNews, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin publishes news
    @PutMapping("/{id}/publish")
    @PreAuthorize("hasRole('SUPER_ADMIN')") // Only SUPER_ADMIN can publish news
    public ResponseEntity<?> publishNews(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long publisherId = currentUser.getId();

        try {
            News publishedNews = newsService.publishNews(publisherId, id);
            return new ResponseEntity<>(publishedNews, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin unpublishes news
    @PutMapping("/{id}/unpublish")
    @PreAuthorize("hasRole('SUPER_ADMIN')") // Only SUPER_ADMIN can unpublish news
    public ResponseEntity<?> unpublishNews(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long unpublisherId = currentUser.getId();

        try {
            News unpublishedNews = newsService.unpublishNews(unpublisherId, id);
            return new ResponseEntity<>(unpublishedNews, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin soft-deletes news
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')") // Only SUPER_ADMIN can soft-delete news
    public ResponseEntity<?> softDeleteNews(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long deleterId = currentUser.getId();

        try {
            News deletedNews = newsService.softDeleteNews(deleterId, id);
            return new ResponseEntity<>(deletedNews, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all published news (for User Rakyat, User Penjabat) - Publicly accessible
    @GetMapping("/published")
    @PreAuthorize("permitAll()") // Already handled by SecurityConfig, but explicit for clarity
    public ResponseEntity<List<News>> getAllPublishedNews() {
        return new ResponseEntity<>(newsService.findAllPublishedNews(), HttpStatus.OK);
    }

    // Get all active news (for Staff Admin, Super Admin)
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('STAFF_ADMIN', 'SUPER_ADMIN')") // Only STAFF_ADMIN or SUPER_ADMIN can view active news
    public ResponseEntity<List<News>> getAllActiveNews() {
        return new ResponseEntity<>(newsService.findAllActiveNews(), HttpStatus.OK);
    }

    // Get news by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Any authenticated user can view news by ID
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        // TODO: Add logic in service to ensure only published news is visible to RAKYAT,
        // and all news (draft/active/deleted) is visible to appropriate admins.
        return newsService.findById(id)
                .map(news -> new ResponseEntity<>(news, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id)); // Use custom exception
    }
}
