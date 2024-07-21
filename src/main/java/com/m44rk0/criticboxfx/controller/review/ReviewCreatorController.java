package com.m44rk0.criticboxfx.controller.review;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.TitleReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.*;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.InvalidReviewException;
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

/**
 * Controlador responsável por criar e editar Reviews de títulos.
 */
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

    /**
     * Método chamado ao clicar no botão de retornar, que retorna a página dos resultados da busca.
     */
    @FXML
    void ReturnButtonClick() {
        if (mainController != null) {
            mainController.restoreSearchResults();
        }
    }

    /**
     * Salva a review atual, validando os campos e verificando se é uma nova Review ou uma edição de outra já existente.
     */
    @FXML
    public void saveReview() {
        try {
            if (mainController.theReviewIsEditable()) {
                int choice = AlertMessage.showChoiceAlert("Edição de Review", "Deseja editar a review?");
                Review reviewToEdit = mainController.getReviewToEdit();
                if (choice == 0) {
                    reviewToEdit.editReview(reviewArea.getText(), getCurrentRating());
                    mainController.setIfTheReviewIsEditable(false);
                    reviewDAO.editReview(reviewToEdit);
                }
                mainController.showUserReviews();
            } else {
                if (getCurrentRating() == 0 && reviewArea.getText().isBlank()) {
                    throw new InvalidReviewException("Esqueceu de tudo");
                } else if (getCurrentRating() == 0) {
                    throw new InvalidReviewException("Esqueceu da nota");
                } else if (reviewArea.getText().isEmpty()) {
                    throw new InvalidReviewException("Esqueceu da avaliação");
                } else if (!containsInReview(title)) {
                    if (title instanceof Film || (title instanceof TvShow &&
                            (episodeBox.getValue() == null || episodeBox.getValue().isEmpty()) &&
                            (seasonBox.getValue() == null || seasonBox.getValue() == 0))) {

                        var review = new TitleReview(title, getCurrentRating(), new Date(), reviewArea.getText());
                        userDAO.addReview(CurrentlyUser.getUser(), review);
                        CurrentlyUser.addReview(review);
                    } else {
                        var review = new EpisodeReview(title, getCurrentRating(), new Date(),
                                reviewArea.getText(), seasonBox.getValue(), episodeBox.getValue());

                        review.setSeason(((TvShow) title).getSeasonByNumber(seasonBox.getValue()));
                        userDAO.addReview(CurrentlyUser.getUser(), review);
                        CurrentlyUser.addReview(review);
                    }
                    mainController.restoreSearchResults();
                } else {
                    AlertMessage.showCommonAlert("Review já existe", "Essa Review já existe animal");
                }
            }
        } catch (InvalidReviewException e) {
            AlertMessage.showCommonAlert("Erro de Review", e.getMessage());
        }
    }

    /**
     * Verifica se uma review já existe para um determinado título.
     *
     * @param title o título a ser verificado
     * @return true se a review já existe, caso contrário false
     */
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
                    } else if (review.getTitle() instanceof TvShow) {
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

    /**
     * Exibe a interface para criação de uma nova Review.
     *
     * @param title o título para o qual a Review será criada
     */
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

            // verifica se a review que está sendo exibida é uma review nova ou uma edição
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

    /**
     * Torna visíveis os campos de seleção de temporada e episódio.
     */
    public void turnVisible() {
        seasonBox.setVisible(true);
        episodeBox.setVisible(true);
    }

    /**
     * Define a imagem do poster.
     *
     * @param posterImage a imagem do poster
     */
    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);
    }

    /**
     * Define o campo de visão geral.
     *
     * @param overviewField o texto da visão geral
     */
    public void setOverviewField(String overviewField) {
        if (overviewField.length() > 540) {
            overviewField = overviewField.substring(0, 537) + "...";
        }
        this.overviewText.setText(overviewField);
    }

    /**
     * Define o campo de título.
     *
     * @param titleField o título do campo
     */
    public void setTitleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    public void setReleaseField(String releaseField) {
    }

    /**
     * Define a avaliação atual.
     *
     * @param currentRating a avaliação atual
     */
    public void setCurrentRating(int currentRating) {
        this.currentRating = currentRating;
    }

    /**
     * Define o controlador principal.
     *
     * @param mainController o controlador principal
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Define o título atual.
     *
     * @param title o título atual
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * Define a lista de episódios no ComboBox.
     *
     * @param episodes a lista de episódios
     */
    public void setEpisodeBox(ArrayList<String> episodes) {
        this.episodeBox.getItems().clear();
        this.episodeBox.getItems().addAll(episodes);
    }

    /**
     * Define a lista de temporadas no ComboBox.
     *
     * @param seasons a lista de temporadas
     */
    public void setSeasonBox(ArrayList<Integer> seasons) {
        this.seasonBox.getItems().addAll(seasons);
    }

    /**
     * Retorna a avaliação atual.
     *
     * @return a avaliação atual
     */
    public int getCurrentRating() {
        return currentRating;
    }

    /**
     * Define o texto da Review.
     *
     * @param text o texto da Review
     */
    public void setText(String text) {
        this.reviewArea.setText(text);
    }

    /**
     * Atualiza o ComboBox de episódios com base na temporada selecionada.
     *
     * @param seasonNumber o número da temporada
     */
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

    /**
     * Converte a data de lançamento no formato "yyyy-MM-dd" para o formato "yyyy".
     *
     * @param data a data de lançamento
     * @return o ano de lançamento
     */
    public String dateToYear(String data) {
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy");
        LocalDate date = LocalDate.parse(data, input);
        return date.format(output);
    }

    /**
     * Define a avaliação selecionada e atualiza a visualização das estrelas.
     *
     * @param rating a avaliação selecionada
     */
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

    /**
     * Método de inicialização do controlador.
     */
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

