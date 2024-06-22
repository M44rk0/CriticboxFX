package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;

public class FilmReview extends Review{

    public FilmReview(Title title, Integer reviewNote, Date reviewDate, String reviewText) {
        super(title, reviewNote, reviewDate, reviewText);
    }

}
