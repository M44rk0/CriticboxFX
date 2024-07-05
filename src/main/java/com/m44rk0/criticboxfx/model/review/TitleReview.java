package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;

public class TitleReview extends Review{

    public TitleReview(Title title, Integer reviewNote, Date reviewDate, String reviewText) {
        super(title, reviewNote, reviewDate, reviewText);
    }

}
