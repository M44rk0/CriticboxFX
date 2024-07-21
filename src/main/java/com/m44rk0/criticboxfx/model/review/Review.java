package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;

/**
 * Classe abstrata que representa uma Review de um título.
 */
public abstract class Review {

    /**
     * ID único da Review.
     */
    protected Integer reviewID;

    /**
     * Data em que a Review foi feita.
     */
    private final Date reviewDate;

    /**
     * Nota da Review atribuída ao título.
     */
    private Integer reviewNote;

    /**
     * Texto da Review escrita pelo usuário.
     */
    private String reviewText;

    /**
     * Título que está sendo avaliado na Review.
     */
    private Title title;

    /**
     * Construtor para criar uma nova Review.
     *
     * @param title O título que está sendo avaliado.
     * @param reviewNote A nota da Review (avaliação).
     * @param reviewDate A data em que a Review foi feita.
     * @param reviewText O texto da Review.
     */
    public Review(Title title, Integer reviewNote, Date reviewDate, String reviewText) {
        this.title = title;
        this.reviewNote = reviewNote;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
    }

    /**
     * Obtém o texto da Review.
     *
     * @return O texto da Review.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Obtém o título associado à Review.
     *
     * @return O título avaliado.
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Define o título associado à Review.
     *
     * @param title O título a ser associado à Review.
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * Obtém a nota da Review.
     *
     * @return A nota da Review.
     */
    public Integer getReviewNote() {
        return reviewNote;
    }

    /**
     * Obtém a data da Review.
     *
     * @return A data em que a Review foi feita.
     */
    public Date getReviewDate() {
        return reviewDate;
    }

    /**
     * Edita o texto e a nota da Review.
     *
     * @param text O novo texto da Review.
     * @param rating A nova nota da Review.
     */
    public void editReview(String text, Integer rating) {
        this.reviewText = text;
        this.reviewNote = rating;
    }

    /**
     * Obtém o ID da Review.
     *
     * @return O ID da Review.
     */
    public int getReviewID() {
        return reviewID;
    }

    /**
     * Define o ID da Review.
     *
     * @param reviewId O ID a ser definido para a Review.
     */
    public void setReviewID(int reviewId) {
        this.reviewID = reviewId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", title=" + title +
                ", reviewNote=" + reviewNote +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}

