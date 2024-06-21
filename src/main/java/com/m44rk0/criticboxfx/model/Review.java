package com.m44rk0.criticboxfx.model;

import java.util.Date;
import java.util.Objects;

public abstract class Review {

    private static Integer reviewCount = 1;
    private Integer reviewID;
    private Title title;
    private Integer reviewNote;
    private String reviewText;
    private Date reviewDate;

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

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
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

    public void setReviewNote(Integer reviewNote) {
        this.reviewNote = reviewNote;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
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
