package com.m44rk0.criticboxfx.controller.details;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.Icon;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import static com.m44rk0.criticboxfx.App.userDAO;
import static com.m44rk0.criticboxfx.App.userMarco;

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
    private MainController mainController;

    @FXML
    public void addToWatched(){
        if (userMarco.getWatched().contains(title)) {
            userMarco.removeWatched(title);
            userDAO.removeWatched(userMarco, title);
        } else {
            userMarco.addWatched(title);
            userDAO.addWatched(userMarco, title);
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
        mainController.setEditReviewIsCalledFrom(1);
    }

    @FXML
    private void setWatchedIcon() {
        watchedIcon.setContent(
                watchedIcon.getContent().equals(Icon.NOT_WATCHED.getPath()) ? Icon.WATCHED.getPath() : Icon.NOT_WATCHED.getPath()
        );
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setOverviewField(String overviewField) {
        if (overviewField.length() > 240) {
            overviewField = overviewField.substring(0, 237) + "...";
        }
        this.overviewText.setText(overviewField);
    }

    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);
    }

    public void setReleaseField(String releaseField){
        this.releaseText.setText(releaseField);
    }

    public void setTitleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setWatchedIcon(String watchedIcon){
        this.watchedIcon.setContent(watchedIcon);
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
    }

}
