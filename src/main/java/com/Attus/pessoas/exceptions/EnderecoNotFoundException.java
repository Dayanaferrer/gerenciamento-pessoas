package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class EnderecoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String endereco;
    private HttpStatus status;

    public EnderecoNotFoundException(String message, String endereco) {
        super(message);
        this.endereco = endereco;
        this.status = HttpStatus.NOT_FOUND;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}