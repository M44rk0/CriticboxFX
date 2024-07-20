package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.NamedIdElement;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TitleDetails {

    protected ArrayList<String> cast;
    protected ArrayList<String> directors;
    protected ArrayList<String> producers;
    protected ArrayList<String> writers;
    protected ArrayList<String> visualEffectsTeam;
    protected ArrayList<String> photographyTeam;
    protected ArrayList<String> artDirection;
    protected ArrayList<String> soundTeam;
    protected ArrayList<String> genres;

    public TitleDetails(Title title){
        try {

            TmdbApi api = new TmdbApi(new TitleSearcher().getAPI_KEY());

            if (title instanceof Film){
                MovieDb movieDb = api.getMovies().getDetails(title.getTitleId(), "pt-BR");
                Credits credits = api.getMovies().getCredits(title.getTitleId(), "pt-BR");
                this.cast = getCast(credits);
                this.directors = getDirectors(credits);
                this.producers = getProducers(credits);
                this.writers = getWriters(credits);
                this.visualEffectsTeam = getVFX(credits);
                this.photographyTeam = getPhotography(credits);
                this.artDirection = getArtDirection(credits);
                this.soundTeam = getSound(credits);
                this.genres = getGenres(movieDb);

            } else if (title instanceof TvShow){
                TvSeriesDb seriesDb = api.getTvSeries().getDetails(title.getTitleId(), "pt-BR");
                info.movito.themoviedbapi.model.tv.core.credits.Credits credits = api.getTvSeries().getCredits(title.getTitleId(), "pt-BR");
                this.cast = getTvCast(credits);
                this.directors = getTvDirectors(seriesDb);
                this.producers = getTvProducers(credits);
                this.writers = getTvWriters(credits);
                this.visualEffectsTeam = getTvVFX(credits);
                this.photographyTeam = getTvPhotography(credits);
                this.artDirection = getTvArtDirection(credits);
                this.soundTeam = getTvSound(credits);
                this.genres = getTvGenres(seriesDb);
            }
        }
         catch (TmdbException e) {
             AlertMessage.showCommonAlert("Erro de Busca", "Erro de Busca");
            }
    }

    protected ArrayList<String> getGenres(MovieDb movie){
        return (ArrayList<String>) movie.getGenres().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());
    }

    protected ArrayList<String> getTvGenres(TvSeriesDb serie){
        return (ArrayList<String>) serie.getGenres().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());
    }


    protected ArrayList<String> getCast(Credits credits){
        List<String> cast = credits.getCast().stream()
                .map(Cast::getName)
                .collect(Collectors.toList());

        if (cast.isEmpty()) {
            cast.add("N/A");
        }

        return (ArrayList<String>) cast;
    }

    protected ArrayList<String> getTvCast(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> cast = credits.getCast().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (cast.isEmpty()) {
            cast.add("N/A");
        }

        return (ArrayList<String>) cast;
    }

    protected ArrayList<String> getDirectors(Credits credits){
        List<String> directors = credits.getCrew().stream()
                .filter(crewMember -> "Director".equals(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (directors.isEmpty()) {
            directors.add("N/A");
        }

        return (ArrayList<String>) directors;
    }

    protected ArrayList<String> getTvDirectors(TvSeriesDb seriesDb){
        List<String> directors = seriesDb.getCreatedBy().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (directors.isEmpty()) {
            directors.add("N/A");
        }

        return (ArrayList<String>) directors;
    }

    protected ArrayList<String> getProducers(Credits credits){
        List<String> producerJobs = Arrays.asList("Producer", "Executive Producer");

        List<String> producers = credits.getCrew().stream()
                .filter(crewMember -> producerJobs.contains(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (producers.isEmpty()) {
            producers.add("N/A");
        }

        return (ArrayList<String>) producers;
    }

    protected ArrayList<String> getTvProducers(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> producerJobs = Arrays.asList("Producer", "Executive Producer");

        List<String> producers = credits.getCrew().stream()
                .filter(crewMember -> producerJobs.contains(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (producers.isEmpty()) {
            producers.add("N/A");
        }

        return (ArrayList<String>) producers;
    }

    protected ArrayList<String> getWriters(Credits credits){
        List<String> writers = credits.getCrew().stream()
                .filter(crewMember -> "Writing".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (writers.isEmpty()) {
            writers.add("N/A");
        }

        return (ArrayList<String>) writers;
    }

    protected ArrayList<String> getTvWriters(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> writers = credits.getCrew().stream()
                .filter(crewMember -> "Writing".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (writers.isEmpty()) {
            writers.add("N/A");
        }

        return (ArrayList<String>) writers;
    }

    protected ArrayList<String> getVFX(Credits credits){
        List<String> vfx = credits.getCrew().stream()
                .filter(crewMember -> "Visual Effects".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (vfx.isEmpty()) {
            vfx.add("N/A");
        }

        return (ArrayList<String>) vfx;
    }

    protected ArrayList<String> getTvVFX(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> vfx = credits.getCrew().stream()
                .filter(crewMember -> "Visual Effects".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (vfx.isEmpty()) {
            vfx.add("N/A");
        }

        return (ArrayList<String>) vfx;
    }

    protected ArrayList<String> getPhotography(Credits credits){
        List<String> photographyTeam = credits.getCrew().stream()
                .filter(crewMember -> "Camera".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (photographyTeam.isEmpty()) {
            photographyTeam.add("N/A");
        }

        return (ArrayList<String>) photographyTeam;
    }

    protected ArrayList<String> getTvPhotography(info.movito.themoviedbapi.model.tv.core.credits.Credits credits) {
        List<String> photographyTeam = credits.getCrew().stream()
                .filter(crewMember -> "Camera".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (photographyTeam.isEmpty()) {
            photographyTeam.add("N/A");
        }

        return (ArrayList<String>) photographyTeam;
    }

    protected ArrayList<String> getArtDirection(Credits credits){
        List<String> artDirection = credits.getCrew().stream()
                .filter(crewMember -> "Art Direction".equals(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (artDirection.isEmpty()) {
            artDirection.add("N/A");
        }

        return (ArrayList<String>) artDirection;
    }

    protected ArrayList<String> getTvArtDirection(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> artDirection = credits.getCrew().stream()
                .filter(crewMember -> "Art Direction".equals(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (artDirection.isEmpty()) {
            artDirection.add("N/A");
        }

        return (ArrayList<String>) artDirection;
    }

    protected ArrayList<String> getSound(Credits credits){
        List<String> sound = credits.getCrew().stream()
                .filter(crewMember -> "Sound".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (sound.isEmpty()) {
            sound.add("N/A");
        }

        return (ArrayList<String>) sound;
    }

    protected ArrayList<String> getTvSound(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> sound = credits.getCrew().stream()
                .filter(crewMember -> "Sound".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (sound.isEmpty()) {
            sound.add("N/A");
        }

        return (ArrayList<String>) sound;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public ArrayList<String> getProducers() {
        return producers;
    }

    public ArrayList<String> getWriters() {
        return writers;
    }

    public ArrayList<String> getVisualEffectsTeam() {
        return visualEffectsTeam;
    }

    public ArrayList<String> getPhotographyTeam() {
        return photographyTeam;
    }

    public ArrayList<String> getArtDirection() {
        return artDirection;
    }

    public ArrayList<String> getSoundTeam() {
        return soundTeam;
    }
}

