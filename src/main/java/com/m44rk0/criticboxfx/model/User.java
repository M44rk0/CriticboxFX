package com.m44rk0.criticboxfx.model;

import java.util.ArrayList;

public class User {

    private static Integer userCount = 1;
    private Integer userID;
    private String name;
    private String username;
    private String password;
    private ArrayList<Title> favorites;
    private ArrayList<Review> reviews;

    public User(String name, String username, String password) {
        this.userID = userCount++;
        this.name = name;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.reviews = new ArrayList<>();
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

}
