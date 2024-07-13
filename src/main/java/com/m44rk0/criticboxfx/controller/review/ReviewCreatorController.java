package com.m44rk0.criticboxfx.controller.review;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.*;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.m44rk0.criticboxfx.App.*;

public class ReviewCreatorController implements CommonController {

    @FXML
    private ImageView posterImage;

    @FXML
    private TextArea reviewArea;

    @FXML
    private Text overviewText;

    @FXML
    private Text tittleText;

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

    private int currentRating = 0;
    private Title title;
    private List<SVGPath> stars;
    private MainController mainController;

    @FXML
    void ReturnButtonClick(){
        if (mainController != null) {
            mainController.restoreSearchResults();
        }
    }

    @FXML
    public void saveReview() {

        if(mainController.theReviewIsEditable()){
            int choice = AlertMessage.showChoiceAlert("Edição de Review", "Deseja editar a review?");
            Review reviewToEdit = mainController.getReviewToEdit();
            if(choice == 0){
                reviewToEdit.editReview(reviewArea.getText(), getCurrentRating());
                mainController.setIfTheReviewIsEditable(false);
                reviewDAO.editReview(reviewToEdit);
            }
            mainController.showUserReviews();
        }
        else {
            if (getCurrentRating() == 0 && reviewArea.getText().isBlank()) {
                AlertMessage.showCommonAlert("Erro de Review", "Esqueceu foi de tudo po, ta de sacanagem?");
            } else if (getCurrentRating() == 0) {
                AlertMessage.showCommonAlert("Erro de Review", "Esquecer de dar a nota meu mano");
            } else if (reviewArea.getText().isEmpty()) {
                AlertMessage.showCommonAlert("Erro de Review", "Esqueceu da avaliação irmão");
            } else if (!containsInReview(title)) {

                if (title instanceof Film || (title instanceof TvShow &&
                        (episodeBox.getValue() == null || episodeBox.getValue().isEmpty()) &&
                        (seasonBox.getValue() == null || seasonBox.getValue() == 0))) {

                    var review = new TitleReview(title, getCurrentRating(), new Date(), reviewArea.getText());
                    userDAO.addReview(CurrentlyUser.getUser(), review);
                    CurrentlyUser.addReview(review);
                } else {

                    var review = new EpisodeReview(title, getCurrentRating(), new Date(),
                            reviewArea.getText(),seasonBox.getValue(), episodeBox.getValue());

                    review.setSeason(((TvShow) title).getSeasonByNumber(seasonBox.getValue()));
                    userDAO.addReview(CurrentlyUser.getUser(), review);
                    CurrentlyUser.addReview(review);
                }
                mainController.restoreSearchResults();
            } else {
                AlertMessage.showCommonAlert("Review já existe", "Essa Review já existe animal");
            }
        }

    }

    public boolean containsInReview(Title title) {

        List<Review> userReviews = CurrentlyUser.getReviews();
        for (Review review : userReviews) {
            if (review instanceof EpisodeReview episodeReview) {
                if (review.getTitle().equals(title) &&
                        Objects.equals(episodeReview.getSeasonNumber(), seasonBox.getValue()) &&
                        episodeReview.getEpisodeName().equals(episodeBox.getValue())) {
                    return true;
                }

            } else if (review instanceof TitleReview) {
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

    public void showCreateReview(Title title) {
        try {
            mainController.getScrollPage().setVvalue(0);
            mainController.getScrollBox().getChildren().clear();
            mainController.getScrollPage().setFitToHeight(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("createReview.fxml"));
            Pane reviewPane = loader.load();
            ReviewCreatorController controller = loader.getController();

            mainController.setCommonFields(controller, title);
            controller.setTitleField(title.getName() + " (" + dateToYear(title.getReleaseDate()) + ")");

            //verifica se a review que está sendo exibida é uma review nova ou uma edição
            if (mainController.theReviewIsEditable()) {
                controller.setCurrentRating(mainController.getReviewToEdit().getReviewNote());
                controller.setText(mainController.getReviewToEdit().getReviewText());
                controller.setSelectedRating(mainController.getReviewToEdit().getReviewNote());
                mainController.getScrollBox().getChildren().add(reviewPane);
            } else {
                if (title instanceof TvShow tvShow) {
                    controller.setSeasonBox(tvShow.getAllSeasons());
                    if (!tvShow.getSeasons().isEmpty()) {
                        controller.setEpisodeBox(tvShow.getSeasons().getFirst().getEpisodeList());
                    }
                    controller.turnVisible();
                }

                mainController.getScrollBox().getChildren().add(reviewPane);
            }

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML do CreateReview");
        }
    }

    public void turnVisible(){
        seasonBox.setVisible(true);
        episodeBox.setVisible(true);
    }

    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);
    }

    public void setOverviewField(String overviewField) {
        if (overviewField.length() > 540) {
            overviewField = overviewField.substring(0, 537) + "...";
        }
        this.overviewText.setText(overviewField);
    }

    public void setTitleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    public void setReleaseField(String releaseField){
    }

    public void setCurrentRating(int currentRating) {
        this.currentRating = currentRating;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setEpisodeBox(ArrayList<String> episodes) {
        this.episodeBox.getItems().clear();
        this.episodeBox.getItems().addAll(episodes);
    }

    public void setSeasonBox(ArrayList<Integer> seasons) {
         this.seasonBox.getItems().addAll(seasons);
    }

    public int getCurrentRating() {
        return currentRating;
    }

    public void setText(String text){
        this.reviewArea.setText(text);
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

    public String dateToYear(String data) {
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy");
        LocalDate date = LocalDate.parse(data, input);
        return date.format(output);
    }

    public void setSelectedRating(int rating) {
        currentRating = rating;

        for (int i = 0; i < stars.size(); i++) {
            if (i < rating) {
                stars.get(i).setFill(Color.YELLOW);
            } else {
                stars.get(i).setFill(Color.GRAY);
            }
        }
    }

    @FXML
    public void initialize() {

        seasonBox.setVisible(false);
        episodeBox.setVisible(false);

        stars = Arrays.asList(star1, star2, star3, star4, star5);

        for (int i = 0; i < stars.size(); i++) {
            final int rating = i + 1;
            stars.get(i).setOnMouseClicked(event -> setSelectedRating(rating));
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
