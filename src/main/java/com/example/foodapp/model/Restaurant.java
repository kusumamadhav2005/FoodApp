package com.example.foodapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "restaurants")
public class Restaurant {

    @Id
    private String id;

    private String name;
    private String location;
    private List<String> dishes;

    private double price;      // avg price of items
    private double avgRating;  // auto-updated
    private int reviewCount;   // auto-updated

    public Restaurant() {}

    public Restaurant(String name, String location, List<String> dishes, double price) {
        this.name = name;
        this.location = location;
        this.dishes = dishes;
        this.price = price;
        this.avgRating = 0;
        this.reviewCount = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDishes() {
        return dishes;
    }

    public void setDishes(List<String> dishes) {
        this.dishes = dishes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", dishes=" + dishes +
                ", price=" + price +
                ", avgRating=" + avgRating +
                ", reviewCount=" + reviewCount +
                '}';
    }
}
