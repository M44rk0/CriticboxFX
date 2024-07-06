package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import java.util.Date;


public class EpisodeReview extends Review{

    private Season season;
    private final Integer seasonNumber;
    private final String episodeName;

    public EpisodeReview(Title tittle, Integer reviewNote, Date reviewDate, String reviewText, Integer seasonNumber, String episodeName) {
        super(tittle, reviewNote, reviewDate, reviewText);
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
    }

    public EpisodeReview(Integer reviewId, Title tittle, Integer reviewNote, Date reviewDate, String reviewText, Integer seasonNumber, String episodeName) {
        super(tittle, reviewNote, reviewDate, reviewText);
        this.reviewID = reviewId;
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
    }

    public Season getSeason(){
        return season;
    }

    public void setSeason(Season season){
        this.season = season;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

}
