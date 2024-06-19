package com.m44rk0.criticboxfx.model;

import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;

import java.util.ArrayList;

public class Film extends Title {

    private final Integer movieID;

    public Film(MovieDb movie, Credits credits) throws TmdbException {
        this.movieID = movie.getId();
        this.name = movie.getTitle();
        this.overview = movie.getOverview();
        this.posterPath = movie.getPosterPath();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.duration = movie.getRuntime();
        this.genres = getGenre(movie);
        this.cast = getCast(credits);
        this.directors = getDirector(credits);
        this.producers = getProducers(credits);
        this.writers = getWriters(credits);
        this.visualEffectsTeam = getVFX(credits);
        this.photographyTeam = getPhotography(credits);
        this.artDirection = getArtDirection(credits);
        this.soundTeam = getSound(credits);
    }

    protected ArrayList<String> getGenre(MovieDb movie){

        ArrayList<String> genres = new ArrayList<>();
        for (int i = 0; i < movie.getGenres().size(); i++) {
            genres.add(movie.getGenres().get(i).getName());
        }

        return genres;
    }

    public Integer getMovieID() {
        return movieID;
    }

}
