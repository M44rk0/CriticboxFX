package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import java.util.Date;

/**
 * Classe que representa uma Review de um episódio específico de uma série.
 * Extende a classe {@link Review} para incluir detalhes específicos do episódio e da temporada.
 */
public class EpisodeReview extends Review {

    /**
     * Temporada à qual o episódio pertence.
     * Pode ser definido posteriormente.
     */
    private Season season;

    /**
     * Número da temporada em que o episódio foi exibido.
     */
    private final Integer seasonNumber;

    /**
     * Nome do episódio revisado.
     */
    private final String episodeName;

    /**
     * Construtor para criar uma nova Review de episódio.
     *
     * @param title O título do episódio.
     * @param reviewNote A nota da Review.
     * @param reviewDate A data em que a Review foi feita.
     * @param reviewText O texto da Review.
     * @param seasonNumber O número da temporada à qual o episódio pertence.
     * @param episodeName O nome do episódio.
     */
    public EpisodeReview(Title title, Integer reviewNote, Date reviewDate, String reviewText, Integer seasonNumber, String episodeName) {
        super(title, reviewNote, reviewDate, reviewText);
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
    }

    /**
     * Construtor para recuperar do Banco de Dados e criar uma Review de episódio que ja existente.
     *
     * @param reviewId O ID da Review.
     * @param title O título do episódio.
     * @param reviewNote A nota da Review.
     * @param reviewDate A data em que a Review foi feita.
     * @param reviewText O texto da Review.
     * @param seasonNumber O número da temporada à qual o episódio pertence.
     * @param episodeName O nome do episódio.
     */
    public EpisodeReview(Integer reviewId, Title title, Integer reviewNote, Date reviewDate, String reviewText, Integer seasonNumber, String episodeName) {
        super(title, reviewNote, reviewDate, reviewText);
        this.reviewID = reviewId;
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
    }

    /**
     * Obtém a temporada à qual o episódio pertence.
     *
     * @return A temporada associada à Review do episódio.
     */
    public Season getSeason() {
        return season;
    }

    /**
     * Define a temporada à qual o episódio pertence.
     *
     * @param season A temporada a ser associada à Review do episódio.
     */
    public void setSeason(Season season) {
        this.season = season;
    }

    /**
     * Obtém o número da temporada em que o episódio foi exibido.
     *
     * @return O número da temporada.
     */
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Obtém o nome do episódio revisado.
     *
     * @return O nome do episódio.
     */
    public String getEpisodeName() {
        return episodeName;
    }
}

