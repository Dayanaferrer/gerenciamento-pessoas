package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

import com.Attus.pessoas.models.EnderecoModel;

public class EnderecoAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private EnderecoModel enderecoModel;
    private HttpStatus status;

    public EnderecoAlreadyExistsException(String message, EnderecoModel enderecoModel) {
        super(message);
        this.enderecoModel = enderecoModel;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public EnderecoAlreadyExistsException(String message, Throwable cause, EnderecoModel enderecoModel) {
        super(message, cause);
        this.enderecoModel = enderecoModel;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public EnderecoModel getEnderecoModel() {
        return this.enderecoModel;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}