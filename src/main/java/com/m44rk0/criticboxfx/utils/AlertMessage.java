package com.m44rk0.criticboxfx.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * A classe {@code AlertMessage} fornece métodos estáticos para exibir diferentes tipos de alertas ao usuário.
 *
 * <p>Estes métodos utilizam a classe {@link Alert} do JavaFX para mostrar caixas de diálogo com diferentes
 * tipos de mensagens e opções para o usuário.</p>
 */
public class AlertMessage {

    /**
     * Exibe um alerta comum com um título e um conteúdo especificado.
     *
     * <p>Este método cria uma caixa de diálogo sem um tipo de alerta específico, mas com um botão "OK".</p>
     *
     * @param title O título do alerta.
     * @param content O conteúdo da mensagem do alerta.
     */
    public static void showCommonAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Exibe um alerta de erro com um título e um conteúdo especificado.
     *
     * <p>Este método cria uma caixa de diálogo com um tipo de alerta de erro, mostrando um ícone de erro e uma mensagem.</p>
     *
     * @param title O título do alerta.
     * @param content O conteúdo da mensagem do alerta.
     */
    public static void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Exibe um alerta de escolha com um título e um conteúdo especificado, oferecendo opções para o usuário.
     *
     * <p>Este método cria uma caixa de diálogo com botões "Sim" e "Não". O método retorna um valor inteiro que
     * indica a escolha do usuário:</p>
     * <ul>
     *   <li>{@code 0} para "Sim"</li>
     *   <li>{@code 1} para "Não"</li>
     * </ul>
     *
     * @param title O título do alerta.
     * @param content O conteúdo da mensagem do alerta.
     * @return {@code 0} se o usuário selecionar "Sim", {@code 1} se o usuário selecionar "Não".
     */
    public static int showChoiceAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(content);

        ButtonType buttonTypeYes = new ButtonType("Sim");
        ButtonType buttonTypeNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            return 0; // Sim
        } else {
            return 1; // Não
        }
    }

}
