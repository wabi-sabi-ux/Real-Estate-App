package com.realestate.repository;

import com.realestate.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface BrokerRepository extends JpaRepository<Broker, Long> {
    @Query("SELECT b FROM Broker b ORDER BY b.avgRating DESC")
    List<Broker> findAllOrderByRatingDesc();
    
    @Query("SELECT b FROM Broker b WHERE b.avgRating >= :minRating")
    List<Broker> findByMinimumRating(double minRating);

    Optional<Broker> findByUser_UserId(Long userId);
}
