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

/**
 * Controlador para exibir detalhes de um título, incluindo filme ou série de TV.
 * <p>
 * Esta classe gerencia a interface gráfica do usuário para a visualização de detalhes de um título obtidos pelo {@link TitleDetails}, incluindo
 * informações sobre o título, gênero, elenco, equipe de produção, etc.
 * </p>
 */
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

    /**
     * Exibe os detalhes do título fornecido na interface do usuário.
     * <p>
     * Carrega o layout FXML para exibição dos detalhes e preenche os campos com as informações do título.
     * </p>
     *
     * @param title O título cujos detalhes serão exibidos.
     */
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
        }
        catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Details");
        }
    }

    /**
     * Manipula o clique do botão de retorno, restaurando a visualização anterior com base na origem da chamada.
     */
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

    /**
     * Abre a tela de criação de Review para o título atual.
     * Define o modo de edição da Review como não editável.
     */
    @FXML
    void makeReview(){
        mainController.showCreateReview(title);
        mainController.setIfTheReviewIsEditable(false);
    }

    /**
     * Adiciona ou remove o título atual dos favoritos do usuário.
     * Atualiza o ícone de favorito de acordo.
     */
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

    /**
     * Define o controlador principal para este controlador.
     *
     * @param mainController O controlador principal a ser definido.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Define o título a ser exibido nos detalhes.
     *
     * @param title O título a ser definido.
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * Define o campo de duração na interface do usuário.
     *
     * @param durationField A duração do título em minutos.
     */
    public void setDurationField(Integer durationField){
        this.durationText.setText(durationField + " min");
    }

    /**
     * Define o campo de data de lançamento na interface do usuário.
     *
     * @param releaseField A data de lançamento do título.
     */
    public void setReleaseField(String releaseField){
        this.releaseText.setText(releaseField);
    }

    /**
     * Define o campo de título na interface do usuário.
     *
     * @param titleField O título a ser exibido.
     */
    public void setTitleField(String titleField) {
        this.tittleText.setText(titleField);
    }

    /**
     * Define o campo de visão geral na interface do usuário.
     *
     * @param overviewField A visão geral do título.
     */
    public void setOverviewField(String overviewField) {
        this.overviewText.setText(overviewField);
    }

    /**
     * Define o {@link FlowPane} de gêneros na interface do usuário.
     *
     * @param genres Lista de gêneros a serem exibidos.
     */
    public void setGenreFlow(ArrayList<String> genres) {
        setFlow(genres, genresFlow);
    }

    /**
     * Define o {@link FlowPane} de diretores na interface do usuário.
     *
     * @param directors Lista de diretores a serem exibidos.
     */
    public void setDirectorFlow(ArrayList<String> directors) {
        setFlow(directors, directorFlow);
    }

    /**
     * Define o {@link FlowPane} de elenco na interface do usuário.
     *
     * @param cast Lista de membros do elenco a serem exibidos.
     */
    public void setCastFlow(ArrayList<String> cast) {
        setFlow(cast, castFlow);
    }

    /**
     * Define o {@link FlowPane} de roteiristas na interface do usuário.
     *
     * @param writers Lista de roteiristas a serem exibidos.
     */
    public void setWriterFlow(ArrayList<String> writers) {
        setFlow(writers, writerFlow);
    }

    /**
     * Define o {@link FlowPane} de produtores na interface do usuário.
     *
     * @param producers Lista de produtores a serem exibidos.
     */
    public void setProducerFlow(ArrayList<String> producers) {
        setFlow(producers, producerFlow);
    }

    /**
     * Define o {@link FlowPane} de diretores de arte na interface do usuário.
     *
     * @param artDirectors Lista de diretores de arte a serem exibidos.
     */
    public void setArtDirectFlow(ArrayList<String> artDirectors) {
        setFlow(artDirectors, artDirectFlow);
    }

    /**
     * Define o {@link FlowPane} de equipe de som na interface do usuário.
     *
     * @param soundMembers Lista de membros da equipe de som a serem exibidos.
     */
    public void setSoundFlow(ArrayList<String> soundMembers) {
        setFlow(soundMembers, soundFlow);
    }

    /**
     * Define o {@link FlowPane} de equipe de câmera na interface do usuário.
     *
     * @param cameraMembers Lista de membros da equipe de câmera a serem exibidos.
     */
    public void setCameraFlow(ArrayList<String> cameraMembers) {
        setFlow(cameraMembers, cameraFlow);
    }

    /**
     * Define o {@link FlowPane} de efeitos visuais na interface do usuário.
     *
     * @param vfxMembers Lista de membros da equipe de efeitos visuais a serem exibidos.
     */
    public void setVfxFlow(ArrayList<String> vfxMembers) {
        setFlow(vfxMembers, vfxFlow);
    }

    /**
     * Define a imagem do pôster na interface do usuário.
     *
     * @param posterImage A imagem do pôster a ser exibida.
     */
    public void setPosterImage(Image posterImage) {
        this.posterImage.setImage(posterImage);
    }

    /**
     * Define o ícone de estrela de favorito com base no estado fornecido.
     *
     * @param fillFavoriteStar O conteúdo SVG para o ícone de favorito.
     */
    public void setFillFavoriteStar(String fillFavoriteStar){
        favoriteStar.setContent(fillFavoriteStar);
    }

    /**
     * Atualiza o ícone de estrela de favorito para indicar se o título está ou não nos favoritos.
     */
    private void setFavoriteIcon() {
        favoriteStar.setContent(
                favoriteStar.getContent().equals(Icon.UNFILLED_STAR.getPath()) ? Icon.FILLED_STAR.getPath() : Icon.UNFILLED_STAR.getPath()
        );
        favoriteStar.setFill(Color.WHITE);
    }

    /**
     * Copia os estilos de texto de um campo de texto para outro.
     *
     * @param source O campo de texto de origem.
     * @param target O campo de texto de destino.
     */
    private void copyTextStyles(Text source, Text target) {
        target.setFont(source.getFont());
        target.setFill(source.getFill());
        target.setStyle(source.getStyle());
        target.getStyleClass().addAll(source.getStyleClass());
    }

    /**
     * Altera a exibição do campo de duração para exibir o número de temporadas para uma série de TV.
     *
     * @param tvshow A série de TV cujas temporadas serão exibidas.
     */
    public void changeDurationToSeasons(TvShow tvshow){
        durationLabelText.setText("Temporadas");
        durationText.setText(String.valueOf(tvshow.getSeasons().size()));
    }

    /**
     * Torna visível o campo de episódios para uma série de TV.
     *
     * @param tvshow A série de TV cujos episódios serão exibidos.
     */
    public void turnEpisodesVisible(TvShow tvshow){
        episodesText.setText(String.valueOf(tvshow.getTotalEpisodes()));
        episodesLabel.setVisible(true);
        episodesField.setVisible(true);
    }

    /**
     * Inicializa o controlador, definindo o estado inicial dos componentes da interface do usuário.
     */
    @FXML
    public void initialize(){
        episodesLabel.setVisible(false);
        episodesField.setVisible(false);
    }

    /**
     * Define o {@link FlowPane} de membros em um {@code FlowPane} com base em uma lista de strings.
     *
     * @param members A lista de membros a ser exibida.
     * @param flowPane O {@code FlowPane} onde os membros serão adicionados.
     */
    private void setFlow(ArrayList<String> members, FlowPane flowPane) {
        flowPane.getChildren().clear();

        for (String member : members) {
            Text memberTextCopy = new Text(member);
            copyTextStyles(memberText, memberTextCopy);

            TextFlow memberField = new TextFlow(memberTextCopy);
            memberField.getStyleClass().add("memberFlow");
            memberField.setBlendMode(BlendMode.ADD);
            memberField.setPadding(new Insets(5, 5, 5, 5));

            flowPane.getChildren().add(memberField);
        }
    }
}

