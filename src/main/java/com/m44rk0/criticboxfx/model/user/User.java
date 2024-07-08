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
    private ArrayList<Title> favorites;
    private ArrayList<Review> reviews;
    private ArrayList<Title> watched;

    public User(String name, String username, String password) {
        this.userID = userCount++;
        this.name = name;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.watched = new ArrayList<>();
    }

    public User(Integer userId, String name, String username, String password) {
        this.userID = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.watched = new ArrayList<>();
    }

    public void addFavorite(Title title){
        favorites.add(title);
    }

    public void removeFavorite(Title title){
        favorites.remove(title);
    }

    public void addWatched(Title title){
        watched.add(title);
    }

    public void removeWatched(Title title){
        watched.remove(title);
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

    public void setFavorites(ArrayList<Title> favorites) {
        this.favorites = favorites;
    }

    public void setReviews(ArrayList<Review> reviews){
        this.reviews = reviews;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Title> getWatched() {
        return watched;
    }

    public void setWatched(ArrayList<Title> watched) {
        this.watched = watched;
    }
}
