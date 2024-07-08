package com.m44rk0.criticboxfx.controller.user;

import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.user.User;

import java.util.ArrayList;

public class CurrentlyUser {

    private static User user;

    public static void addFavorite(Title title){
        user.addFavorite(title);
    }

    public static void addWatched(Title title){
        user.addWatched(title);
    }

    public static void removeFavorite(Title title){
        user.removeFavorite(title);
    }

    public static void removeWatched(Title title){
        user.removeWatched(title);
    }

    public static ArrayList<Title> getFavorites(){
        return user.getFavorites();
    }

    public static ArrayList<Title> getWatched(){
        return user.getWatched();
    }

    public static void addReview(Review review){
        user.addReview(review);
    }

    public static void removeReview(Review review){
        user.removeReview(review);
    }

    public static ArrayList<Review> getReviews(){
        return user.getReviews();
    }

    public static void setFavorites(ArrayList<Title> favorites){
        user.setFavorites(favorites);
    }

    public static void setWatched(ArrayList<Title> watched){
        user.setWatched(watched);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentlyUser.user = user;
    }

    public static void setReviews(ArrayList<Review> reviews) {
        user.setReviews(reviews);
    }
}
