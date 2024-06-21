package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.model.Title;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.io.IOException;

import static com.m44rk0.criticboxfx.App.user;

public class MovieInfoController {

    @FXML
    private Pane movieDetailsPane;

    @FXML
    private Button addFavoriteButton;

    @FXML
    public Button detailsButton;

    @FXML
    private Button doReviewButton;

    @FXML
    private FlowPane movieInfoFlow;

    @FXML
    private Pane movieInfoPane;

    @FXML
    private TextFlow overviewField;

    @FXML
    private Text overviewText;

    @FXML
    private ImageView posterImage;

    @FXML
    private TextFlow releaseField;

    @FXML
    private Text releaseText;

    @FXML
    private TextFlow titleField;

    @FXML
    private Text tittleText;

    @FXML
    private SVGPath favoriteStar;

    @FXML
    private TextFlow seasonField;

    @FXML
    private Text seasonText;

    @FXML
    private TextFlow episodesField;

    @FXML
    private Text episodesText;

    private Title title;
    private ViewController mainController;

    public void setMainController(ViewController mainController) {
        this.mainController = mainController;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @FXML
    public void addFavorite(){

        if (user.getFavoritesNames().contains(title.getName())) {
            user.removeFavorite(title);
        } else {
            user.addFavorite(title);
        }
    }

    public void setFillFavoriteStar(String fillFavoriteStar){
        favoriteStar.setContent(fillFavoriteStar);
    }

    @FXML
    public void showDetails(){
        mainController.showMovieDetails(title);
    }

    @FXML
    public void showReview(){
        mainController.showTitleReview(title);
    }

    @FXML
    public void setFavoriteStar() {
        String unfilledStar = "M22,9.67A1,1,0,0,0,21.14,9l-5.69-.83L12.9,3a1,1,0,0,0-1.8,0L8.55,8.16,2.86,9a1,1,0,0,0-.81.68,1,1,0,0,0,.25,1l4.13,4-1,5.68A1,1,0,0,0,6.9,21.44L12,18.77l5.1,2.67a.93.93,0,0,0,.46.12,1,1,0,0,0,.59-.19,1,1,0,0,0,.4-1l-1-5.68,4.13-4A1,1,0,0,0,22,9.67Zm-6.15,4a1,1,0,0,0-.29.88l.72,4.2-3.76-2a1.06,1.06,0,0,0-.94,0l-3.76,2,.72-4.2a1,1,0,0,0-.29-.88l-3-3,4.21-.61a1,1,0,0,0,.76-.55L12,5.7l1.88,3.82a1,1,0,0,0,.76.55l4.21.61Z";
        String fillStar = "M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z";

        if(favoriteStar.getContent().equals(unfilledStar)){
            favoriteStar.setContent(fillStar);
        }

        else if(favoriteStar.getContent().equals(fillStar)){
            favoriteStar.setContent(unfilledStar);
        }

        favoriteStar.setFill(Color.WHITE);
    }

    public void setOverviewField(String overviewField) {
        if (overviewField.length() > 240) {
            overviewField = overviewField.substring(0, 237) + "...";
        }
        this.overviewText.setText(overviewField);
    }

    public void setPosterImage(String posterImage) {
        Image poster = new Image("https://image.tmdb.org/t/p/original/" + posterImage, 250, 350, false, false);
        this.posterImage.setImage(poster);
    }

    public void setReleaseField(String releaseField){
        this.releaseText.setText(releaseField);
    }

    public void setTittleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    public void setEpisodesField(String episodesField){
        this.episodesText.setText(episodesField);
    }

    public void setSeasonField(String seasonField){
        this.seasonText.setText(seasonField);
    }

    public void turnVisible(){
        episodesField.setVisible(true);
        seasonField.setVisible(true);
    }

    @FXML
    public void initialize(){
        episodesField.setVisible(false);
        seasonField.setVisible(false);
        String unfilledStar = "M22,9.67A1,1,0,0,0,21.14,9l-5.69-.83L12.9,3a1,1,0,0,0-1.8,0L8.55,8.16,2.86,9a1,1,0,0,0-.81.68,1,1,0,0,0,.25,1l4.13,4-1,5.68A1,1,0,0,0,6.9,21.44L12,18.77l5.1,2.67a.93.93,0,0,0,.46.12,1,1,0,0,0,.59-.19,1,1,0,0,0,.4-1l-1-5.68,4.13-4A1,1,0,0,0,22,9.67Zm-6.15,4a1,1,0,0,0-.29.88l.72,4.2-3.76-2a1.06,1.06,0,0,0-.94,0l-3.76,2,.72-4.2a1,1,0,0,0-.29-.88l-3-3,4.21-.61a1,1,0,0,0,.76-.55L12,5.7l1.88,3.82a1,1,0,0,0,.76.55l4.21.61Z";
        favoriteStar.setContent(unfilledStar);
    }

}
