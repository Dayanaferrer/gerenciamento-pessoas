package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

public class PessoaAlreadyExistsException extends ApiBaseException {

    private static final long serialVersionUID = 1L;
    private String pessoa;

    public PessoaAlreadyExistsException(String message, String pessoa) {
        super(HttpStatus.BAD_REQUEST, message);
        this.pessoa = pessoa;
    }

    public String getPessoa() {
        return this.pessoa;
    }

    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }
}