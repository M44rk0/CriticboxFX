package com.m44rk0.criticboxfx.model.title;

import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;

/**
 * Representa uma classe para um filme, estendendo a classe abstrata {@link Title}.
 */
public class Film extends Title {

    /**
     * Construtor que inicializa um objeto Film usando dados de um objeto MovieDb do Wrapper do TmdbAPI.
     *
     * @param movie O objeto MovieDb contendo os dados do filme.
     * @throws TmdbException Se houver um erro ao acessar os dados do filme.
     */
    public Film(MovieDb movie) throws TmdbException {
        this.titleId = movie.getId();
        this.name = movie.getTitle();
        this.overview = movie.getOverview();
        this.posterPath = movie.getPosterPath();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.duration = movie.getRuntime();
    }

    /**
     * Construtor padrão para criar um objeto Film vazio.
     */
    public Film() {

    }

    /**
     * Construtor que inicializa um objeto Film com valores especificados.
     *
     * @param titleId     O identificador único do título.
     * @param name        O nome do filme.
     * @param overview    A visão geral do filme.
     * @param posterPath  O caminho do pôster do filme.
     * @param releaseDate A data de lançamento do filme.
     * @param duration    A duração do filme em minutos.
     * @param popularity  A popularidade do filme.
     */
    public Film(int titleId, String name, String overview, String posterPath, String releaseDate, int duration, double popularity) {
        this.titleId = titleId;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.duration = duration;
    }
}

