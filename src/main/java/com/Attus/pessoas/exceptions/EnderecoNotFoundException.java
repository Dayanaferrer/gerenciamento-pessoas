package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class EnderecoNotFoundException extends ApiBaseException {

    private static final long serialVersionUID = 1L;
    private String endereco;

    public EnderecoNotFoundException(String message, String endereco) {
        super(HttpStatus.NOT_FOUND, message);
        this.endereco = endereco;
    }

    public String getEndereco() {
        return this.endereco;
    }
}