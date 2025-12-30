package com.example.foodapp.service;

import com.example.foodapp.exception.ResourceNotFoundException;
import com.example.foodapp.model.FavoriteItem;
import com.example.foodapp.model.User;
import com.example.foodapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FavoriteService {

    private final UserRepository userRepository;

    public FavoriteService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public List<FavoriteItem> getFavorites(String email) {
        return getUser(email).getFavorites();
    }

    public List<FavoriteItem> addOrUpdateFavorite(
            String email,
            String restaurantId,
            int rating,
            String review) {

        User user = getUser(email);

        // Remove old review of same restaurant by same user
        user.getFavorites().removeIf(f ->
                restaurantId.equals(f.getRestaurantId())
        );

        // Add updated review at top
        user.getFavorites().add(
                0,
                new FavoriteItem(
                        restaurantId,
                        user.getUsername(), // ✅ username stored
                        rating,
                        review
                )
        );

        userRepository.save(user);
        return user.getFavorites();
    }





    public List<FavoriteItem> removeFavorite(String email, String restaurantId) {
        User user = getUser(email);

        user.getFavorites().removeIf(f ->
                f.getRestaurantId() != null &&
                        f.getRestaurantId().equals(restaurantId)
        );

        userRepository.save(user);
        return user.getFavorites();
    }
    public List<FavoriteItem> getReviews(String restaurantId, int page, int size) {

        List<FavoriteItem> all = userRepository.findAll()
                .stream()
                .flatMap(u -> u.getFavorites().stream())
                .filter(f -> restaurantId.equals(f.getRestaurantId()))
                .sorted((a, b) -> {
                    if (a.getCreatedAt() == null && b.getCreatedAt() == null) return 0;
                    if (a.getCreatedAt() == null) return 1;
                    if (b.getCreatedAt() == null) return -1;
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                })
                .toList();

        int start = page * size;
        int end = Math.min(start + size, all.size());

        if (start >= all.size()) return List.of();
        return all.subList(start, end);
    }


    public double getAverageRating(String restaurantId) {
        List<User> users = userRepository.findAll();

        return users.stream()
                .flatMap(u -> u.getFavorites().stream())
                .filter(f -> restaurantId.equals(f.getRestaurantId()))
                .mapToInt(FavoriteItem::getRating)
                .average()
                .orElse(0.0);
    }


}
