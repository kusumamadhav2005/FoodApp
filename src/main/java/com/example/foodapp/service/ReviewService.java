package com.example.foodapp.service;

import com.example.foodapp.model.Review;
import com.example.foodapp.model.User;
import com.example.foodapp.repository.ReviewRepository;
import com.example.foodapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public Review addReview(String email,
                            String restaurantId,
                            int rating,
                            String reviewText) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review(
                restaurantId,
                email,
                user.getUsername(),
                rating,
                reviewText == null ? "" : reviewText
        );

        return reviewRepository.save(review);
    }

    public List<Review> getReviews(String restaurantId) {
        return reviewRepository
                .findByRestaurantIdOrderByCreatedAtDesc(restaurantId);
    }

    /* 🔹 EDIT REVIEW */
    public Review updateReview(String reviewId,
                               String email,
                               int rating,
                               String reviewText) {

        Review review = reviewRepository
                .findByIdAndUserEmail(reviewId, email)
                .orElseThrow(() -> new RuntimeException("Review not found or unauthorized"));

        review.setRating(rating);
        review.setReview(reviewText == null ? "" : reviewText);

        return reviewRepository.save(review);
    }

    public void deleteReview(String reviewId, String email) {

        Review review = reviewRepository
                .findByIdAndUserEmail(reviewId, email)
                .orElseThrow(() -> new RuntimeException("Review not found or unauthorized"));

        reviewRepository.delete(review);
    }

}
