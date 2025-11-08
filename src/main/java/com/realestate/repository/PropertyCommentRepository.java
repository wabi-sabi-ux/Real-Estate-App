package com.realestate.repository;

import com.realestate.model.Property;
import com.realestate.model.PropertyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyCommentRepository extends JpaRepository<PropertyComment, Long> {
    List<PropertyComment> findByPropertyOrderByCreatedAtDesc(Property property);
    
    @Query("SELECT AVG(c.rating) FROM PropertyComment c WHERE c.property = ?1")
    Double getAverageRatingForProperty(Property property);
    
    @Query("SELECT COUNT(c) FROM PropertyComment c WHERE c.property = ?1")
    Integer getReviewCountForProperty(Property property);
}
