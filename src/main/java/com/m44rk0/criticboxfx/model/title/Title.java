package com.m44rk0.criticboxfx.model.title;

import info.movito.themoviedbapi.model.movies.Credits;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class Title {

    protected String name;
    protected String overview;
    protected String posterPath;
    protected String releaseDate;
    protected Integer duration;
    protected Double popularity;
    protected ArrayList<String> genres;
    protected ArrayList<String> cast;
    protected ArrayList<String> directors;
    protected ArrayList<String> producers;
    protected ArrayList<String> writers;
    protected ArrayList<String> visualEffectsTeam;
    protected ArrayList<String> photographyTeam;
    protected ArrayList<String> artDirection;
    protected ArrayList<String> soundTeam;

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

    protected ArrayList<String> getDirector(Credits credits){
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

    public String getName() {
        return name;
    }

    public Double getPopularity(){
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getDuration() {
        return duration;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title tittle = (Title) o;
        return Objects.equals(name, tittle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                '}';
    }
}
