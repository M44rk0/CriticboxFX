package com.m44rk0.criticboxfx.model.user;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.*;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.DatabaseConnection;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.m44rk0.criticboxfx.App.reviewDAO;
import static com.m44rk0.criticboxfx.App.titleDAO;
import static com.m44rk0.criticboxfx.controller.MainController.titlePosterCache;

public class UserDAO {

    private final Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addUser(User user) {

        String sql = "INSERT INTO User (name, username, password) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    // Método para adicionar um título favorito para um usuário
    public void addFavorite(User user, Title title) {

        String sql = "INSERT INTO UserFavorites (user_id, title_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    // Método para remover um título favorito de um usuário
    public void removeFavorite(User user, Title title) {
        String sql = "DELETE FROM UserFavorites WHERE user_id = ? AND title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    // Método para adicionar um título assistido para um usuário
    public void addWatched(User user, Title title){

        String sql = "INSERT INTO UserWatched (user_id, title_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    // Método para remover um título assistido de um usuário
    public void removeWatched(User user, Title title){

        String sql = "DELETE FROM UserWatched WHERE user_id = ? AND title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, title.getTitleId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    // Método para adicionar um título favorito para um usuário
    public void addReview(User user, Review review){
        reviewDAO.addReview(review);

        String sql = "INSERT INTO UserReviews (user_id, review_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, review.getReviewID());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    // Método para remover um título favorito de um usuário
    public void removeReview(User user, Review review){
        reviewDAO.removeReview(review);

        String sql = "DELETE FROM UserReviews WHERE user_id = ? AND review_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, review.getReviewID());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    //Metodo para buscar a lista de favoritos de um usuário
    public ArrayList<Title> getFavoritesByUser(User user) {
        ArrayList<Title> favorites = new ArrayList<>();

        String sql = "SELECT t.title_id, t.name, t.overview, t.poster_path, t.release_date, t.duration, t.popularity, t.type, " +
                "       ts.total_episodes " +
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
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return favorites;
    }

    //Metodo para buscar a lista de assistidos de um usuário
    public ArrayList<Title> getWatchedByUser(User user) {
        ArrayList<Title> watched = new ArrayList<>();

        String sql = "SELECT t.title_id, t.name, t.overview, t.poster_path, t.release_date, t.duration, t.popularity, t.type, " +
                "       ts.total_episodes " +
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
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return watched;
    }

    //Metodo para buscar a lista de reviews de um usuário
    public ArrayList<Review> getReviewsByUser(User user) throws TmdbException {
        ArrayList<Review> reviews = new ArrayList<>();

        String sql = "SELECT r.review_id, r.review_date, r.review_note, r.review_text, r.title_id, t.type AS title_type, " +
                "CASE WHEN er.review_id IS NOT NULL THEN 'EpisodeReview' " +
                "     WHEN tr.review_id IS NOT NULL THEN 'TitleReview' " +
                "     ELSE 'Unknown' END AS review_type, " +
                "er.season_number, er.episode_name " +
                "FROM review r " +
                "LEFT JOIN episodereview er ON r.review_id = er.review_id " +
                "LEFT JOIN titlereview tr ON r.review_id = tr.review_id " +
                "JOIN userreviews ur ON r.review_id = ur.review_id " +
                "JOIN title t ON r.title_id = t.title_id " +
                "WHERE ur.user_id = ?";

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

                    TitleSearcher titleSearcher = new TitleSearcher();
                    Title title;

                    if ("FILM".equals(titleType)) {
                        title = titleSearcher.searchMovieById(titleId);
                    } else if ("TVSHOW".equals(titleType)) {
                        title = titleSearcher.searchTvShowById(titleId);
                    } else {
                        throw new IllegalArgumentException("Unknown title type: " + titleType);
                    }

                    Review review;
                    if ("TitleReview".equals(reviewType)) {
                        review = new TitleReview(reviewId, title, reviewNote, reviewDate, reviewText);
                    } else if ("EpisodeReview".equals(reviewType)) {
                        int seasonNumber = rs.getInt("season_number");
                        String episodeName = rs.getString("episode_name");
                        review = new EpisodeReview(reviewId, title, reviewNote, reviewDate, reviewText, seasonNumber, episodeName);
                        ((EpisodeReview) review).setSeason(((TvShow) title).getSeasonByNumber(seasonNumber));
                    } else {
                        throw new IllegalArgumentException("Unknown review type: " + reviewType);
                    }

                    reviews.add(review);
                }
            }
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return reviews;
    }
}
