package com.m44rk0.criticboxfx.controller.user;

import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.user.User;

import java.util.ArrayList;

/**
 * Classe responsável por gerenciar as informações do usuário atualmente logado.
 * Fornece métodos estáticos para acessar e manipular os dados do usuário,
 * como favoritos, títulos assistidos e avaliações.
 */
public class CurrentlyUser {

    private static User user;

    /**
     * Adiciona um título à lista de favoritos do usuário atualmente logado.
     *
     * @param title o título a ser adicionado aos favoritos
     */
    public static void addFavorite(Title title) {
        user.addFavorite(title);
    }

    /**
     * Adiciona um título à lista de títulos assistidos do usuário atualmente logado.
     *
     * @param title o título a ser adicionado aos assistidos
     */
    public static void addWatched(Title title) {
        user.addWatched(title);
    }

    /**
     * Remove um título da lista de favoritos do usuário atualmente logado.
     *
     * @param title o título a ser removido dos favoritos
     */
    public static void removeFavorite(Title title) {
        user.removeFavorite(title);
    }

    /**
     * Remove um título da lista de títulos assistidos do usuário atualmente logado.
     *
     * @param title o título a ser removido dos assistidos
     */
    public static void removeWatched(Title title) {
        user.removeWatched(title);
    }

    /**
     * Obtém a lista de favoritos do usuário atualmente logado.
     *
     * @return uma lista contendo os títulos favoritos do usuário
     */
    public static ArrayList<Title> getFavorites() {
        return user.getFavorites();
    }

    /**
     * Obtém a lista de títulos assistidos pelo usuário atualmente logado.
     *
     * @return uma lista contendo os títulos assistidos do usuário
     */
    public static ArrayList<Title> getWatched() {
        return user.getWatched();
    }

    /**
     * Adiciona uma avaliação à lista de avaliações do usuário atualmente logado.
     *
     * @param review a avaliação a ser adicionada
     */
    public static void addReview(Review review) {
        user.addReview(review);
    }

    /**
     * Remove uma avaliação da lista de avaliações do usuário atualmente logado.
     *
     * @param review a avaliação a ser removida
     */
    public static void removeReview(Review review) {
        user.removeReview(review);
    }

    /**
     * Obtém a lista de avaliações do usuário atualmente logado.
     *
     * @return uma lista contendo as avaliações do usuário
     */
    public static ArrayList<Review> getReviews() {
        return user.getReviews();
    }

    /**
     * Define a lista de favoritos do usuário atualmente logado.
     *
     * @param favorites a lista de favoritos a ser definida
     */
    public static void setFavorites(ArrayList<Title> favorites) {
        user.setFavorites(favorites);
    }

    /**
     * Define a lista de títulos assistidos do usuário atualmente logado.
     *
     * @param watched a lista de títulos assistidos a ser definida
     */
    public static void setWatched(ArrayList<Title> watched) {
        user.setWatched(watched);
    }

    /**
     * Obtém o usuário atualmente logado.
     *
     * @return o usuário atualmente logado
     */
    public static User getUser() {
        return user;
    }

    /**
     * Define o usuário atualmente logado.
     *
     * @param user o usuário a ser definido como o usuário atualmente logado
     */
    public static void setUser(User user) {
        CurrentlyUser.user = user;
    }

    /**
     * Define a lista de avaliações do usuário atualmente logado.
     *
     * @param reviews a lista de avaliações a ser definida
     */
    public static void setReviews(ArrayList<Review> reviews) {
        user.setReviews(reviews);
    }
}

