package com.realestate.controller;

import com.realestate.model.Property;
import com.realestate.model.PropertyComment;
import com.realestate.model.User;
import com.realestate.repository.PropertyCommentRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/properties/{propertyId}/comments")
public class PropertyCommentController {
    private final PropertyRepository propertyRepo;
    private final PropertyCommentRepository commentRepo;
    private final UserRepository userRepo;

    public PropertyCommentController(PropertyRepository propertyRepo,
                                   PropertyCommentRepository commentRepo,
                                   UserRepository userRepo) {
        this.propertyRepo = propertyRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable Long propertyId) {
        Property property = propertyRepo.findById(propertyId)
            .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        return ResponseEntity.ok(commentRepo.findByPropertyOrderByCreatedAtDesc(property));
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long propertyId,
                                      @RequestBody PropertyComment comment,
                                      Authentication auth) {
        Property property = propertyRepo.findById(propertyId)
            .orElseThrow(() -> new IllegalArgumentException("Property not found"));
            
        User user = userRepo.findByEmail(auth.getName())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        comment.setProperty(property);
        comment.setUser(user);
        
        PropertyComment saved = commentRepo.save(comment);
        
        // Update property rating stats
        property.setAvgRating(commentRepo.getAverageRatingForProperty(property));
        property.setReviewCount(commentRepo.getReviewCountForProperty(property));
        propertyRepo.save(property);
        
        return ResponseEntity.ok(saved);
    }
}
