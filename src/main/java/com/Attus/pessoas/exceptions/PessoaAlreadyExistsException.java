package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class PessoaAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String pessoa;
    private HttpStatus status;

    public PessoaAlreadyExistsException(String message, String pessoa) {
        super(message);
        this.pessoa = pessoa;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public String getPessoa() {
        return this.pessoa;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}