package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class EnderecoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String id;
    private String detalhes;
    private HttpStatus status;

    public EnderecoNotFoundException(String message, String id, String detalhes) {
        super(message);
        this.id = id;
        this.detalhes = detalhes;
        this.status = HttpStatus.NOT_FOUND;
    }

    public String getId() {
        return this.id;
    }

    public String getDetalhes() {
        return this.detalhes;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}