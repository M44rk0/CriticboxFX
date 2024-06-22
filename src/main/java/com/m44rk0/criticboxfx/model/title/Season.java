package com.m44rk0.criticboxfx.model.title;

import java.util.ArrayList;

public class Season {

    private Integer seasonNumber;
    private ArrayList<String> episodeList;

    public Season(Integer seasonNumber, ArrayList<String> episodeList) {
        this.seasonNumber = seasonNumber;
        this.episodeList = episodeList;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public ArrayList<String> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(ArrayList<String> episodeList) {
        this.episodeList = episodeList;
    }

}
