package com.example.foodapp.controller;

import com.example.foodapp.dto.FavoriteRequest;
import com.example.foodapp.dto.RemoveFavoriteRequest;
import com.example.foodapp.model.FavoriteItem;
import com.example.foodapp.service.FavoriteService;
import com.example.foodapp.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@CrossOrigin("*")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;

    public FavoriteController(FavoriteService favoriteService, JwtUtil jwtUtil) {
        this.favoriteService = favoriteService;
        this.jwtUtil = jwtUtil;
    }

    private String extractEmail(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token");
        }
        return jwtUtil.extractEmail(authHeader.substring(7));
    }


    @GetMapping
    public ResponseEntity<List<FavoriteItem>> getFavorites(
            @RequestHeader("Authorization") String authHeader) {

        String email = extractEmail(authHeader);
        return ResponseEntity.ok(favoriteService.getFavorites(email));
    }

    @PostMapping("/add")
    public List<FavoriteItem> addFavorite(
            @AuthenticationPrincipal String email,
            @RequestBody FavoriteRequest request
    ) {
        return favoriteService.addOrUpdateFavorite(
                email,
                request.getRestaurantId(),
                request.getRating(),
                request.getReview()
        );
    }




    @PostMapping("/remove")
    public ResponseEntity<List<FavoriteItem>> removeFavorite(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RemoveFavoriteRequest request) {

        String email = extractEmail(authHeader);
        return ResponseEntity.ok(
                favoriteService.removeFavorite(email, request.getRestaurantId())
        );
    }
    @GetMapping("/rating/{restaurantId}")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable String restaurantId) {

        return ResponseEntity.ok(
                favoriteService.getAverageRating(restaurantId)
        );
    }
    @GetMapping("/reviews/{restaurantId}")
    public ResponseEntity<?> getReviews(
            @PathVariable String restaurantId,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                favoriteService.getReviews(restaurantId, page, size)
        );
    }
    @PostMapping("/reviews/{restaurantId}")
    public ResponseEntity<?> addReview(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String restaurantId,
            @RequestBody FavoriteRequest request) {

        String email = extractEmail(authHeader);

        return ResponseEntity.ok(
                favoriteService.addOrUpdateFavorite(
                        email,
                        restaurantId,
                        request.getRating(),
                        request.getReview()
                )
        );
    }



}
