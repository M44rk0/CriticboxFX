package com.m44rk0.criticboxfx.model.user;

import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.Title;

import java.util.ArrayList;

public class User {

    private static Integer userCount = 1;
    private final Integer userID;
    private final String name;
    private final String username;
    private final String password;
    private final ArrayList<Title> favorites;
    private final ArrayList<Review> reviews;
    private final ArrayList<Title> watched;

    public User(String name, String username, String password) {
        this.userID = userCount++;
        this.name = name;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.watched = new ArrayList<>();
    }

    public void addFavorite(Title tittle){
        favorites.add(tittle);
    }

    public void removeFavorite(Title tittle){
        favorites.remove(tittle);
    }

    public void addWatched(Title tittle){
        watched.add(tittle);
    }

    public void removeWatched(Title tittle){
        watched.remove(tittle);
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public void removeReview(Review review){
        reviews.remove(review);
    }

    public Integer getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Title> getFavorites() {
        return favorites;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Title> getWatched() {
        return watched;
    }

}
