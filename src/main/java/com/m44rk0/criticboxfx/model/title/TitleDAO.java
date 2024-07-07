package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.Search;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.DatabaseConnection;
import info.movito.themoviedbapi.tools.TmdbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TitleDAO {

    private final Connection connection;

    public TitleDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Integer> getAllTitleIds() throws SQLException {
        List<Integer> titleIds = new ArrayList<>();
        String sql = "SELECT title_id FROM Title";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int titleId = rs.getInt("title_id");
                titleIds.add(titleId);
            }
        }

        return titleIds;
    }

    public void clearLastResults() {
        String sql = "DELETE FROM lastresults";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    public void addTitleToLastResults(Title title) {
        String sql = "INSERT INTO lastresults (title_id) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, title.getTitleId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    public List<Title> getLastSearchedTitles(){
        ArrayList<Title> lastSearchedTitles = new ArrayList<>();
        TitleSearcher titleSearcher = new TitleSearcher();

        String sql = "SELECT lr.title_id, t.type " +
                "FROM lastresults lr " +
                "JOIN title t ON lr.title_id = t.title_id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int titleId = rs.getInt("title_id");
                String titleType = rs.getString("type");

                Title title = null;
                if ("FILM".equals(titleType)) {
                    title = titleSearcher.searchMovieById(titleId);
                } else if ("TVSHOW".equals(titleType)) {
                    title = titleSearcher.searchTvShowById(titleId);
                }

                if (title != null) {
                    lastSearchedTitles.add(title);
                }
            }
        } catch (SQLException | TmdbException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        Search.sortByPopularity(lastSearchedTitles);

        return lastSearchedTitles;
    }

    public void addTitle(Title title){
        if (title instanceof Film) {
            addFilm((Film) title);
        } else if (title instanceof TvShow) {
            addTvShow((TvShow) title);
        }
    }

    public void removeTitle(Title title){
        if (title instanceof Film) {
            removeFilm(title.getTitleId());
        }
        else if (title instanceof TvShow) {
            removeTvShow(title.getTitleId());
        }
        removeTitleFromBaseTable(title.getTitleId());
    }

    private void removeFilm(int titleId){
        String sql = "DELETE FROM Film WHERE title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, titleId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    private void removeTvShow(int titleId){
        String sql = "DELETE FROM TvShow WHERE title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, titleId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    private void removeTitleFromBaseTable(int titleId){
        String sql = "DELETE FROM Title WHERE title_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, titleId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }


    private void addFilm(Film film){
        addTitleToBaseTable(film);

        String sql = "INSERT INTO Film (title_id) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, film.getTitleId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    private void addTvShow(TvShow tvShow){
        addTitleToBaseTable(tvShow);

        String sql = "INSERT INTO TvShow (title_id) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tvShow.getTitleId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        for (Season season : tvShow.getSeasons()) {
            addSeason(season, tvShow.getTitleId());
        }
    }

    private void addTitleToBaseTable(Title title){
        String sql = "INSERT INTO Title (title_id, name, overview, poster_path, release_date, duration, popularity, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, title.getTitleId());
            stmt.setString(2, title.getName());
            stmt.setString(3, title.getOverview());
            stmt.setString(4, title.getPosterPath());
            stmt.setString(5, title.getReleaseDate());
            stmt.setInt(6, title.getDuration());
            stmt.setDouble(7, title.getPopularity());

            if (title instanceof Film) {
                stmt.setString(8, "FILM");
            } else if (title instanceof TvShow) {
                stmt.setString(8, "TVSHOW");
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }

    public void addSeason(Season season, int tvShowId) {
        String sqlInsertSeason = "INSERT INTO Season (season_number, season_poster_path, tvshow_id) VALUES (?, ?, ?)";
        String sqlInsertEpisode = "INSERT INTO Episode (episode_name, episode_runtime, season_id) VALUES (?, ?, ?)";

        try {
            try (PreparedStatement stmtSeason = connection.prepareStatement(sqlInsertSeason, Statement.RETURN_GENERATED_KEYS)) {
                stmtSeason.setInt(1, season.getSeasonNumber());
                stmtSeason.setString(2, season.getSeasonPosterPath());
                stmtSeason.setInt(3, tvShowId);

                stmtSeason.executeUpdate();

                try (ResultSet generatedKeys = stmtSeason.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int seasonId = generatedKeys.getInt(1);

                        for (Episode episode : season.getEpisodes()) {
                            try (PreparedStatement stmtEpisode = connection.prepareStatement(sqlInsertEpisode)) {
                                stmtEpisode.setString(1, episode.getEpisodeName());
                                stmtEpisode.setInt(2, episode.getEpisodeRuntime());
                                stmtEpisode.setInt(3, seasonId);

                                stmtEpisode.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }
    }




}
