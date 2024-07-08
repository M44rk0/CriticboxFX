package com.m44rk0.criticboxfx.controller.details;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.Icon;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;

import static com.m44rk0.criticboxfx.App.*;

public class TitleDetailsController implements CommonController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Text overviewText;

    @FXML
    private Text releaseText;

    @FXML
    private Text tittleText;

    @FXML
    private TextFlow durationField;

    @FXML
    private Text durationText;

    @FXML
    private TextFlow durationLabel;

    @FXML
    private Text memberText;

    @FXML
    private SVGPath favoriteStar;

    @FXML
    private FlowPane genresFlow;

    @FXML
    private FlowPane directorFlow;

    @FXML
    private FlowPane castFlow;

    @FXML
    private FlowPane writerFlow;

    @FXML
    private FlowPane producerFlow;

    @FXML
    private FlowPane artDirectFlow;

    @FXML
    private FlowPane soundFlow;

    @FXML
    private FlowPane cameraFlow;

    @FXML
    private FlowPane vfxFlow;

    @FXML
    private ComboBox<Integer> seasonBox;

    @FXML
    private ComboBox<String> episodeBox;


    private MainController mainController;

    private Title title;

    @FXML
    void handleReturnButtonClick() {
        if (mainController != null) {
            if(mainController.getDetailsIsCalledFrom() == 1) {
                mainController.restoreSearchResults();
            }
            else if (mainController.getDetailsIsCalledFrom() == 2){
                mainController.showFavorites();
                }
        }
    }

    @FXML
    void makeReview(){
            mainController.showCreateReview(title);
            mainController.setEditReviewIsCalledFrom(1);
    }

    @FXML
    public void addFavorite(){
        if (user.getFavorites().contains(title)) {
            userDAO.removeFavorite(user, title);
            user.removeFavorite(title);
        } else {
            userDAO.addFavorite(user, title);
            user.addFavorite(title);
        }
        setFavoriteIcon();
    }

    private void setFavoriteIcon() {

        favoriteStar.setContent(
                favoriteStar.getContent().equals(Icon.UNFILLED_STAR.getPath()) ? Icon.FILLED_STAR.getPath() : Icon.UNFILLED_STAR.getPath()
        );

        favoriteStar.setFill(Color.WHITE);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDurationField(Integer durationField){
        this.durationText.setText(durationField + " min");
    }

    public void setReleaseField(String releaseField){
        this.releaseText.setText(releaseField);
    }

    public void setTitleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    public void setOverviewField(String overviewField) {
        this.overviewText.setText(overviewField);
    }

    public void setGenreFlow(ArrayList<String> genres) {
        setFlow(genres, genresFlow);
    }

    public void setDirectorFlow(ArrayList<String> directors) {
        setFlow(directors, directorFlow);
    }

    public void setCastFlow(ArrayList<String> cast) {
        setFlow(cast, castFlow);
    }

    public void setWriterFlow(ArrayList<String> writers) {
        setFlow(writers, writerFlow);
    }

    public void setProducerFlow(ArrayList<String> producers) {
        setFlow(producers, producerFlow);
    }

    public void setArtDirectFlow(ArrayList<String> artDirectors) {
        setFlow(artDirectors, artDirectFlow);
    }

    public void setSoundFlow(ArrayList<String> soundMembers) {
        setFlow(soundMembers, soundFlow);
    }

    public void setCameraFlow(ArrayList<String> cameraMembers) {
        setFlow(cameraMembers, cameraFlow);
    }

    public void setVfxFlow(ArrayList<String> vfxMembers) {
        setFlow(vfxMembers, vfxFlow);
    }

    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);
    }

    public void setFillFavoriteStar(String fillFavoriteStar){
        favoriteStar.setContent(fillFavoriteStar);
    }

    private void copyTextStyles(Text source, Text target) {
        target.setFont(source.getFont());
        target.setFill(source.getFill());
        target.setStyle(source.getStyle());
        target.getStyleClass().addAll(source.getStyleClass());
    }

    public void hideDuration(){
        durationField.setVisible(false);
        durationLabel.setVisible(false);
    }

    private void setFlow(ArrayList<String> members, FlowPane flowPane) {

        flowPane.getChildren().clear();

        for (String member : members) {

            Text memberTextCopy = new Text(member);
            copyTextStyles(memberText, memberTextCopy);

            TextFlow memberField = new TextFlow(memberTextCopy);
            memberField.getStyleClass().add("memberFlow");
            memberField.setBlendMode(BlendMode.ADD);
            memberField.setPadding(new Insets(5, 5 , 5 ,5));

            flowPane.getChildren().add(memberField);
        }
    }

    public void setEpisodeBox(ArrayList<String> episodes) {
        this.episodeBox.getItems().clear();
        this.episodeBox.getItems().addAll(episodes);
    }

    public void setSeasonBox(ArrayList<Integer> seasons) {
        this.seasonBox.getItems().addAll(seasons);
    }

    public void turnVisible(){
        seasonBox.setVisible(true);
        episodeBox.setVisible(true);
    }

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
    public void initialize(){

        seasonBox.setVisible(false);
        episodeBox.setVisible(false);

        seasonBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateEpisodeBox(newValue);
            }
        });

    }

}
