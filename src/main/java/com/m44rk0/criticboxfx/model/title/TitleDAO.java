package com.m44rk0.criticboxfx.model.title;
import com.m44rk0.criticboxfx.model.search.Search;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.DatabaseConnection;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.m44rk0.criticboxfx.controller.MainController.titlePosterCache;

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

        String sql = "SELECT t.title_id, t.name, t.overview, t.poster_path, t.release_date, t.duration, t.popularity, t.type, " +
                "       ts.total_episodes " +
                "FROM lastresults lr " +
                "JOIN title t ON lr.title_id = t.title_id " +
                "LEFT JOIN tvshow ts ON t.title_id = ts.title_id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
                        title = createTvShowWithSeasonsAndEpisodes(titleId, name, overview, posterPath, releaseDate, duration, popularity, totalEpisodes);
                    } else {
                        throw new IllegalArgumentException("Unknown title type: " + titleType);
                    }

                    if (!titlePosterCache.containsKey(title)) {
                        Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                title.getPosterPath(), 250, 350, false, false);
                        titlePosterCache.put(title, posterImage);
                    }

                    lastSearchedTitles.add(title);
                }
            }
        }
        catch (SQLException e) {
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

    //cria um TvShow com suas respectivas temporadas e episódios
    public TvShow createTvShowWithSeasonsAndEpisodes(int titleId, String name, String overview, String posterPath, String releaseDate, int duration, double popularity, int totalEpisodes){
        TvShow tvShow = new TvShow(titleId, name, duration, overview, posterPath, releaseDate, popularity, totalEpisodes);

        String seasonSql = "SELECT season_id, season_number, season_poster_path " +
                "FROM season WHERE tvshow_id = ?";
        try (PreparedStatement seasonStmt = connection.prepareStatement(seasonSql)) {
            seasonStmt.setInt(1, titleId);
            ArrayList<Season> seasons = new ArrayList<>();
            try (ResultSet seasonRs = seasonStmt.executeQuery()) {
                while (seasonRs.next()) {
                    int seasonNumber = seasonRs.getInt("season_number");
                    int seasonId = seasonRs.getInt("season_id");
                    String seasonPosterPath = seasonRs.getString("season_poster_path");
                    Season season = new Season(seasonNumber, seasonPosterPath);
                    season.setEpisodes(getEpisodesForSeason(seasonId));
                    seasons.add(season);
                }
                tvShow.setSeasons(seasons);
            }
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return tvShow;
    }

    //busca os episódios de uma temporada
    private ArrayList<Episode> getEpisodesForSeason(int seasonId) {
        ArrayList<Episode> episodes = new ArrayList<>();

        String episodeSql = "SELECT episode_name, episode_runtime " +
                "FROM episode WHERE season_id = ?";
        try (PreparedStatement episodeStmt = connection.prepareStatement(episodeSql)) {
            episodeStmt.setInt(1, seasonId);

            try (ResultSet episodeRs = episodeStmt.executeQuery()) {
                while (episodeRs.next()) {
                    String episodeName = episodeRs.getString("episode_name");
                    Integer episodeRuntime = episodeRs.getInt("episode_runtime");

                    Episode episode = new Episode(episodeName, episodeRuntime);
                    episodes.add(episode);
                }
            }
        }
        catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        return episodes;
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

        String sql = "INSERT INTO TvShow (title_id, total_episodes) VALUES (?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tvShow.getTitleId());
            stmt.setInt(2, tvShow.getTotalEpisodes());

            stmt.executeUpdate();
        } catch (SQLException e) {
            AlertMessage.showErrorAlert("SQL Error", e.getMessage());
        }

        for (Season season : tvShow.getSeasons()) {
            addSeason(season, tvShow.getTitleId());
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
