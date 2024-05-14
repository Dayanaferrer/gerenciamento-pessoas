package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class PessoaNotEditableException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String detalhes;
    private HttpStatus status;

    public PessoaNotEditableException(String message, String detalhes, HttpStatus status) {
        super(message);
        this.detalhes = detalhes;
        this.status = status;
    }

    public String getDetalhes() {
        return this.detalhes;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}