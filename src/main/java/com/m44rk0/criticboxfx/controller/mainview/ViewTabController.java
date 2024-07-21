package com.m44rk0.criticboxfx.controller.mainview;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

/**
 * Controlador da aba de visualização, responsável por gerenciar a exibição de resultados em uma aba específica.
 * Inclui métodos para obter o FlowPane de resultados e definir o texto da aba.
 */
public class ViewTabController {

    @FXML
    private FlowPane resultsFlow;

    @FXML
    private Tab resultsTab;

    /**
     * Obtém o FlowPane de resultados exibido na aba.
     *
     * @return o FlowPane de resultados exibido na aba
     */
    public FlowPane getResultsFlow() {
        return resultsFlow;
    }

    /**
     * Define o texto da aba de resultados.
     *
     * @param text o texto a ser definido na aba
     */
    public void setResultTabText(String text) {
        this.resultsTab.setText(text);
    }
}
