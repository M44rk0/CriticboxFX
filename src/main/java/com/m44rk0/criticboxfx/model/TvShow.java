package com.m44rk0.criticboxfx.model;

import info.movito.themoviedbapi.model.tv.core.credits.Credits;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TmdbApi;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Arrays;
import java.util.List;

public class TvShow extends Title {

    private final Integer tvShowID;
    private final ArrayList<Season> seasons;
    private final Integer totalEpisodes;

    public TvShow(TvSeriesDb movie, Credits credits) throws TmdbException {
        this.tvShowID = movie.getId();
        this.name = movie.getName();
        this.overview = movie.getOverview();
        this.posterPath = movie.getPosterPath();
        this.releaseDate = movie.getFirstAirDate();
        this.popularity = movie.getPopularity();
        this.duration = 1;
        this.seasons = getTvShowInfo(movie);
        this.totalEpisodes = getEpisodes();
        this.genres = getTvGenre(movie);
        this.cast = getCast(credits);
        this.directors = getDirector(movie);
        this.producers = getProducers(credits);
        this.writers = getWriters(credits);
        this.visualEffectsTeam = getVFX(credits);
        this.photographyTeam = getFotography(credits);
        this.artDirection = getArtDirection(credits);
        this.soundTeam = getSound(credits);
    }

    public Integer getTvShowID() {
        return tvShowID;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }

    protected ArrayList<String> getTvGenre(TvSeriesDb serie){

        ArrayList<String> genres = new ArrayList<>();
        for (int i = 0; i < serie.getGenres().size(); i++) {
            genres.add(serie.getGenres().get(i).getName());
        }

        return genres;
    }

    private ArrayList<Season> getTvShowInfo(TvSeriesDb seriesDb) throws TmdbException {

        TmdbApi apiKey = new TmdbApi(new MovieSearcher().getAPI_KEY());

        TvSeriesDb serie = apiKey.getTvSeries().getDetails(seriesDb.getId(), "pt-BR");

        ArrayList<Season> seasons = new ArrayList<>();
        TmdbTvSeasons tvSeasons = apiKey.getTvSeasons();

        for (int i = 0; i < serie.getSeasons().size(); i++) {

            Integer seasonNumber = serie.getSeasons().get(i).getSeasonNumber();
            ArrayList<String> episodes = new ArrayList<>();

            if(serie.getSeasons().get(i).getSeasonNumber() != 0) {
                TvSeasonDb tvSeasonDb = tvSeasons.getDetails(seriesDb.getId(), seasonNumber, "pt-BR");

                for (int j = 0; j < tvSeasonDb.getEpisodes().size(); j++) {
                    episodes.add(tvSeasonDb.getEpisodes().get(j).getName());
                }

            Season season = new Season(seasonNumber, episodes);
            seasons.add(season);
            }
        }
        return seasons;
    }

    protected ArrayList<String> getCast(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        ArrayList<String> cast = new ArrayList<>();
        for (int i = 0; i < credits.getCast().size(); i++) {
            cast.add(credits.getCast().get(i).getName());
        }
        if(cast.isEmpty()){
            cast.add("N/A");
        }
        return cast;
    }

    protected ArrayList<String> getDirector(TvSeriesDb seriesDb){
        ArrayList<String> crew = new ArrayList<>();
        for (int i = 0; i < seriesDb.getCreatedBy().size(); i++) {
                crew.add(seriesDb.getCreatedBy().get(i).getName());
        }
        if(crew.isEmpty()){
            crew.add("N/A");
        }
        return crew;
    }


    protected ArrayList<String> getProducers(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
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

    protected ArrayList<String> getWriters(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
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

    protected ArrayList<String> getVFX(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
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

    protected ArrayList<String> getFotography(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
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

    protected ArrayList<String> getArtDirection(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
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

    protected ArrayList<String> getSound(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
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


    private Integer getEpisodes(){
        int episodeCount = 0;
        for (Season season : seasons) {
             episodeCount += season.getEpisodeList().size();
        }
        return episodeCount;
    }

}
