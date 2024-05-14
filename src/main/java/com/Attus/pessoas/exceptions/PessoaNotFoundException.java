package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class PessoaNotFoundException extends ApiBaseException {

    private static final long serialVersionUID = 1L;
    private String id;
    private String detalhes;

    public PessoaNotFoundException(String message, String id) {
        super(HttpStatus.NOT_FOUND, message);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getDetalhes() {
        return this.detalhes;
    }

    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }
}