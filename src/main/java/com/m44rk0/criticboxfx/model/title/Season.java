package com.m44rk0.criticboxfx.model.title;

import java.util.ArrayList;

public class Season {

    private final Integer seasonNumber;
    private final ArrayList<Episode> episodes;

    public Season(Integer seasonNumber, ArrayList<Episode> episodes) {
        this.seasonNumber = seasonNumber;
        this.episodes = episodes;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public ArrayList<String> getEpisodeList() {
        ArrayList<String> episodeList = new ArrayList<>();
        for (Episode episode : episodes) {
            episodeList.add(episode.episodeName());
        }
        return episodeList;
    }

}

