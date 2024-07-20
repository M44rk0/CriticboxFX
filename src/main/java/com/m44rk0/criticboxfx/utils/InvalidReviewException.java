package com.m44rk0.criticboxfx.utils;

public class InvalidReviewException extends Exception {

    // Construtor que aceita uma mensagem
    public InvalidReviewException(String message) {
        super(message);
    }

    // Construtor que aceita uma mensagem e uma causa
    public InvalidReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}
