package com.example.foodapp.controller;

import com.example.foodapp.dto.ReviewRequest;
import com.example.foodapp.model.Review;
import com.example.foodapp.security.JwtUtil;
import com.example.foodapp.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    public ReviewController(ReviewService reviewService,
                            JwtUtil jwtUtil) {
        this.reviewService = reviewService;
        this.jwtUtil = jwtUtil;
    }

    /* PUBLIC */
    @GetMapping("/{restaurantId}")
    public List<Review> getReviews(@PathVariable String restaurantId) {
        return reviewService.getReviews(restaurantId);
    }

    /* ADD REVIEW */
    @PostMapping("/{restaurantId}")
    public Review addReview(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String restaurantId,
            @RequestBody ReviewRequest request) {

        String email = jwtUtil.extractEmail(authHeader.substring(7));

        return reviewService.addReview(
                email,
                restaurantId,
                request.getRating(),
                request.getReview()
        );
    }

    /* ✏️ EDIT REVIEW */
    @PutMapping("/{reviewId}")
    public Review updateReview(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String reviewId,
            @RequestBody ReviewRequest request) {

        String email = jwtUtil.extractEmail(authHeader.substring(7));

        return reviewService.updateReview(
                reviewId,
                email,
                request.getRating(),
                request.getReview()
        );
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String reviewId) {

        String email = jwtUtil.extractEmail(authHeader.substring(7));
        reviewService.deleteReview(reviewId, email);
    }

}
