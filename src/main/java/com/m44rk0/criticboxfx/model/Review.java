package com.m44rk0.criticboxfx.model;

import java.util.Date;

public class Review {

    private static Integer reviewCount = 1;
    private Integer reviewID;
    private Title tittle;
    private Integer reviewNote;
    private Date reviewDate;

    public Review(Title tittle, Integer reviewNote, Date reviewDate) {
        this.reviewID = reviewCount++;
        this.tittle = tittle;
        this.reviewNote = reviewNote;
        this.reviewDate = reviewDate;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public Title getTittle() {
        return tittle;
    }

    public void setTittle(Title tittle) {
        this.tittle = tittle;
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
}
