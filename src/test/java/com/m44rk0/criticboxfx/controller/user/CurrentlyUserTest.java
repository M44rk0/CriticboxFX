package com.m44rk0.criticboxfx.controller.user;

import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.model.title.Film;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.model.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link CurrentlyUser}.
 * Testa as funcionalidades relacionadas à adição, remoção e manipulação de favoritos, títulos assistidos e avaliações do usuário atual.
 */
public class CurrentlyUserTest {

    private Film film1;
    private TvShow tvShow1;
    private Review review1;
    private Review review2;

    /**
     * Configuração inicial para os testes.
     * Cria um usuário e inicializa filmes, programas de TV e avaliações para os testes.
     */
    @BeforeEach
    void setUp() {
        User user = new User("John Doe", "johndoe", "password123");
        CurrentlyUser.setUser(user);

        film1 = new Film(1, "Filme Teste 1", "Teste de Descrição 1", "poster1.jpg", "2024-07-19", 67, 9.7);
        tvShow1 = new TvShow(1, "Show Teste 1", 25, "poster1.jpg", "2024-07-19", "2024-07-19", 5.7, 80);

        review1 = new TitleReview(film1, 5, new Date(), "Bom!");
        review2 = new EpisodeReview(tvShow1, 5, new Date(), "Boa tbm!", 1, "Episódio teste");
    }

    /**
     * Testa a adição de favoritos.
     * Verifica se filmes e programas de TV são corretamente adicionados à lista de favoritos do usuário.
     */
    @Test
    void testAddFavorite() {
        CurrentlyUser.addFavorite(film1);
        assertTrue(CurrentlyUser.getFavorites().contains(film1));
        CurrentlyUser.addFavorite(tvShow1);
        assertTrue(CurrentlyUser.getFavorites().contains(tvShow1));
    }

    /**
     * Testa a remoção de favoritos.
     * Verifica se filmes e programas de TV são corretamente removidos da lista de favoritos do usuário.
     */
    @Test
    void testRemoveFavorite() {
        CurrentlyUser.addFavorite(film1);
        CurrentlyUser.removeFavorite(film1);
        assertFalse(CurrentlyUser.getFavorites().contains(film1));
        CurrentlyUser.addFavorite(tvShow1);
        CurrentlyUser.removeFavorite(tvShow1);
        assertFalse(CurrentlyUser.getFavorites().contains(tvShow1));
    }

    /**
     * Testa a adição de títulos assistidos.
     * Verifica se filmes e programas de TV são corretamente adicionados à lista de títulos assistidos do usuário.
     */
    @Test
    void testAddWatched() {
        CurrentlyUser.addWatched(film1);
        assertTrue(CurrentlyUser.getWatched().contains(film1));
        CurrentlyUser.addWatched(tvShow1);
        assertTrue(CurrentlyUser.getWatched().contains(tvShow1));
    }

    /**
     * Testa a remoção de títulos assistidos.
     * Verifica se filmes e programas de TV são corretamente removidos da lista de títulos assistidos do usuário.
     */
    @Test
    void testRemoveWatched() {
        CurrentlyUser.addWatched(film1);
        CurrentlyUser.removeWatched(film1);
        assertFalse(CurrentlyUser.getWatched().contains(film1));
        CurrentlyUser.addWatched(tvShow1);
        CurrentlyUser.removeWatched(tvShow1);
        assertFalse(CurrentlyUser.getWatched().contains(tvShow1));
    }

    /**
     * Testa a adição de avaliações.
     * Verifica se as avaliações são corretamente adicionadas à lista de avaliações do usuário.
     */
    @Test
    void testAddReview() {
        CurrentlyUser.addReview(review1);
        assertTrue(CurrentlyUser.getReviews().contains(review1));
        CurrentlyUser.addReview(review2);
        assertTrue(CurrentlyUser.getReviews().contains(review2));
    }

    /**
     * Testa a remoção de avaliações.
     * Verifica se as avaliações são corretamente removidas da lista de avaliações do usuário.
     */
    @Test
    void testRemoveReview() {
        CurrentlyUser.addReview(review1);
        CurrentlyUser.removeReview(review1);
        assertFalse(CurrentlyUser.getReviews().contains(review1));
        CurrentlyUser.addReview(review2);
        CurrentlyUser.removeReview(review2);
        assertFalse(CurrentlyUser.getReviews().contains(review2));
    }

    /**
     * Testa a definição de favoritos.
     * Verifica se a lista de favoritos do usuário é corretamente atualizada com uma nova lista.
     */
    @Test
    void testSetFavorites() {
        ArrayList<Film> favorites = new ArrayList<>();
        favorites.add(film1);
        CurrentlyUser.setFavorites(new ArrayList<>(favorites));
        assertEquals(favorites, CurrentlyUser.getFavorites());
    }

    /**
     * Testa a definição de títulos assistidos.
     * Verifica se a lista de títulos assistidos do usuário é corretamente atualizada com uma nova lista.
     */
    @Test
    void testSetWatched() {
        ArrayList<Film> watched = new ArrayList<>();
        watched.add(film1);
        CurrentlyUser.setWatched(new ArrayList<>(watched));
        assertEquals(watched, CurrentlyUser.getWatched());
    }

    /**
     * Testa a definição de avaliações.
     * Verifica se a lista de avaliações do usuário é corretamente atualizada com uma nova lista.
     */
    @Test
    void testSetReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        CurrentlyUser.setReviews(new ArrayList<>(reviews));
        assertEquals(reviews, CurrentlyUser.getReviews());
    }
}

