package com.m44rk0.criticboxfx.model.user;

import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.Title;

import java.util.ArrayList;

/**
 * Representa um usuário do sistema, com informações como ID do usuário, nome, nome de usuário e senha.
 * Além disso, o usuário pode ter uma lista de títulos favoritos, uma lista de revisões e uma lista de títulos assistidos.
 */
public class User {

    /**
     * Contador estático para gerar IDs únicos de usuário.
     */
    private static Integer userCount = 1;

    /**
     * O identificador único do usuário.
     */
    private final Integer userID;

    /**
     * O nome do usuário.
     */
    private final String name;

    /**
     * O nome de usuário do usuário.
     */
    private final String username;

    /**
     * A senha do usuário.
     */
    private final String password;

    /**
     * A lista de títulos favoritos do usuário.
     */
    private ArrayList<Title> favorites;

    /**
     * A lista de revisões feitas pelo usuário.
     */
    private ArrayList<Review> reviews;

    /**
     * A lista de títulos assistidos pelo usuário.
     */
    private ArrayList<Title> watched;

    /**
     * Construtor que inicializa um usuário com nome, nome de usuário e senha.
     * O ID do usuário é atribuído automaticamente.
     *
     * @param name     O nome do usuário.
     * @param username O nome de usuário do usuário.
     * @param password A senha do usuário.
     */
    public User(String name, String username, String password) {
        this.userID = userCount++;
        this.name = name;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.watched = new ArrayList<>();
    }

    /**
     * Construtor que inicializa um usuário com um ID de usuário pré-definido, nome, nome de usuário e senha.
     *
     * @param userId   O identificador único do usuário.
     * @param name     O nome do usuário.
     * @param username O nome de usuário do usuário.
     * @param password A senha do usuário.
     */
    public User(Integer userId, String name, String username, String password) {
        this.userID = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.watched = new ArrayList<>();
    }

    /**
     * Adiciona um título à lista de favoritos do usuário.
     *
     * @param title O título a ser adicionado aos favoritos.
     */
    public void addFavorite(Title title) {
        favorites.add(title);
    }

    /**
     * Remove um título da lista de favoritos do usuário.
     *
     * @param title O título a ser removido dos favoritos.
     */
    public void removeFavorite(Title title) {
        favorites.remove(title);
    }

    /**
     * Adiciona um título à lista de títulos assistidos pelo usuário.
     *
     * @param title O título a ser adicionado à lista de assistidos.
     */
    public void addWatched(Title title) {
        watched.add(title);
    }

    /**
     * Remove um título da lista de títulos assistidos pelo usuário.
     *
     * @param title O título a ser removido da lista de assistidos.
     */
    public void removeWatched(Title title) {
        watched.remove(title);
    }

    /**
     * Adiciona uma Review à lista de revisões do usuário.
     *
     * @param review A Review a ser adicionada.
     */
    public void addReview(Review review) {
        reviews.add(review);
    }

    /**
     * Remove uma Review da lista de revisões do usuário.
     *
     * @param review A Review a ser removida.
     */
    public void removeReview(Review review) {
        reviews.remove(review);
    }

    /**
     * Obtém o identificador único do usuário.
     *
     * @return O ID do usuário.
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return O nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém o nome de usuário do usuário.
     *
     * @return O nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return A senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtém a lista de títulos favoritos do usuário.
     *
     * @return A lista de títulos favoritos.
     */
    public ArrayList<Title> getFavorites() {
        return favorites;
    }

    /**
     * Define a lista de títulos favoritos do usuário.
     *
     * @param favorites A nova lista de títulos favoritos.
     */
    public void setFavorites(ArrayList<Title> favorites) {
        this.favorites = favorites;
    }

    /**
     * Define a lista de revisões do usuário.
     *
     * @param reviews A nova lista de revisões.
     */
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Obtém a lista de revisões feitas pelo usuário.
     *
     * @return A lista de revisões.
     */
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    /**
     * Obtém a lista de títulos assistidos pelo usuário.
     *
     * @return A lista de títulos assistidos.
     */
    public ArrayList<Title> getWatched() {
        return watched;
    }

    /**
     * Define a lista de títulos assistidos pelo usuário.
     *
     * @param watched A nova lista de títulos assistidos.
     */
    public void setWatched(ArrayList<Title> watched) {
        this.watched = watched;
    }
}

