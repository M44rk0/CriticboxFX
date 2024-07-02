package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Details{

    protected ArrayList<String> cast;
    protected ArrayList<String> directors;
    protected ArrayList<String> producers;
    protected ArrayList<String> writers;
    protected ArrayList<String> visualEffectsTeam;
    protected ArrayList<String> photographyTeam;
    protected ArrayList<String> artDirection;
    protected ArrayList<String> soundTeam;
    protected ArrayList<String> genres;

    public Details(Title title){
        try {
            TitleSearcher titleSearcher = new TitleSearcher();
            String apiKey = titleSearcher.getAPI_KEY();
            TmdbApi api = new TmdbApi(apiKey);

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
                e.printStackTrace();
            }
    }

    protected ArrayList<String> getGenres(MovieDb movie){

        ArrayList<String> genres = new ArrayList<>();
        for (int i = 0; i < movie.getGenres().size(); i++) {
            genres.add(movie.getGenres().get(i).getName());
        }

        return genres;
    }

    protected ArrayList<String> getTvGenres(TvSeriesDb serie){

        ArrayList<String> genres = new ArrayList<>();
        for (int i = 0; i < serie.getGenres().size(); i++) {
            genres.add(serie.getGenres().get(i).getName());
        }

        return genres;
    }


    protected ArrayList<String> getCast(Credits credits){
        ArrayList<String> cast = new ArrayList<>();
        for (int i = 0; i < credits.getCast().size(); i++) {
            cast.add(credits.getCast().get(i).getName());
        }
        if(cast.isEmpty()){
            cast.add("N/A");
        }
        return cast;
    }

    protected ArrayList<String> getTvCast(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> cast = new ArrayList<>();
        for (int i = 0; i < credits.getCast().size(); i++) {
            cast.add(credits.getCast().get(i).getName());
        }
        if(cast.isEmpty()){
            cast.add("N/A");
        }
        return cast;
    }

    protected ArrayList<String> getDirectors(Credits credits){
        ArrayList<String> directors = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getJob(), "Director")) {
                directors.add(credits.getCrew().get(i).getName());
            }
        }
        if(directors.isEmpty()){
            directors.add("N/A");
        }
        return directors;
    }

    protected ArrayList<String> getTvDirectors(TvSeriesDb seriesDb){
        ArrayList<String> crew = new ArrayList<>();
        for (int i = 0; i < seriesDb.getCreatedBy().size(); i++) {
            crew.add(seriesDb.getCreatedBy().get(i).getName());
        }
        if(crew.isEmpty()){
            crew.add("N/A");
        }
        return crew;
    }

    protected ArrayList<String> getProducers(Credits credits){
        ArrayList<String> producers = new ArrayList<>();
        List<String> producerJobs = Arrays.asList("Producer", "Executive Producer");
        for (int i = 0; i < credits.getCrew().size(); i++){
            if (producerJobs.contains(credits.getCrew().get(i).getJob())) {
                producers.add(credits.getCrew().get(i).getName());
            }
        }
        if(producers.isEmpty()){
            producers.add("N/A");
        }
        return producers;
    }

    protected ArrayList<String> getTvProducers(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> producers = new ArrayList<>();
        List<String> producerJobs = Arrays.asList("Producer", "Executive Producer");
        for (int i = 0; i < credits.getCrew().size(); i++){
            if (producerJobs.contains(credits.getCrew().get(i).getJob())) {
                producers.add(credits.getCrew().get(i).getName());
            }
        }
        if(producers.isEmpty()){
            producers.add("N/A");
        }
        return producers;
    }

    protected ArrayList<String> getWriters(Credits credits){
        ArrayList<String> writers = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Writing")) {
                writers.add(credits.getCrew().get(i).getName());
            }
        }
        if(writers.isEmpty()){
            writers.add("N/A");
        }
        return writers;
    }

    protected ArrayList<String> getTvWriters(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> writers = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Writing")) {
                writers.add(credits.getCrew().get(i).getName());
            }
        }
        if(writers.isEmpty()){
            writers.add("N/A");
        }
        return writers;
    }

    protected ArrayList<String> getVFX(Credits credits){
        ArrayList<String> vfx = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Visual Effects")) {
                vfx.add(credits.getCrew().get(i).getName());
            }
        }
        if(vfx.isEmpty()){
            vfx.add("N/A");
        }
        return vfx;
    }

    protected ArrayList<String> getTvVFX(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> vfx = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Visual Effects")) {
                vfx.add(credits.getCrew().get(i).getName());
            }
        }
        if(vfx.isEmpty()){
            vfx.add("N/A");
        }
        return vfx;
    }

    protected ArrayList<String> getPhotography(Credits credits){
        ArrayList<String> fotography = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Camera")) {
                fotography.add(credits.getCrew().get(i).getName());
            }
        }
        if(fotography.isEmpty()){
            fotography.add("N/A");
        }
        return fotography;
    }

    protected ArrayList<String> getTvPhotography(info.movito.themoviedbapi.model.tv.core.credits.Credits credits) {
        ArrayList<String> fotography = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if (Objects.equals(credits.getCrew().get(i).getDepartment(), "Camera")) {
                fotography.add(credits.getCrew().get(i).getName());
            }
        }
        if (fotography.isEmpty()) {
            fotography.add("N/A");
        }
        return fotography;
    }

    protected ArrayList<String> getArtDirection(Credits credits){
        ArrayList<String> artDirection = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getJob(), "Art Direction")) {
                artDirection.add(credits.getCrew().get(i).getName());
            }
        }
        if(artDirection.isEmpty()){
            artDirection.add("N/A");
        }
        return artDirection;
    }

    protected ArrayList<String> getTvArtDirection(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> artDirection = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getJob(), "Art Direction")) {
                artDirection.add(credits.getCrew().get(i).getName());
            }
        }
        if(artDirection.isEmpty()){
            artDirection.add("N/A");
        }
        return artDirection;
    }

    protected ArrayList<String> getSound(Credits credits){
        ArrayList<String> sound = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Sound")) {
                sound.add(credits.getCrew().get(i).getName());
            }
        }
        if(sound.isEmpty()){
            sound.add("N/A");
        }
        return sound;
    }

    protected ArrayList<String> getTvSound(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> sound = new ArrayList<>();
        for (int i = 0; i < credits.getCrew().size(); i++) {
            if(Objects.equals(credits.getCrew().get(i).getDepartment(), "Sound")) {
                sound.add(credits.getCrew().get(i).getName());
            }
        }
        if(sound.isEmpty()){
            sound.add("N/A");
        }
        return sound;
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

