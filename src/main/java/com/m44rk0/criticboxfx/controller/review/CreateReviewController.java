package com.m44rk0.criticboxfx.controller.review;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.review.FilmReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TvReview;
import com.m44rk0.criticboxfx.model.title.Film;
import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;

import static com.m44rk0.criticboxfx.App.user;

public class CreateReviewController implements CommonController {

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

    private Title title;
    private List<SVGPath> stars;
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
        if (overviewField.length() > 540) {
            overviewField = overviewField.substring(0, 537) + "...";
        }
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


    public void setMainController(ViewController viewController) {
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

        if(getCurrentRating() == 0 || reviewArea.getText().isEmpty()){

            AlertMessage.showAlert("Erro de review", "Esquecer de dar a nota meu mano");

        }

        else if(!containsInReview(title)) {

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

    public boolean containsInReview(Title title) {

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

                    if (review.getTitle() instanceof Film) {
                        return true;
                    }

                    else if (review.getTitle() instanceof TvShow) {

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

        reviewArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 456) {
                reviewArea.setText(oldValue);
            }
        });
    }
}
