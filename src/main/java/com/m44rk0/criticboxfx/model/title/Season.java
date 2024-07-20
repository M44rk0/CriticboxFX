package com.m44rk0.criticboxfx.model.title;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Season {

    private final Integer seasonNumber;
    private final String seasonPosterPath;
    private ArrayList<Episode> episodes;

    public Season(Integer seasonNumber , ArrayList<Episode> episodes, String seasonPosterPath) {
        this.seasonNumber = seasonNumber;
        this.episodes = episodes;
        this.seasonPosterPath = seasonPosterPath;
    }

    public Season(Integer seasonNumber, String seasonPosterPath) {
        this.seasonNumber = seasonNumber;
        this.seasonPosterPath = seasonPosterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public String getSeasonPosterPath(){
        return seasonPosterPath;
    }

    public ArrayList<String> getEpisodeList() {
        return episodes.stream()
                .map(Episode::episodeName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}

