package com.m44rk0.criticboxfx.controller.details;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.mainview.ViewTabController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.Icon;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.m44rk0.criticboxfx.App.titleDAO;
import static com.m44rk0.criticboxfx.App.userDAO;

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

    //exibe os resultados da busca na tela
    public void showSearchResults(List<Title> searchResults) {
        try {
            mainController.resetScrollBox();
            mainController.getSearchResultNodes().clear();

            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("resultsTab.fxml"));
            TabPane resultsTab = tabLoader.load();
            ViewTabController tabController = tabLoader.getController();
            tabController.setResultTabText("Resultados para: " + "\"" + mainController.getSearchField().getText() + "\"");
            FlowPane resultsPane = tabController.getResultsFlow();

            if (!mainController.getLastSearchedTitles().isEmpty()) {
                titleDAO.clearLastResults();
            }

            for (Title title : searchResults) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("titleInfo.fxml"));
                Pane movieInfoPane = loader.load();
                TitleInfoController controller = loader.getController();

                mainController.setCommonFields(controller, title);

                if (title instanceof TvShow) {
                    controller.setSeasonField(((TvShow) title).getSeasons().size() + " Temporada(s)");
                    controller.setEpisodesField(((TvShow) title).getTotalEpisodes() + " Episódio(s)");
                    controller.turnVisible();
                }

                if (CurrentlyUser.getWatched().contains(title)) {
                    controller.setWatchedIcon(Icon.WATCHED.getPath());
                }

                resultsPane.getChildren().add(movieInfoPane);

                mainController.getSearchResultNodes().add(movieInfoPane);

                if (!titleDAO.getAllTitleIds().contains(title.getTitleId())) {
                    titleDAO.addTitle(title);
                }

                titleDAO.addTitleToLastResults(title);
            }

            mainController.getScrollBox().getChildren().add(resultsTab);

            mainController.getScrollPage().setFitToHeight(searchResults.size() == 1);

            if (searchResults.isEmpty()) {
                tabController.setResultTabText("Nenhum resultado existente para: " + "\"" + mainController.getSearchField().getText() + "\"");
                mainController.getScrollPage().setFitToHeight(true);
            }

        } catch (IOException | SQLException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    //restaura os resultados de busca salvos
    public void restoreSearchResults() {
        try {
            mainController.resetScrollBox();
            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("resultsTab.fxml"));
            TabPane resultsTab = tabLoader.load();
            ViewTabController tabController = tabLoader.getController();
            FlowPane resultsPane = tabController.getResultsFlow();

            if (mainController.getSearchResultNodes().isEmpty()) {
                for (Title title : mainController.getLastSearchedTitles()) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("titleInfo.fxml"));
                    Pane movieInfoPane = loader.load();
                    TitleInfoController controller = loader.getController();

                    mainController.setCommonFields(controller, title);

                    if (title instanceof TvShow) {
                        controller.setSeasonField(((TvShow) title).getSeasons().size() + " Temporada(s)");
                        controller.setEpisodesField(((TvShow) title).getTotalEpisodes() + " Episódio(s)");
                        controller.turnVisible();
                    }

                    if (CurrentlyUser.getWatched().contains(title)) {
                        controller.setWatchedIcon(Icon.WATCHED.getPath());
                    }

                    resultsPane.getChildren().add(movieInfoPane);
                    mainController.getSearchResultNodes().add(movieInfoPane);
                }
                mainController.getScrollBox().getChildren().add(resultsTab);
            } else {
                resultsPane.getChildren().addAll(mainController.getSearchResultNodes());
                mainController.getScrollBox().getChildren().add(resultsTab);
            }

            mainController.getScrollPage().setFitToHeight(mainController.getSearchResultNodes().size() == 1 || mainController.getSearchResultNodes().isEmpty());

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    @FXML
    public void addToWatched(){
        if (CurrentlyUser.getWatched().contains(title)) {
            CurrentlyUser.removeWatched(title);
            userDAO.removeWatched(CurrentlyUser.getUser(), title);
        } else {
            CurrentlyUser.addWatched(title);
            userDAO.addWatched(CurrentlyUser.getUser(), title);
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
        mainController.setIfTheReviewIsEditable(false);
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
