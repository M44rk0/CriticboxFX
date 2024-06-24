package com.m44rk0.criticboxfx.model.user;

import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.Title;

import java.util.ArrayList;

public class User {

    private static Integer userCount = 1;
    private Integer userID;
    private String name;
    private String username;
    private String password;
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

    public ArrayList<String> getFavoritesNames() {
        ArrayList<String> favoriteNames = new ArrayList<>();
        for (int i = 0; i < getFavorites().size(); i++) {
            favoriteNames.add(getFavorites().get(i).getName());
        }

        return favoriteNames;
    }

    public ArrayList<String> getFavoritesPosterPath() {
        ArrayList<String> favoritesPosterPath = new ArrayList<>();
        for (int i = 0; i < getFavorites().size(); i++) {
            favoritesPosterPath.add(getFavorites().get(i).getPosterPath());
        }

        return favoritesPosterPath;
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Title> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Title> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<Title> getWatched() {
        return watched;
    }

    public void setWatched(ArrayList<Title> watched) {
        this.watched = watched;
    }
}
