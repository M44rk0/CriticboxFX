package com.m44rk0.criticboxfx.dao;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReviewDAO {

    private final Connection connection;

    public ReviewDAO() {
        this.connection = DatabaseConnection.getConnection();

    }

    public void addReview(Review review) {
        if (review instanceof EpisodeReview) {
            addEpisodeReview((EpisodeReview) review);

        } else if (review instanceof TitleReview) {
            addTitleReview((TitleReview) review);
        }
    }

    public void removeReview(Review review){

        String sql = "DELETE FROM Review WHERE review_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getReviewID());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    public void editReview(Review review){

        String sql = "UPDATE review SET review_note = ?, review_text = ? WHERE review_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getReviewNote());
            stmt.setString(2, review.getReviewText());
            stmt.setInt(3, review.getReviewID());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    public void addReviewToBaseTable(Review review){

        String sql = "INSERT INTO  Review (review_id, review_date, review_note, review_text, title_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getReviewID());
            stmt.setTimestamp(2, new java.sql.Timestamp(review.getReviewDate().getTime()));
            stmt.setInt(3, review.getReviewNote());
            stmt.setString(4, review.getReviewText());
            stmt.setInt(5, review.getTitle().getTitleId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    private void addEpisodeReview(EpisodeReview episodeReview){
        addReviewToBaseTable(episodeReview);

        String sql = "INSERT INTO  EpisodeReview (review_id, season_number, episode_name) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, episodeReview.getReviewID());
            stmt.setInt(2, episodeReview.getSeasonNumber());
            stmt.setString(3, episodeReview.getEpisodeName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    private void addTitleReview(TitleReview titleReview){
        addReviewToBaseTable(titleReview);

        String sql = "INSERT INTO TitleReview (review_id) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, titleReview.getReviewID());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

    }
}



