package com.m44rk0.criticboxfx.controller.review;
import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.m44rk0.criticboxfx.App.userDAO;
import static com.m44rk0.criticboxfx.model.search.Search.seasonPosterCache;
import static com.m44rk0.criticboxfx.model.search.Search.titlePosterCache;

/**
 * Controlador para gerenciar a exibição e edição de Reviews do usuário.
 */
public class ReviewController implements CommonController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Text reviewText;

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
    private Text tittleText;

    @FXML
    private Text watchedText;

    @FXML
    private TextFlow infoTVField;

    @FXML
    private Text infoTVText;

    private List<SVGPath> stars;

    private MainController mainController;
    private Review review;

    /**
     * Carrega as Reviews do usuário atual e as exibe na interface.
     */
    public void showUserReviews() {
        try {
            mainController.resetScrollBox();
            mainController.getScrollPage().setVvalue(0);

            List<Review> userReviews = CurrentlyUser.getReviews().reversed();
            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("reviewsTab.fxml"));
            TabPane reviewTab = tabLoader.load();
            ReviewTabController tabController = tabLoader.getController();

            FlowPane reviewFlow = tabController.getReviewsFlow();

            for (Review review : userReviews) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userReview.fxml"));
                Pane reviewPane = loader.load();
                ReviewController controller = loader.getController();

                controller.setReview(review);
                controller.setMainController(mainController);
                controller.setTitleField(review.getTitle().getName());
                controller.setPosterImage(titlePosterCache.get(review.getTitle()));
                controller.setReviewField("\"" + review.getReviewText() + "\"");
                controller.setWatchedField(review.getReviewDate());
                controller.setSelectedRating(review.getReviewNote());

                if (review instanceof EpisodeReview episodeReview) {
                    int seasonNumber = episodeReview.getSeasonNumber();
                    String episodeName = episodeReview.getEpisodeName();
                    TvShow tvShow = (TvShow) review.getTitle();
                    Season season = tvShow.getSeasons().stream()
                            .filter(s -> s.getSeasonNumber() == seasonNumber)
                            .findFirst()
                            .orElse(null);

                    if (season != null && tvShow.getSeasons().size() > 1) {
                        if (!seasonPosterCache.containsKey(episodeReview.getSeason())) {
                            Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                    season.getSeasonPosterPath(), 250, 350, false, false);
                            controller.setPosterImage(posterImage);
                            seasonPosterCache.put(episodeReview.getSeason(), posterImage);
                        } else {
                            controller.setPosterImage(seasonPosterCache.get(episodeReview.getSeason()));
                        }
                    } else {
                        controller.setPosterImage(titlePosterCache.get(tvShow));
                    }

                    controller.setInfoTVField(seasonNumber + "ª Temporada - " + episodeName);
                    controller.turnVisible();
                }

                reviewFlow.getChildren().add(reviewPane);
            }

            mainController.getScrollBox().getChildren().add(reviewTab);
            mainController.getScrollPage().setFitToHeight(userReviews.size() <= 1);

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML do UserReview");
        }
    }

    /**
     * Remove a review do usuário após confirmação.
     */
    @FXML
    private void RemoveReview() {
        int areYouSure = AlertMessage.showChoiceAlert("Remoção da Review", "Você tem certeza que deseja remover essa review?");
        if (areYouSure == 0) {
            userDAO.removeReview(CurrentlyUser.getUser(), review);
            CurrentlyUser.removeReview(review);
            mainController.showUserReviews();
        }
    }

    /**
     * Edita a review do usuário.
     */
    @FXML
    private void EditReview() {
        mainController.getReviewCreatorController().setReviewToEdit(review);
        mainController.getReviewCreatorController().setIfTheReviewIsEditable(true);
        System.out.println("teste");
        mainController.showCreateReview(review.getTitle());
    }

    /**
     * Define o campo da data de assistido.
     *
     * @param reviewDate A data da review.
     */
    public void setWatchedField(Date reviewDate) {
        this.watchedText.setText(formatDate(reviewDate));
    }

    /**
     * Define o campo da review.
     *
     * @param overviewField O campo da review.
     */
    public void setReviewField(String overviewField) {
        this.reviewText.setText(overviewField);
    }

    /**
     * Define o campo de informações da série de TV.
     *
     * @param infoTV As informações da série de TV.
     */
    public void setInfoTVField(String infoTV) {
        this.infoTVText.setText(infoTV);
    }

    /**
     * Define a review.
     *
     * @param review A review.
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Torna o campo de informações de uma série de TV visível.
     */
    public void turnVisible() {
        infoTVField.setVisible(true);
    }

    /**
     * Formata a data para o formato "Assistido em dd 'de' MMM 'de' yyyy".
     *
     * @param date A data a ser formatada.
     * @return A data formatada como string.
     */
    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));
        return "Assistido em " + format.format(date);
    }

    /**
     * Define a classificação selecionada exibindo as estrelas.
     *
     * @param rating A classificação selecionada.
     */
    public void setSelectedRating(int rating) {
        for (int i = 0; i < stars.size(); i++) {
            if (i < rating) {
                stars.get(i).setFill(javafx.scene.paint.Color.YELLOW);
            } else {
                stars.get(i).setFill(javafx.scene.paint.Color.GRAY);
            }
        }
    }

    /**
     * Inicializa os componentes do controlador.
     */
    public void initialize() {
        stars = Arrays.asList(star1, star2, star3, star4, star5);
        infoTVField.setVisible(false);
    }

    @Override
    public void setTitle(Title title) {
    }

    @Override
    public void setTitleField(String titleField) {
        if (titleField.length() > 45) {
            titleField = titleField.substring(0, 42) + "...";
        }
        this.tittleText.setText(titleField);
    }

    @Override
    public void setOverviewField(String overview) {
    }

    @Override
    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);
    }

    @Override
    public void setReleaseField(String releaseDate) {
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}

