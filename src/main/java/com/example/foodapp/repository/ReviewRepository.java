package com.example.foodapp.repository;

import com.example.foodapp.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByRestaurantIdOrderByCreatedAtDesc(String restaurantId);

    Optional<Review> findByIdAndUserEmail(String id, String userEmail);
}
