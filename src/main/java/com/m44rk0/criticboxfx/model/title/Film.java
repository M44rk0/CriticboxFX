package com.m44rk0.criticboxfx.model.title;

import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;

import java.util.ArrayList;

public class Film extends Title {

    public Film(MovieDb movie) throws TmdbException {
        this.titleId = movie.getId();
        this.name = movie.getTitle();
        this.overview = movie.getOverview();
        this.posterPath = movie.getPosterPath();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.duration = movie.getRuntime();
    }

}
