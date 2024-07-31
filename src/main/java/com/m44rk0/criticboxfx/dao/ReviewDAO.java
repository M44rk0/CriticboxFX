package com.m44rk0.criticboxfx.dao;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.DatabaseConnection;

import java.sql.*;

/**
 * Classe de Data Access Object (DAO) para gerenciar operações de Review no banco de dados.
 * Esta classe fornece métodos para adicionar, remover, editar e persistir revisões de filmes e séries.
 */
public class ReviewDAO {

    private final Connection connection;

    /**
     * Construtor para criar uma instância de ReviewDAO.
     * Estabelece a conexão com o banco de dados usando a classe {@link DatabaseConnection}.
     */
    public ReviewDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Adiciona uma Review ao banco de dados. Dependendo do tipo da Review (episódio ou título),
     * a Review é adicionada à tabela correspondente.
     *
     * @param review A Review a ser adicionada. Pode ser uma instância de {@link EpisodeReview} ou {@link TitleReview}.
     */
    public void addReview(Review review) {
        if (review instanceof EpisodeReview) {
            addEpisodeReview((EpisodeReview) review);
        } else if (review instanceof TitleReview) {
            addTitleReview((TitleReview) review);
        }
    }

    /**
     * Remove uma Review do banco de dados com base no ID da Review.
     *
     * @param review A Review a ser removida. Deve ter um ID válido.
     */
    public void removeReview(Review review) {
        String sql = "DELETE FROM Review WHERE review_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getReviewID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Edita uma Review existente no banco de dados.
     *
     * @param review A Review a ser editada. Deve ter um ID válido.
     */
    public void editReview(Review review) {
        String sql = "UPDATE review SET review_note = ?, review_text = ? WHERE review_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getReviewNote());
            stmt.setString(2, review.getReviewText());
            stmt.setInt(3, review.getReviewID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Adiciona uma Review à tabela base de revisões. Este método é chamado por {@link #addEpisodeReview(EpisodeReview)}}
     * e {@link #addTitleReview(TitleReview)} para garantir que a Review seja inserida na tabela base.
     *
     * @param review A Review a ser adicionada.
     */
    public void addReviewToBaseTable(Review review) {
        String sql = "INSERT INTO Review (review_date, review_note, review_text, title_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(review.getReviewDate().getTime()));
            stmt.setInt(2, review.getReviewNote());
            stmt.setString(3, review.getReviewText());
            stmt.setInt(4, review.getTitle().getTitleId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int reviewId = generatedKeys.getInt(1);
                    review.setReviewID(reviewId);
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Adiciona uma Review de episódio ao banco de dados. Primeiro, insere a Review na tabela base
     * e depois insere detalhes específicos na tabela de revisões de episódios.
     *
     * @param episodeReview A Review do episódio a ser adicionada.
     */
    private void addEpisodeReview(EpisodeReview episodeReview) {
        addReviewToBaseTable(episodeReview);

        String sql = "INSERT INTO EpisodeReview (review_id, season_number, episode_name) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, episodeReview.getReviewID());
            stmt.setInt(2, episodeReview.getSeason().getSeasonNumber());
            stmt.setString(3, episodeReview.getEpisodeName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    /**
     * Adiciona uma Review de título ao banco de dados. Primeiro, insere a Review na tabela base
     * e depois insere detalhes específicos na tabela de revisões de títulos.
     *
     * @param titleReview A Review do título a ser adicionada.
     */
    private void addTitleReview(TitleReview titleReview) {
        addReviewToBaseTable(titleReview);

        String sql = "INSERT INTO TitleReview (review_id) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, titleReview.getReviewID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }
}




