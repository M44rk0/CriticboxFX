package com.m44rk0.criticboxfx.model.title;

public class Episode {
    private String episodeName;
    private Integer episodeRuntime;

    public Episode(String episodeName, Integer episodeRuntime) {
        this.episodeName = episodeName;
        this.episodeRuntime = episodeRuntime;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public Integer getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRuntime(Integer episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }
}
