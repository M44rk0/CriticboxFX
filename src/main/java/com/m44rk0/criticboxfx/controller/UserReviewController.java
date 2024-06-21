package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;

import static com.m44rk0.criticboxfx.App.user;

public class UserReviewController {

    @FXML
    private Button doReviewButton;

    @FXML
    private ImageView posterImage;

    @FXML
    private TextArea reviewArea;

    @FXML
    private TextFlow overviewField;

    @FXML
    private Text overviewText;

    @FXML
    private TextFlow releaseField;

    @FXML
    private Text releaseText;

    @FXML
    private ComboBox<Integer> seasonBox;

    @FXML
    private ComboBox<String> episodeBox;

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

    @FXML
    private TextFlow tittleField;

    @FXML
    private Text tittleText;

    private List<SVGPath> stars;
    private Title title;
    private int currentRating = 0;
    private ViewController viewController;

    private void updateEpisodeBox(int seasonNumber) {
        if (title instanceof TvShow tvShow) {
            for (Season season : tvShow.getSeasons()) {
                if (season.getSeasonNumber() == seasonNumber) {
                    setEpisodeBox(season.getEpisodeList());
                    break;
                }
            }
        }
    }

    @FXML
    void ReturnButtonClick() {
        if (viewController != null) {
            viewController.restoreSearchResults();
        }
    }

    public void setTitle(Title title) {
        this.title = title;
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

    public void setReleaseField(String releaseField){
        this.releaseText.setText(releaseField);
    }

    public void setTittleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    public void setOverviewField(String overviewField) {
        this.overviewText.setText(overviewField);
    }

    public void setPosterImage(String posterImage) {
        Image poster = new Image("https://image.tmdb.org/t/p/original/" + posterImage, 250, 350, false, false);
        this.posterImage.setImage(poster);
    }

    public void setEpisodeBox(ArrayList<String> episodes) {
        this.episodeBox.getItems().clear();
        this.episodeBox.getItems().addAll(episodes);
    }

    public void setSeasonBox(ArrayList<Integer> seasons) {
         this.seasonBox.getItems().addAll(seasons);
    }


    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    public TextArea getReviewArea() {
        return reviewArea;
    }

    public int getCurrentRating() {
        return currentRating;
    }

    @FXML
    public void saveReview(){

        if(getCurrentRating() == 0){
            System.out.println("Opa amigão, faltou algo aí");
        }

        if(!containsInReview(title)) {

            if (title instanceof Film || (title instanceof TvShow &&
                            (episodeBox.getValue() == null || episodeBox.getValue().isEmpty()) &&
                            (seasonBox.getValue() == null || seasonBox.getValue() == 0))) {

                user.addReview(new FilmReview(title, getCurrentRating(), new Date(), reviewArea.getText()));
            }

            else{

                user.addReview(new TvReview(title, getCurrentRating(), new Date(),
                        reviewArea.getText(), seasonBox.getValue(), episodeBox.getValue()));
            }

            System.out.println(user.getReviews());
            viewController.restoreSearchResults();
        }
        else{
            System.out.println("Review ja existe seu merda");
        }

    }

    private boolean containsInReview(Title title) {

        ArrayList<Review> userReviews = user.getReviews();
        for (Review review : userReviews) {
            if (review instanceof TvReview tvReview) {
                if (review.getTitle().equals(title) &&
                        Objects.equals(tvReview.getSeasonNumber(), seasonBox.getValue()) &&
                        tvReview.getEpisodeName().equals(episodeBox.getValue())) {
                    return true;
                }

            } else if (review instanceof FilmReview) {
                if (review.getTitle().equals(title)) {
                    // Se o título é um filme, retornamos true
                    if (review.getTitle() instanceof Film) {
                        return true;
                    }
                    // Se o título é um TvShow sem temporada e episódio, retornamos true
                    else if (review.getTitle() instanceof TvShow) {
                        // Aqui consideramos que uma review de série sem episódios específicos é um FilmReview
                        if ((seasonBox.getValue() == null || seasonBox.getValue() == 0) &&
                                (episodeBox.getValue() == null || episodeBox.getValue().isEmpty())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }





    //                if (


    public void turnVisible(){
        seasonBox.setVisible(true);
        episodeBox.setVisible(true);
    }

    @FXML
    public void initialize() {

        seasonBox.setVisible(false);
        episodeBox.setVisible(false);

        stars = Arrays.asList(star1, star2, star3, star4, star5);

        for (int i = 0; i < stars.size(); i++) {
            final int rating = i + 1;
            stars.get(i).setOnMouseClicked(event -> setRating(rating));
        }

        seasonBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateEpisodeBox(newValue);
            }
        });

    }

}
