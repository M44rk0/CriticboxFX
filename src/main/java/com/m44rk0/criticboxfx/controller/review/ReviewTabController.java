package com.m44rk0.criticboxfx.controller.review;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

/**
 * Controlador responsável por gerenciar a aba de reviews.
 */
public class ReviewTabController {

    @FXML
    private FlowPane reviewsFlow;

    /**
     * Retorna o FlowPane que contém as reviews.
     *
     * @return o FlowPane que contém as reviews
     */
    public FlowPane getReviewsFlow() {
        return reviewsFlow;
    }

}

