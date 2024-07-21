package com.m44rk0.criticboxfx.model.review;

import com.m44rk0.criticboxfx.model.title.Title;

import java.util.Date;


/**
 * Classe que representa uma Review de um título genérico, que pode ser um filme ou uma série.
 * Estende a classe {@link Review} para incluir detalhes específicos do título revisado.
 */
public class TitleReview extends Review {

    /**
     * Construtor para criar uma nova Review de título.
     *
     * @param title O título que está sendo revisado. Pode ser um filme ou uma série.
     * @param reviewNote A nota da Review, normalmente uma classificação numérica.
     * @param reviewDate A data em que a Review foi escrita.
     * @param reviewText O texto da Review, descrevendo a opinião do revisor.
     */
    public TitleReview(Title title, Integer reviewNote, Date reviewDate, String reviewText) {
        super(title, reviewNote, reviewDate, reviewText);
    }

    /**
     * Construtor para recuperar do Banco de Dados e criar uma Review de título já existente.
     *
     * @param reviewID O ID da Review, usado para identificar a Review no banco de dados.
     * @param title O título que está sendo revisado. Pode ser um filme ou uma série.
     * @param reviewNote A nota da Review, normalmente uma classificação numérica.
     * @param reviewDate A data em que a Review foi escrita.
     * @param reviewText O texto da Review, descrevendo a opinião do revisor.
     */
    public TitleReview(Integer reviewID, Title title, Integer reviewNote, Date reviewDate, String reviewText) {
        super(title, reviewNote, reviewDate, reviewText);
        this.reviewID = reviewID;
    }
}

