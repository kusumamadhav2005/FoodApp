package com.example.foodapp.model;

import java.time.LocalDateTime;

public class FavoriteItem {

    private String restaurantId;
    private String username;   // ✅ NEW
    private int rating;
    private String review;
    private LocalDateTime createdAt;

    public FavoriteItem() {}

    public FavoriteItem(String restaurantId, String username, int rating, String review) {
        this.restaurantId = restaurantId;
        this.username = username;
        this.rating = rating;
        this.review = review;
        this.createdAt = LocalDateTime.now();
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
