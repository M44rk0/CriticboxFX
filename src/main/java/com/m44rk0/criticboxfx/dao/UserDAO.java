package com.m44rk0.criticboxfx.dao;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.model.title.*;
import com.m44rk0.criticboxfx.model.user.User;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.DatabaseConnection;
import javafx.scene.image.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.m44rk0.criticboxfx.App.reviewDAO;
import static com.m44rk0.criticboxfx.App.titleDAO;
import static com.m44rk0.criticboxfx.service.Search.titlePosterCache;

/**
 * Gerencia as operações de acesso a dados relacionadas aos usuários no banco de dados.
 * Inclui métodos para verificar a existência de um usuário, recuperar um usuário por suas credenciais,
 * adicionar e remover favoritos e títulos assistidos, adicionar e remover avaliações, e recuperar listas de favoritos,
 * títulos assistidos e avaliações de um usuário.
 */
public class UserDAO {

    private final Connection connection;

    /**
     * Construtor para criar uma instância de TitleDAO.
     * Estabelece a conexão com o banco de dados usando a classe {@link DatabaseConnection}.
     */
    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Verifica se um usuário com o nome de usuário fornecido existe no banco de dados.
     *
     * @param username O nome de usuário a ser verificado.
     * @return {@code true} se o usuário existir, {@code false} caso contrário.
     */
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return false;
    }

    /**
     * Recupera um usuário a partir das credenciais fornecidas.
     *
     * @param username O nome de usuário do usuário a ser recuperado.
     * @param password A senha do usuário a ser recuperado.
     * @return O objeto {@link User} correspondente às credenciais fornecidas, ou {@code null} se não encontrado.
     */
    public User getUserByCredentials(String username, String password) {
        String sql = "SELECT user_id, name, username, password FROM User WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String retrievedUsername = rs.getString("username");
                    String retrievedPassword = rs.getString("password");

                    return new User(userId, name, retrievedUsername, retrievedPassword);
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return null;
    }

    /**
     * Adiciona um novo usuário ao banco de dados.
     *
     * @param user O usuário a ser adicionado.
     */
    public void addUser(User user) {

        String sql = "INSERT INTO User (name, username, password) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }


    /**
     * Adiciona um título à lista de favoritos de um usuário.
     *
     * @param user O usuário que está adicionando o título aos favoritos.
     * @param title O título a ser adicionado aos favoritos.
     */
    public void addFavorite(User user, Title title) {

        String sql = "INSERT INTO UserFavorites (user_id, title_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Remove um título da lista de favoritos de um usuário.
     *
     * @param user O usuário que está removendo o título dos favoritos.
     * @param title O título a ser removido dos favoritos.
     */
    public void removeFavorite(User user, Title title) {

        String sql = "DELETE FROM UserFavorites WHERE user_id = ? AND title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Adiciona um título à lista de títulos assistidos de um usuário.
     *
     * @param user O usuário que está adicionando o título à lista de assistidos.
     * @param title O título a ser adicionado à lista de assistidos.
     */
    public void addWatched(User user, Title title){

        String sql = "INSERT INTO UserWatched (user_id, title_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Remove um título da lista de títulos assistidos de um usuário.
     *
     * @param user O usuário que está removendo o título da lista de assistidos.
     * @param title O título a ser removido da lista de assistidos.
     */
    public void removeWatched(User user, Title title){

        String sql = "DELETE FROM UserWatched WHERE user_id = ? AND title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Adiciona uma avaliação feita por um usuário.
     *
     * @param user O usuário que está adicionando a avaliação.
     * @param review A avaliação a ser adicionada.
     */
    public void addReview(User user, Review review){
        reviewDAO.addReview(review);

        String sql = "INSERT INTO UserReviews (user_id, review_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, review.getReviewID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Remove uma avaliação feita por um usuário.
     *
     * @param user O usuário que está removendo a avaliação.
     * @param review A avaliação a ser removida.
     */
    public void removeReview(User user, Review review){
        reviewDAO.removeReview(review);

        String sql = "DELETE FROM UserReviews WHERE user_id = ? AND review_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, review.getReviewID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Recupera a lista de títulos favoritos de um usuário.
     *
     * @param user O usuário cujos favoritos serão recuperados.
     * @return Uma lista de títulos favoritos do usuário.
     */
    public ArrayList<Title> getFavoritesByUser(User user) {
        ArrayList<Title> favorites = new ArrayList<>();

        String sql = "SELECT t.title_id, t.name, t.overview, t.poster_path, t.release_date, t.duration, t.popularity, t.type, " + "ts.total_episodes " +
                "FROM title t " +
                "LEFT JOIN tvshow ts ON t.title_id = ts.title_id " +
                "JOIN userfavorites uf ON t.title_id = uf.title_id " +
                "WHERE uf.user_id = ? " + "ORDER BY favoritedate";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int titleId = rs.getInt("title_id");
                    String name = rs.getString("name");
                    String overview = rs.getString("overview");
                    String posterPath = rs.getString("poster_path");
                    String releaseDate = String.valueOf(rs.getDate("release_date"));
                    int duration = rs.getInt("duration");
                    double popularity = rs.getDouble("popularity");
                    String titleType = rs.getString("type");
                    int totalEpisodes = rs.getInt("total_episodes");

                    Title title;

                    if ("FILM".equals(titleType)) {
                        title = new Film(titleId, name, overview, posterPath, releaseDate, duration, popularity);
                    } else if ("TVSHOW".equals(titleType)) {
                        title = titleDAO.createTvShowWithSeasonsAndEpisodes(titleId, name, overview, posterPath,
                                releaseDate, duration, popularity, totalEpisodes);
                    } else {
                        throw new IllegalArgumentException("Unknown title type: " + titleType);
                    }

                    Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                            title.getPosterPath(), 250, 350, false, false);

                    titlePosterCache.put(title, posterImage);
                    favorites.add(title);
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return favorites;
    }

    /**
     * Recupera a lista de títulos assistidos de um usuário.
     *
     * @param user O usuário cujos títulos assistidos serão recuperados.
     * @return Uma lista de títulos assistidos do usuário.
     */
    public ArrayList<Title> getWatchedByUser(User user) {
        ArrayList<Title> watched = new ArrayList<>();

        String sql = "SELECT t.title_id, t.name, t.overview, t.poster_path, t.release_date, t.duration, t.popularity, t.type, " +
                "ts.total_episodes " +
                "FROM title t " +
                "LEFT JOIN tvshow ts ON t.title_id = ts.title_id " +
                "JOIN userwatched uf ON t.title_id = uf.title_id " +
                "WHERE uf.user_id = ? " + "ORDER BY watcheddate";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    int titleId = rs.getInt("title_id");
                    String name = rs.getString("name");
                    String overview = rs.getString("overview");
                    String posterPath = rs.getString("poster_path");
                    String releaseDate = String.valueOf(rs.getDate("release_date"));
                    int duration = rs.getInt("duration");
                    double popularity = rs.getDouble("popularity");
                    String titleType = rs.getString("type");
                    int totalEpisodes = rs.getInt("total_episodes");

                    Title title;

                    if ("FILM".equals(titleType)) {
                        title = new Film(titleId, name, overview, posterPath, releaseDate, duration, popularity);
                    } else if ("TVSHOW".equals(titleType)) {
                        title = titleDAO.createTvShowWithSeasonsAndEpisodes(titleId, name, overview, posterPath,
                                releaseDate, duration, popularity, totalEpisodes);
                    } else {
                        throw new IllegalArgumentException("Unknown title type: " + titleType);
                    }

                    Image posterImage = new Image("https://image.tmdb.org/t/p/w500/"
                            + title.getPosterPath(), 250, 350, false, false);

                    titlePosterCache.put(title, posterImage);
                    watched.add(title);
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return watched;
    }

    /**
     * Recupera a lista de avaliações feitas por um usuário.
     *
     * @param user O usuário cujas avaliações serão recuperadas.
     * @return Uma lista de avaliações feitas pelo usuário.
     */
    public ArrayList<Review> getReviewsByUser(User user) {
        ArrayList<Review> reviews = new ArrayList<>();

        String sql = "SELECT r.review_id, r.review_date, r.review_note, r.review_text, r.title_id, t.type AS title_type, " +
                "CASE WHEN er.review_id IS NOT NULL THEN 'EpisodeReview' WHEN tr.review_id IS NOT NULL THEN 'TitleReview' " +
                "ELSE 'Unknown' END AS review_type, " + "er.season_number, er.episode_name, " +
                "t.name, t.overview, t.poster_path, t.release_date, t.duration, t.popularity, " + "ts.total_episodes " +
                "FROM review r " + "LEFT JOIN episodereview er ON r.review_id = er.review_id LEFT JOIN titlereview tr ON r.review_id = tr.review_id " +
                "JOIN userreviews ur ON r.review_id = ur.review_id JOIN title t ON r.title_id = t.title_id " +
                "LEFT JOIN tvshow ts ON t.title_id = ts.title_id WHERE ur.user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int reviewId = rs.getInt("review_id");
                    Date reviewDate = new Date(rs.getTimestamp("review_date").getTime());
                    int reviewNote = rs.getInt("review_note");
                    String reviewText = rs.getString("review_text");
                    int titleId = rs.getInt("title_id");
                    String reviewType = rs.getString("review_type");
                    String titleType = rs.getString("title_type");

                    String name = rs.getString("name");
                    String overview = rs.getString("overview");
                    String posterPath = rs.getString("poster_path");
                    String releaseDate = String.valueOf(rs.getDate("release_date"));
                    int duration = rs.getInt("duration");
                    double popularity = rs.getDouble("popularity");
                    int totalEpisodes = rs.getInt("total_episodes");

                    Title title;
                    if ("FILM".equals(titleType)) {
                        title = new Film(titleId, name, overview, posterPath, releaseDate, duration, popularity);
                    } else if ("TVSHOW".equals(titleType)) {
                        title = titleDAO.createTvShowWithSeasonsAndEpisodes(titleId, name, overview, posterPath, releaseDate, duration, popularity, totalEpisodes);
                    } else {
                        throw new IllegalArgumentException("Unknown title type: " + titleType);
                    }

                    if (!titlePosterCache.containsKey(title)) {
                        Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" + title.getPosterPath(), 250, 350, false, false);
                        titlePosterCache.put(title, posterImage);
                    }

                    Review review;
                    if ("TitleReview".equals(reviewType)) {
                        review = new TitleReview(reviewId, title, reviewNote, reviewDate, reviewText);
                    } else if ("EpisodeReview".equals(reviewType)) {
                        int seasonNumber = rs.getInt("season_number");
                        String episodeName = rs.getString("episode_name");
                        review = new EpisodeReview(reviewId, title, reviewNote, reviewDate, reviewText, seasonNumber, episodeName);
                        assert title instanceof TvShow;
                        ((EpisodeReview) review).setSeason(((TvShow) title).getSeasonByNumber(seasonNumber));
                    } else {
                        throw new IllegalArgumentException("Unknown review type: " + reviewType);
                    }

                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return reviews;
    }
}
