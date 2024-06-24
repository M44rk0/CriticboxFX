package com.m44rk0.criticboxfx.controller.details;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import static com.m44rk0.criticboxfx.App.user;

public class TitleInfoController implements CommonController {

    @FXML
    private Text overviewText;

    @FXML
    private ImageView posterImage;

    @FXML
    private Text releaseText;

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

    @FXML
    private SVGPath watchedIcon;

    private Title title;
    private ViewController mainController;

    @FXML
    public void addFavorite(){

        if (user.getFavorites().contains(title)) {
            user.removeFavorite(title);
        } else {
            user.addFavorite(title);
        }
        setFavoriteIcon();
    }

    @FXML
    public void addToWatched(){
        if (user.getWatched().contains(title)) {
            user.removeWatched(title);
        } else {
            user.addWatched(title);
        }
        setWatchedIcon();

    }

    @FXML
    public void showDetails(){
        mainController.showTitleDetails(title);
        mainController.setDetailsIsCalledFrom(1);
    }

    @FXML
    public void showReview(){
        mainController.showCreateReview(title);
    }

    @FXML
    private void setWatchedIcon() {
        String hasWatchedIcon = "M10.94,6.08A6.93,6.93,0,0,1,12,6c3.18,0,6.17,2.29,7.91,6a15.23,15.23,0,0,1-.9,1.64a1,1,0,0,0-.16.55a1,1,0,0,0,1.86.5a15.77,15.77,0,0,0,1.21-2.3a1,1,0,0,0,0-.79C19.9,6.91,16.1,4,12,4a7.77,7.77,0,0,0-1.4.12a1,1,0,1,0,.34,2ZM3.71,2.29A1,1,0,0,0,2.29,3.71L5.39,6.8a14.62,14.62,0,0,0-3.31,4.8a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20a9.26,9.26,0,0,0,5.05-1.54l3.24,3.25a1,1,0,0,0,1.42,0a1,1,0,0,0,0-1.42Zm6.36,9.19l2.45,2.45A1.81,1.81,0,0,1,12,14a2,2,0,0,1-2-2A1.81,1.81,0,0,1,10.07,11.48ZM12,18c-3.18,0-6.17-2.29-7.9-6A12.09,12.09,0,0,1,6.8,8.21L8.57,10A4,4,0,0,0,14,15.43L15.59,17A7.24,7.24,0,0,1,12,18Z";
        String notWatchedIcon = "M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z";

        watchedIcon.setContent(
                watchedIcon.getContent().equals(notWatchedIcon) ? hasWatchedIcon : notWatchedIcon
        );
    }
    
    public void setMainController(ViewController mainController) {
        this.mainController = mainController;
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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    private void setFavoriteIcon() {
        String unfilledStarIcon = "M22,9.67A1,1,0,0,0,21.14,9l-5.69-.83L12.9,3a1,1,0,0,0-1.8,0L8.55,8.16,2.86,9a1,1,0,0,0-.81.68,1,1,0,0,0,.25,1l4.13,4-1,5.68A1,1,0,0,0,6.9,21.44L12,18.77l5.1,2.67a.93.93,0,0,0,.46.12,1,1,0,0,0,.59-.19,1,1,0,0,0,.4-1l-1-5.68,4.13-4A1,1,0,0,0,22,9.67Zm-6.15,4a1,1,0,0,0-.29.88l.72,4.2-3.76-2a1.06,1.06,0,0,0-.94,0l-3.76,2,.72-4.2a1,1,0,0,0-.29-.88l-3-3,4.21-.61a1,1,0,0,0,.76-.55L12,5.7l1.88,3.82a1,1,0,0,0,.76.55l4.21.61Z";
        String filledStarIcon = "M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z";

        favoriteStar.setContent(
                favoriteStar.getContent().equals(unfilledStarIcon) ? filledStarIcon : unfilledStarIcon
        );
        
        favoriteStar.setFill(Color.WHITE);
    }

    public void setWatchedIcon(String watchedIcon){
        this.watchedIcon.setContent(watchedIcon);
    }

    public void setFillFavoriteStar(String favoriteStar){
        this.favoriteStar.setContent(favoriteStar);
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
