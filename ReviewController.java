package com.example.foodapp.controller;

import com.example.foodapp.dto.ReviewRequest;
import com.example.foodapp.model.Review;
import com.example.foodapp.security.JwtUtil;
import com.example.foodapp.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin("http://localhost:5173/")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    public ReviewController(ReviewService reviewService,
                            JwtUtil jwtUtil) {
        this.reviewService = reviewService;
        this.jwtUtil = jwtUtil;
    }

    // PUBLIC – anyone can see reviews
    @GetMapping("/{restaurantId}")
    public List<Review> getReviews(@PathVariable String restaurantId) {
        return reviewService.getReviews(restaurantId);
    }

    // PROTECTED – only logged-in users
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
}
