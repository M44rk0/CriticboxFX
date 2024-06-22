package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;
import java.util.Objects;

public class TvReview extends Review{

    private Integer seasonNumber;
    private String episodeName;

    public TvReview(Title tittle, Integer reviewNote, Date reviewDate, String reviewText, Integer seasonNumber, String episodeName) {
        super(tittle, reviewNote, reviewDate, reviewText);
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + getReviewID() +
                ", tittle=" + getTitle() +
                ", reviewNote=" + getReviewNote() +
                ", reviewText='" + getReviewText() + '\'' +
                ", reviewDate=" + getReviewDate() +
                ", episodeName=" + episodeName +
                ", seasonNumber=" + seasonNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvReview tvReview = (TvReview) o;

        if (!Objects.equals(seasonNumber, tvReview.seasonNumber))
            return false;
        return Objects.equals(episodeName, tvReview.episodeName);
    }

    @Override
    public int hashCode() {
        int result = seasonNumber != null ? seasonNumber.hashCode() : 0;
        result = 31 * result + (episodeName != null ? episodeName.hashCode() : 0);
        return result;
    }
}
