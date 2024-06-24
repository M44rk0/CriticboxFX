package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;
import java.util.Objects;

public class TvReview extends Review{

    private final Integer seasonNumber;
    private final String episodeName;

    public TvReview(Title tittle, Integer reviewNote, Date reviewDate, String reviewText, Integer seasonNumber, String episodeName) {
        super(tittle, reviewNote, reviewDate, reviewText);
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public String getEpisodeName() {
        return episodeName;
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
