package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.Search;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TmdbApi;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.m44rk0.criticboxfx.controller.MainController.seasonPosterCache;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Representa uma série de TV, estendendo a classe abstrata Title.
 */
public class TvShow extends Title {

    /**
     * A lista de temporadas da série de TV.
     */
    private ArrayList<Season> seasons;

    /**
     * O número total de episódios da série de TV.
     */
    private Integer totalEpisodes;

    /**
     * Construtor que inicializa um objeto TvShow usando dados de um objeto TvSeriesDb da classe Wrapper do TmdbAPI.
     *
     * @param tvshow O objeto TvSeriesDb contendo os dados da série de TV.
     * @throws TmdbException Se houver um erro ao acessar os dados da série de TV.
     */
    public TvShow(TvSeriesDb tvshow) throws TmdbException {
        this.titleId = tvshow.getId();
        this.name = tvshow.getName();
        this.duration = 0;
        this.overview = tvshow.getOverview();
        this.posterPath = tvshow.getPosterPath();
        this.releaseDate = tvshow.getFirstAirDate();
        this.popularity = tvshow.getPopularity();
        this.seasons = getTvShowSeasons(tvshow);
        this.totalEpisodes = getEpisodes();
    }

    /**
     * Construtor que inicializa um objeto TvShow com valores especificados.
     *
     * @param titleId       O identificador único do título.
     * @param name          O nome da série de TV.
     * @param duration      A duração da série de TV em minutos.
     * @param overview      A visão geral da série de TV.
     * @param posterPath    O caminho do pôster da série de TV.
     * @param releaseDate   A data de lançamento da série de TV.
     * @param popularity    A popularidade da série de TV.
     * @param totalEpisodes O número total de episódios da série de TV.
     */
    public TvShow(Integer titleId, String name, Integer duration, String overview, String posterPath, String releaseDate, Double popularity, Integer totalEpisodes) {
        this.titleId = titleId;
        this.name = name;
        this.duration = duration;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.totalEpisodes = totalEpisodes;
        this.seasons = new ArrayList<>();
    }

    /**
     * Construtor padrão para criar um objeto TvShow vazio.
     */
    public TvShow() {
    }

    /**
     * Obtém a lista de temporadas da série de TV.
     *
     * @return A lista de temporadas.
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * Obtém uma temporada específica pelo número da temporada.
     *
     * @param seasonNumber O número da temporada.
     * @return A temporada correspondente ao número, ou {@code null} se não encontrada.
     */
    public Season getSeasonByNumber(Integer seasonNumber) {
        for (Season season : seasons) {
            if (Objects.equals(season.getSeasonNumber(), seasonNumber)) {
                return season;
            }
        }
        return null;
    }

    /**
     * Obtém uma lista de números de todas as temporadas.
     *
     * @return A lista de números das temporadas.
     */
    public ArrayList<Integer> getAllSeasons() {
        return seasons.stream()
                .map(Season::getSeasonNumber)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Obtém o número total de episódios da série de TV.
     *
     * @return O número total de episódios.
     */
    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }

    /**
     * Define o número total de episódios da série de TV.
     *
     * @param totalEpisodes O novo número total de episódios.
     */
    public void setTotalEpisodes(Integer totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    /**
     * Define a lista de temporadas da série de TV.
     *
     * @param seasons A nova lista de temporadas.
     */
    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    /**
     * Método privado para obter as temporadas da série de TV usando dados de um objeto TvSeriesDb.
     *
     * @param seriesDb O objeto TvSeriesDb contendo os dados da série de TV.
     * @return A lista de temporadas.
     * @throws TmdbException Se houver um erro ao acessar os dados das temporadas.
     */
    private ArrayList<Season> getTvShowSeasons(TvSeriesDb seriesDb) throws TmdbException {
        TmdbApi apiKey = new TmdbApi(new TitleSearcher().getAPI_KEY());
        TvSeriesDb serie = apiKey.getTvSeries().getDetails(seriesDb.getId(), "pt-BR");

        ArrayList<Season> seasons = new ArrayList<>();
        TmdbTvSeasons tvSeasons = apiKey.getTvSeasons();

        for (int i = 0; i < serie.getSeasons().size(); i++) {
            String seasonAirDate = serie.getSeasons().get(i).getAirDate();

            if (Search.isReleased(seasonAirDate)) {
                Integer seasonNumber = serie.getSeasons().get(i).getSeasonNumber();
                String seasonPosterPath = serie.getSeasons().get(i).getPosterPath();
                ArrayList<Episode> episodes = new ArrayList<>();

                if (seasonNumber != 0) {
                    TvSeasonDb tvSeasonDb = tvSeasons.getDetails(seriesDb.getId(), seasonNumber, "pt-BR");

                    for (int j = 0; j < tvSeasonDb.getEpisodes().size(); j++) {
                        String episodeAirDate = tvSeasonDb.getEpisodes().get(j).getAirDate();

                        if (Search.isReleased(episodeAirDate) && tvSeasonDb.getEpisodes().get(j).getRuntime() != null) {
                            Episode episode = new Episode(tvSeasonDb.getEpisodes().get(j).getName(), tvSeasonDb.getEpisodes().get(j).getRuntime());
                            episodes.add(episode);
                        }
                    }

                    Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                            seasonPosterPath, 250, 350, false, false);

                    Season season = new Season(seasonNumber, episodes, seasonPosterPath);
                    seasonPosterCache.put(season, posterImage);

                    seasons.add(season);
                }
            }
        }
        return seasons;
    }

    /**
     * Método privado para calcular o número total de episódios da série de TV.
     *
     * @return O número total de episódios.
     */
    private Integer getEpisodes() {
        return seasons.stream()
                .mapToInt(s -> s.getEpisodeList().size())
                .sum();
    }
}

