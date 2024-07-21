package com.m44rk0.criticboxfx.model.search;
import com.m44rk0.criticboxfx.model.title.Film;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.scene.image.Image;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.m44rk0.criticboxfx.controller.MainController.titlePosterCache;

/**
 * Classe responsável pela lógica de busca de títulos (filmes e séries) utilizando a API do TMDb e sua classe Wrapper.
 */
public class Search {

    /**
     * Formato de data utilizado no formato "yyyy-MM-dd".
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Busca todos os títulos válidos (filmes e séries) relacionados ao termo de busca fornecido.
     *
     * @param apiKey A chave da API para autenticação com o TMDb.
     * @param search O termo de busca para encontrar filmes e séries.
     * @return Uma lista de títulos encontrados que correspondem ao termo de busca.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public static ArrayList<Title> searchAll(String apiKey, String search) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);

        var searchMovie = api.getSearch().searchMovie(search, true, "pt-BR", null, 1, null, null).getResults();
        var searchTVShow = api.getSearch().searchTv(search, null, true, "pt-BR", 1, null).getResults();

        List<CompletableFuture<Title>> movieFutures = searchMovie.stream()
                .map(movie -> CompletableFuture.supplyAsync(() -> {
                    Title resultMovie;
                    try {
                        resultMovie = new Film(api.getMovies().getDetails(movie.getId(), "pt-BR"));
                        if (isValidMovie(resultMovie)) {
                            if (!titlePosterCache.containsKey(resultMovie)) {
                                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                        resultMovie.getPosterPath(), 250, 350, false, false);
                                titlePosterCache.put(resultMovie, posterImage);
                            }
                            return resultMovie;
                        }
                    } catch (TmdbException e) {
                        return null;
                    }
                    return null;
                }))
                .toList();

        List<CompletableFuture<Title>> tvShowFutures = searchTVShow.stream()
                .map(tvShow -> CompletableFuture.supplyAsync(() -> {
                    Title resultSerie;
                    try {
                        resultSerie = new TvShow(api.getTvSeries().getDetails(tvShow.getId(), "pt-BR"));
                        if (isValidTvShow(resultSerie)) {
                            if (!titlePosterCache.containsKey(resultSerie)) {
                                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                        resultSerie.getPosterPath(), 250, 350, false, false);
                                titlePosterCache.put(resultSerie, posterImage);
                            }
                            return resultSerie;
                        }
                    } catch (TmdbException e) {
                        return null;
                    }
                    return null;
                }))
                .toList();

        ArrayList<Title> results = new ArrayList<>();
        results.addAll(movieFutures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList());
        results.addAll(tvShowFutures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList());

        sortByPopularity(results);

        return results;
    }

    /**
     * Busca filmes relacionados ao termo de busca fornecido.
     *
     * @param apiKey A chave da API para autenticação com o TMDb.
     * @param search O termo de busca para encontrar filmes.
     * @return Uma lista de filmes encontrados que correspondem ao termo de busca.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public static ArrayList<Title> searchMovie(String apiKey, String search) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);
        var searchMovie = api.getSearch().searchMovie(search, false, "pt-BR", null, 1, null, null).getResults();
        List<CompletableFuture<Title>> movieFutures = searchMovie.stream()
                .map(movie -> CompletableFuture.supplyAsync(() -> {
                    Title resultMovie;
                    try {
                        resultMovie = new Film(api.getMovies().getDetails(movie.getId(), "pt-BR"));
                        if (isValidMovie(resultMovie)) {
                            if (!titlePosterCache.containsKey(resultMovie)) {
                                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                        resultMovie.getPosterPath(), 250, 350, false, false);
                                titlePosterCache.put(resultMovie, posterImage);
                            }
                            return resultMovie;
                        }
                    } catch (TmdbException e) {
                        return null;
                    }
                    return null;
                }))
                .toList();

        return new ArrayList<>(movieFutures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList());
    }

    /**
     * Busca séries relacionadas ao termo de busca fornecido.
     *
     * @param apiKey A chave da API para autenticação com o TMDb.
     * @param search O termo de busca para encontrar séries.
     * @return Uma lista de séries encontradas que correspondem ao termo de busca.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public static ArrayList<Title> searchTVShow(String apiKey, String search) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);
        var searchTVShow = api.getSearch().searchTv(search, null, false, "pt-BR", 1, null).getResults();

        List<Title> tvShowList = searchTVShow.stream()
                .map(tvShow -> {
                    Title resultSerie;
                    try {
                        resultSerie = new TvShow(api.getTvSeries().getDetails(tvShow.getId(), "pt-BR"));
                        if (isValidTvShow(resultSerie)) {
                            if (!titlePosterCache.containsKey(resultSerie)) {
                                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                        resultSerie.getPosterPath(), 250, 350, false, false);
                                titlePosterCache.put(resultSerie, posterImage);
                            }
                            return resultSerie;
                        }
                    } catch (TmdbException e) {
                        return null;
                    }
                    return null;
                })
                .toList();

        return new ArrayList<>(tvShowList.stream()
                .filter(Objects::nonNull)
                .toList());
    }

    /**
     * Busca um filme específico pelo ID fornecido.
     *
     * @param apiKey A chave da API para autenticação com o TMDb.
     * @param movieId O ID do filme a ser pesquisado.
     * @return O filme encontrado, ou {@code null} se não for válido.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public static Title searchMovieById(String apiKey, int movieId) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);
        Title resultMovie = new Film(api.getMovies().getDetails(movieId, "pt-BR"));
        if (isValidMovie(resultMovie)) {
            if (!titlePosterCache.containsKey(resultMovie)) {
                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                        resultMovie.getPosterPath(), 250, 350, false, false);
                titlePosterCache.put(resultMovie, posterImage);
            }
            return resultMovie;
        }
        return null;
    }

    /**
     * Busca uma série específica pelo ID fornecido.
     *
     * @param apiKey A chave da API para autenticação com o TMDb.
     * @param tvShowId O ID da série a ser pesquisada.
     * @return A série encontrada, ou {@code null} se não for válida.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public static Title searchTVShowById(String apiKey, int tvShowId) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);
        Title resultSerie = new TvShow(api.getTvSeries().getDetails(tvShowId, "pt-BR"));
        if (isValidTvShow(resultSerie)) {
            if (!titlePosterCache.containsKey(resultSerie)) {
                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                        resultSerie.getPosterPath(), 250, 350, false, false);
                titlePosterCache.put(resultSerie, posterImage);
            }
            return resultSerie;
        }
        return null;
    }

    /**
     * Ordena a lista de títulos pela popularidade em ordem decrescente.
     *
     * @param results A lista de títulos a ser ordenada.
     */
    public static void sortByPopularity(ArrayList<Title> results) {
        results.sort((t1, t2) -> {
            double popularity1 = t1.getPopularity();
            double popularity2 = t2.getPopularity();
            return Double.compare(popularity2, popularity1);
        });
    }

