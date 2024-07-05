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

import static com.m44rk0.criticboxfx.controller.ViewController.titlePosterCache;

public class Search {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //Busca Assíncrona pra econimizar alguns segundos (8s pra 3s "Velozes e Furiosos")
    public static ArrayList<Title> searchAll(String apiKey, String search) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);

        var searchMovie = api.getSearch().searchMovie(search, false, "pt-BR", null, 1, null, null).getResults();
        var searchTVShow = api.getSearch().searchTv(search, null, false, "pt-BR", 1, null).getResults();

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

    private static void sortByPopularity(ArrayList<Title> results) {
        results.sort((t1, t2) -> {
            double popularity1 = t1.getPopularity();
            double popularity2 = t2.getPopularity();
            return Double.compare(popularity2, popularity1);
        });
    }

    private static boolean isValidMovie(Title resultMovie) {
        if (resultMovie instanceof Film film) {
            return isReleased(film.getReleaseDate()) &&
                    film.getPosterPath() != null &&
                    film.getPopularity() != null &&
                    film.getOverview() != null &&
                    !film.getOverview().isEmpty();
        }
        return false;
    }

    private static boolean isValidTvShow(Title resultSerie) {
        if (resultSerie instanceof TvShow tvShow) {
            return isReleased(tvShow.getReleaseDate()) &&
                    tvShow.getPosterPath() != null &&
                    tvShow.getOverview() != null &&
                    !tvShow.getOverview().isEmpty();
        }
        return false;
    }

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
