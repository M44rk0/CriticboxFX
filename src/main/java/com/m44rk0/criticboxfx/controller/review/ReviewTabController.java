package com.m44rk0.criticboxfx.controller.review;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

public class ReviewTabController {

    @FXML
    private TabPane TabPaneReviews;

    @FXML
    private FlowPane reviewsFlow;

    @FXML
    private Tab reviewsTab;

    public TabPane getTabPaneReviews() {
        return TabPaneReviews;
    }

    public void setTabPaneReviews(TabPane tabPaneReviews) {
        TabPaneReviews = tabPaneReviews;
    }

    public FlowPane getReviewsFlow() {
        return reviewsFlow;
    }

    public void setReviewsFlow(FlowPane reviewsFlow) {
        this.reviewsFlow = reviewsFlow;
    }

    public Tab getReviewsTab() {
        return reviewsTab;
    }

    public void setReviewsTab(Tab reviewsTab) {
        this.reviewsTab = reviewsTab;
    }
}
