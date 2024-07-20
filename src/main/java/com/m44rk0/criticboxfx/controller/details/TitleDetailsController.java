package com.m44rk0.criticboxfx.controller.details;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TitleDetails;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.Icon;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import java.io.IOException;
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
    private Text durationText;

    @FXML
    private Text durationLabelText;

    @FXML
    private TextFlow episodesField;

    @FXML
    private Text episodesText;

    @FXML
    private TextFlow episodesLabel;

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

    private MainController mainController;

    private Title title;

    public void showTitleDetails(Title title) {
        try {
            mainController.resetScrollBox();
            mainController.getScrollPage().setVvalue(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("titleDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            TitleDetailsController controller = loader.getController();

            mainController.setCommonFields(controller, title);
            TitleDetails details = new TitleDetails(title);

            controller.setDurationField(title.getDuration());
            controller.setGenreFlow(details.getGenres());
            controller.setDirectorFlow(details.getDirectors());
            controller.setCastFlow(details.getCast());
            controller.setWriterFlow(details.getWriters());
            controller.setProducerFlow(details.getProducers());
            controller.setArtDirectFlow(details.getArtDirection());
            controller.setSoundFlow(details.getSoundTeam());
            controller.setCameraFlow(details.getPhotographyTeam());
            controller.setVfxFlow(details.getVisualEffectsTeam());

            if (title instanceof TvShow) {
                controller.changeDurationToSeasons((TvShow) title);
                controller.turnEpisodesVisible((TvShow) title);
            }

            if (CurrentlyUser.getFavorites().contains(title)) {
                controller.setFillFavoriteStar(Icon.FILLED_STAR.getPath());
            }

            mainController.getScrollBox().getChildren().add(movieDetailsPane);
        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Details");
        }
    }

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
            mainController.setIfTheReviewIsEditable(false);
    }

    @FXML
    public void addFavorite(){
        if (CurrentlyUser.getFavorites().contains(title)) {
            userDAO.removeFavorite(CurrentlyUser.getUser(), title);
            CurrentlyUser.removeFavorite(title);
        } else {
            userDAO.addFavorite(CurrentlyUser.getUser(), title);
            CurrentlyUser.addFavorite(title);
        }
        setFavoriteIcon();
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

    private void setFavoriteIcon() {

        favoriteStar.setContent(
                favoriteStar.getContent().equals(Icon.UNFILLED_STAR.getPath()) ? Icon.FILLED_STAR.getPath() : Icon.UNFILLED_STAR.getPath()
        );

        favoriteStar.setFill(Color.WHITE);
    }

    private void copyTextStyles(Text source, Text target) {
        target.setFont(source.getFont());
        target.setFill(source.getFill());
        target.setStyle(source.getStyle());
        target.getStyleClass().addAll(source.getStyleClass());
    }

    public void changeDurationToSeasons(TvShow tvshow){
        durationLabelText.setText("Temporadas");
        durationText.setText(String.valueOf(tvshow.getSeasons().size()));
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

    public void turnEpisodesVisible(TvShow tvshow){
        episodesText.setText(String.valueOf(tvshow.getTotalEpisodes()));
        episodesLabel.setVisible(true);
        episodesField.setVisible(true);
    }

    @FXML
    public void initialize(){
        episodesLabel.setVisible(false);
        episodesField.setVisible(false);
    }

}
