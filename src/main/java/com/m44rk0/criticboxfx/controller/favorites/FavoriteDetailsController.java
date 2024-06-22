package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.utils.CommonFields;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import java.util.ArrayList;
import javafx.fxml.FXML;

public class FavoriteDetailsController implements CommonFields {

    @FXML
    private Button returnButton;

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
    private TextFlow durationField;

    @FXML
    private Text durationText;

    @FXML
    private TextFlow durationLabel;

    @FXML
    private TextFlow memberField;

    @FXML
    private Text memberText;

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

    private ViewController mainController;

    @FXML
    void handleReturnButtonClick() {
        if (mainController != null) {
            mainController.showFavorites();
        }
    }

    private void copyTextStyles(Text source, Text target) {
        target.setFont(source.getFont());
        target.setFill(source.getFill());
        target.setStyle(source.getStyle());
        target.getStyleClass().addAll(source.getStyleClass());
    }

    public void setFlow(ArrayList<String> members, FlowPane flowPane) {

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

    public void setMainController(ViewController mainController) {
        this.mainController = mainController;
    }

    public void setDurationField(Integer durationField){
        this.durationText.setText(String.valueOf(durationField) + " min");
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

    public void setPosterImage(String posterImage) {
        Image poster = new Image("https://image.tmdb.org/t/p/original/" + posterImage, 250, 350, false, false);
        this.posterImage.setImage(poster);
    }

    public void hideDuration(){
        durationField.setVisible(false);
        durationLabel.setVisible(false);
    }

}