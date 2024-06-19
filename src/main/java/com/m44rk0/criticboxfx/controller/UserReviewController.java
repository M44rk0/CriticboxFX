package com.m44rk0.criticboxfx.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.Arrays;
import java.util.List;

public class UserReviewController {

    @FXML
    private HBox ratingBox;
    @FXML
    private SVGPath star1;
    @FXML
    private SVGPath star2;
    @FXML
    private SVGPath star3;
    @FXML
    private SVGPath star4;
    @FXML
    private SVGPath star5;

    private List<SVGPath> stars;
    private int currentRating = 0;

    @FXML
    public void initialize() {
        stars = Arrays.asList(star1, star2, star3, star4, star5);

        for (int i = 0; i < stars.size(); i++) {
            final int rating = i + 1;
            stars.get(i).setOnMouseClicked(event -> setRating(rating));
        }
    }

    private void setRating(int rating) {
        currentRating = rating;

        for (int i = 0; i < stars.size(); i++) {
            if (i < rating) {
                stars.get(i).setFill(Color.YELLOW);
            } else {
                stars.get(i).setFill(Color.GRAY);
            }
        }
    }

    public int getCurrentRating() {
        return currentRating;
    }
}
