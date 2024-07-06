package com.m44rk0.criticboxfx.controller.details;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TitleDAO;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.model.user.UserDAO;
import com.m44rk0.criticboxfx.utils.CommonController;
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


    private ViewController mainController;

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
    public void addFavorite() throws SQLException {
        if (userMarco.getFavorites().contains(title)) {
            userDAO.removeFavorite(userMarco, title);
            userMarco.removeFavorite(title);
        } else {
            userDAO.addFavorite(userMarco, title);
            userMarco.addFavorite(title);
        }
        setFavoriteIcon();
    }

    private void setFavoriteIcon() {
        String unfilledStarIcon = "M22,9.67A1,1,0,0,0,21.14,9l-5.69-.83L12.9,3a1,1,0,0,0-1.8,0L8.55,8.16,2.86,9a1,1,0,0,0-.81.68,1,1,0,0,0,.25,1l4.13,4-1,5.68A1,1,0,0,0,6.9,21.44L12,18.77l5.1,2.67a.93.93,0,0,0,.46.12,1,1,0,0,0,.59-.19,1,1,0,0,0,.4-1l-1-5.68,4.13-4A1,1,0,0,0,22,9.67Zm-6.15,4a1,1,0,0,0-.29.88l.72,4.2-3.76-2a1.06,1.06,0,0,0-.94,0l-3.76,2,.72-4.2a1,1,0,0,0-.29-.88l-3-3,4.21-.61a1,1,0,0,0,.76-.55L12,5.7l1.88,3.82a1,1,0,0,0,.76.55l4.21.61Z";
        String filledStarIcon = "M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z";

        favoriteStar.setContent(
                favoriteStar.getContent().equals(unfilledStarIcon) ? filledStarIcon : unfilledStarIcon
        );

        favoriteStar.setFill(Color.WHITE);
    }

    public void setMainController(ViewController mainController) {
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
