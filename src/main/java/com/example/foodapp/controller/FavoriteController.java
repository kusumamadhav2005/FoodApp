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
@CrossOrigin("http://localhost:5173")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;

    public FavoriteController(FavoriteService favoriteService, JwtUtil jwtUtil) {
        this.favoriteService = favoriteService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<FavoriteItem> getFavorites(
            @RequestHeader("Authorization") String authHeader
    ) {
        String email = jwtUtil.extractEmail(authHeader.substring(7));
        return favoriteService.getFavorites(email);
    }

    @PostMapping("/add")
    public List<FavoriteItem> addFavorite(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FavoriteRequest request
    ) {
        String email = jwtUtil.extractEmail(authHeader.substring(7));

        return favoriteService.addOrUpdateFavorite(
                email,
                request.getRestaurantId(),
                request.getRating(),
                request.getReview()
        );
    }

    @DeleteMapping("/{restaurantId}")
    public List<FavoriteItem> removeFavorite(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String restaurantId
    ) {
        String email = jwtUtil.extractEmail(authHeader.substring(7));
        return favoriteService.removeFavorite(email, restaurantId);
    }
}

