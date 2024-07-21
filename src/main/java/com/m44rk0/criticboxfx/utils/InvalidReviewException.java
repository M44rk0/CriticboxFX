package com.m44rk0.criticboxfx.utils;

/**
 * Exceção personalizada {@code InvalidReviewException} para representar erros relacionados a avaliações inválidas.
 * <p>
 * Esta exceção é lançada quando uma operação com uma avaliação não é válida, como quando uma avaliação não atende
 * aos critérios esperados ou contém dados incorretos.
 * </p>
 */
public class InvalidReviewException extends Exception {

    /**
     * Construtor que aceita uma mensagem de erro.
     *
     * @param message A mensagem que descreve o motivo da exceção.
     */
    public InvalidReviewException(String message) {
        super(message);
    }

    /**
     * Construtor que aceita uma mensagem de erro e uma causa.
     *
     * @param message A mensagem que descreve o motivo da exceção.
     * @param cause   A causa que provocou esta exceção. Pode ser outra exceção que causou esta exceção.
     */
    public InvalidReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}