    /**
     * Antes de ser adicionado a lista de resultados, verifica se um filme encontrado tem seus princípais campos preenchidos corretamente.
     *
     * @param resultMovie O filme a ser verificado.
     * @return {@code true} se o filme for válido, {@code false} caso contrário.
     */
    protected static boolean isValidMovie(Title resultMovie) {
        if (resultMovie instanceof Film film) {
            return isReleased(film.getReleaseDate()) &&
                    film.getPosterPath() != null &&
                    film.getPopularity() != null &&
                    film.getOverview() != null &&
                    !film.getOverview().isEmpty();
        }
        return false;
    }

    /**
     * Antes de ser adicionado a lista de resultados, verifica se uma série encontrada tem seus princípais campos preenchidos corretamente.
     *
     * @param resultSerie A série a ser verificada.
     * @return {@code true} se a série for válida, {@code false} caso contrário.
     */
    protected static boolean isValidTvShow(Title resultSerie) {
        if (resultSerie instanceof TvShow tvShow) {
            return isReleased(tvShow.getReleaseDate()) &&
                    tvShow.getPosterPath() != null &&
                    tvShow.getOverview() != null &&
                    !tvShow.getOverview().isEmpty();
        }
        return false;
    }

    /**
     * Verifica se a data de lançamento fornecida indica que o título já foi lançado.
     *
     * @param releaseDate A data de lançamento no formato "yyyy-MM-dd".
     * @return {@code true} se o título já foi lançado, {@code false} caso contrário.
     */
    public static boolean isReleased(String releaseDate) {
        if (releaseDate == null) {
            return false;
        }
        try {
            LocalDate release = LocalDate.parse(releaseDate, DATE_FORMATTER);
            LocalDate today = LocalDate.now();
            return !release.isAfter(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
