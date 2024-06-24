package com.m44rk0.criticboxfx.controller.review;
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


    @Override
    public void setPosterImage(String posterImage) {
        Image poster = new Image("https://image.tmdb.org/t/p/original/" + posterImage, 250, 360, false, false);
        this.posterImage.setImage(poster);
    }

    @Override
    public void setOverviewField(String overview) {
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

    public void turnVisible(){
        infoTVField.setVisible(true);
    }

    private String formatDate(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", new Locale("pt", "BR"));

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
