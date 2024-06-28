package com.m44rk0.criticboxfx.controller.review;
import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.m44rk0.criticboxfx.App.user;

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

    private ViewController mainController;
    private Review review;

    @Override
    public void setOverviewField(String overview) {
    }

    @Override
    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);

    }

    @FXML
    private void RemoveReview(){
        int areYouSure = AlertMessage.showChoiceAlert("Remoção da Review", "Você tem certeza que deseja remover essa review?");
        if(areYouSure == 0){
            user.removeReview(review);
            mainController.showUserReviews();
        }
    }

    @FXML
    private void EditReview(){
        mainController.setEditReviewIsCalledFrom(2);
        mainController.setReviewToEdit(review);
        mainController.showCreateReview(review.getTitle());
    }

    @Override
    public void setTittleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    @Override
    public void setReleaseField(String releaseDate) {
    }

    public void setWatchedField(Date reviewDate){
        this.watchedText.setText(formatDate(reviewDate));
    }

    public void setReviewField(String overviewField) {
        this.reviewText.setText(overviewField);
    }

    public void setInfoTVField(String infoTV){
        this.infoTVText.setText(infoTV);
    }

    public void setMainController(ViewController mainController) {
        this.mainController = mainController;
    }

    public void setReview(Review review){
        this.review = review;    }

    public void turnVisible(){
        infoTVField.setVisible(true);
    }

    private String formatDate(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));

        String dataFormatada = format.format(date);

        return "Assistido em " + dataFormatada;
    }

    public void setSelectedRating(int rating) {
        for (int i = 0; i < stars.size(); i++) {
            if (i < rating) {
                stars.get(i).setFill(javafx.scene.paint.Color.YELLOW);
            } else {
                stars.get(i).setFill(javafx.scene.paint.Color.GRAY);
            }
        }
    }

    public void initialize(){
        stars = Arrays.asList(star1, star2, star3, star4, star5);
        infoTVField.setVisible(false);
    }


}
