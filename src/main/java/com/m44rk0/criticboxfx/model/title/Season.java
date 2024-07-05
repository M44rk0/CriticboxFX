package com.m44rk0.criticboxfx.model.title;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Season {

    private final Integer seasonNumber;
    private final String seasonPosterPath;
    private final ArrayList<Episode> episodes;

    public Season(Integer seasonNumber , ArrayList<Episode> episodes, String seasonPosterPath) {
        this.seasonNumber = seasonNumber;
        this.episodes = episodes;
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
                .map(Episode::getEpisodeName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

