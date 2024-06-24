package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;

public abstract class Review {

    private static Integer reviewCount = 1;
    private final Integer reviewID;
    private final Integer reviewNote;
    private final String reviewText;
    private final Date reviewDate;
    private Title title;

    public Review(Title title, Integer reviewNote, Date reviewDate, String reviewText) {

        this.reviewID = reviewCount++;
        this.title = title;
        this.reviewNote = reviewNote;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;

    }

    public String getReviewText() {
        return reviewText;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Integer getReviewNote() {
        return reviewNote;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", tittle=" + title +
                ", reviewNote=" + reviewNote +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }

}
