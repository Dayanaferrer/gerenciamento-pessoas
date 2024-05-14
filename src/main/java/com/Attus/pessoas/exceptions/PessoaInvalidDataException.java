package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class PessoaInvalidDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String detalhes;
    private HttpStatus status;

    public PessoaInvalidDataException(String message, String detalhes) {
        super(message);
        this.detalhes = detalhes;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public String getDetalhes() {
        return this.detalhes;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}