package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class PessoaInvalidDataException extends ApiBaseException {

    private static final long serialVersionUID = 1L;
    private String detalhes;

    public PessoaInvalidDataException(String message, String detalhes) {
        super(HttpStatus.BAD_REQUEST, message);
        this.detalhes = detalhes;
    }

    public String getDetalhes() {
        return this.detalhes;
    }

    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }
}