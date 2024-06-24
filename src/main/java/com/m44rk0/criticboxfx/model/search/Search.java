package com.m44rk0.criticboxfx.model.search;
import com.m44rk0.criticboxfx.model.title.Film;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.tools.TmdbException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Search {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //remade by my bro Guilherme Pontes Gonçalves <3

    public static ArrayList<Title> searchAll(String apiKey, String search) throws TmdbException {
        TmdbApi api = new TmdbApi(apiKey);

        var searchMovie = api.getSearch().searchMovie(search, false, "pt-BR", null, 1, null, null).getResults();
        var searchTVShow = api.getSearch().searchTv(search, null, false, "pt-BR", 1, null).getResults();

        List<CompletableFuture<Title>> movieFutures = searchMovie.stream()
                .map(movie -> CompletableFuture.supplyAsync(() -> {
                    Credits credits = null;
                    try {
                        credits = api.getMovies().getCredits(movie.getId(), "pt-BR");
                    } catch (TmdbException e) {
                        e.printStackTrace();
                    }
                    Title resultMovie = null;
                    try {
                        resultMovie = new Film(api.getMovies().getDetails(movie.getId(), "pt-BR"), credits);
                    } catch (TmdbException e) {
                        e.printStackTrace();
                    }
                    return isValidMovie(resultMovie) ? resultMovie : null;
                }))
                .toList();

        List<CompletableFuture<Title>> tvShowFutures = searchTVShow.stream()
                .map(tvShow -> CompletableFuture.supplyAsync(() -> {
                    info.movito.themoviedbapi.model.tv.core.credits.Credits credits = null;
                    try {
                        credits = api.getTvSeries().getCredits(tvShow.getId(), "pt-BR");
                    } catch (TmdbException e) {
                        e.printStackTrace();
                    }
                    Title resultSerie = null;
                    try {
                        resultSerie = new TvShow(api.getTvSeries().getDetails(tvShow.getId(), "pt-BR"), credits);
                    } catch (TmdbException e) {
                        e.printStackTrace();
                    }
                    return isValidTvShow(resultSerie) ? resultSerie : null;
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
                    Credits credits = null;
                    try {
                        credits = api.getMovies().getCredits(movie.getId(), "pt-BR");
                    } catch (TmdbException e) {
                        throw new RuntimeException(e);
                    }
                    Title resultMovie = null;
                    try {
                        resultMovie = new Film(api.getMovies().getDetails(movie.getId(), "pt-BR"), credits);
                    } catch (TmdbException e) {
                        throw new RuntimeException(e);
                    }
                    return isValidMovie(resultMovie) ? resultMovie : null;
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

        List<CompletableFuture<Title>> tvShowFutures = searchTVShow.stream()
                .map(tvShow -> CompletableFuture.supplyAsync(() -> {
                    info.movito.themoviedbapi.model.tv.core.credits.Credits credits = null;
                    try {
                        credits = api.getTvSeries().getCredits(tvShow.getId(), "pt-BR");
                    } catch (TmdbException e) {
                        throw new RuntimeException(e);
                    }
                    Title resultSerie = null;
                    try {
                        resultSerie = new TvShow(api.getTvSeries().getDetails(tvShow.getId(), "pt-BR"), credits);
                    } catch (TmdbException e) {
                        throw new RuntimeException(e);
                    }
                    return isValidTvShow(resultSerie) ? resultSerie : null;
                }))
                .toList();

        return new ArrayList<>(tvShowFutures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList());
    }

////this one is the pai who made it
//    public static ArrayList<Tittle> searchMovie(String apiKey, String search) throws TmdbException {
//
//        TmdbApi api = new TmdbApi(apiKey);
//
//        var searchMovie = api.getSearch().searchMovie(search, false, "pt-BR", null, 1, null, null).getResults();
//        var searchTVShow = api.getSearch().searchTv(search, null, false, "pt-BR", 1, null).getResults();
//
//        ArrayList<Tittle> results = new ArrayList<>();
//
//        for (Movie movie : searchMovie) {
//            Credits credits = api.getMovies().getCredits(movie.getId(), "pt-BR");
//            Tittle resultMovie = new Film(api.getMovies().getDetails(movie.getId(), "pt-BR"), credits);
//            if (isValidMovie(resultMovie)) {
//                results.add(resultMovie);
//            }
//        }
//
//        for (TvSeries serie : searchTVShow) {
//            info.movito.themoviedbapi.model.tv.core.credits.Credits credits = api.getTvSeries().getCredits(serie.getId(), "pt-BR");
//            Tittle resultSerie = new TvShow(api.getTvSeries().getDetails(serie.getId(), "pt-BR"), credits);
//            if (isValidTvShow(resultSerie)) {
//                results.add(resultSerie);
//            }
//        }
//
//        sortByReleaseDate(results);
//
//        return results;
//    }

    private static void sortByPopularity(ArrayList<Title> results) {
        results.sort(new Comparator<Title>() {
            @Override
            public int compare(Title t1, Title t2) {
                double popularity1 = t1.getPopularity();
                double popularity2 = t2.getPopularity();

                // Note que, para ordenar em ordem decrescente de popularidade,
                // devemos comparar popularity2 com popularity1.
                return Double.compare(popularity2, popularity1);
            }
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
