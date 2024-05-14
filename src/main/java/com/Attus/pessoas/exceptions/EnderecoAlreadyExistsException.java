package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;

import com.Attus.pessoas.models.EnderecoModel;

public class EnderecoAlreadyExistsException extends ApiBaseException {

    private static final long serialVersionUID = 1L;
    private EnderecoModel enderecoModel;

    public EnderecoAlreadyExistsException(String message, EnderecoModel enderecoModel) {
        super(HttpStatus.BAD_REQUEST, message);
        this.enderecoModel = enderecoModel;
    }

    public EnderecoAlreadyExistsException(String message, Throwable cause, EnderecoModel enderecoModel) {
        super(HttpStatus.BAD_REQUEST, message, cause);
        this.enderecoModel = enderecoModel;
    }

    public EnderecoModel getEnderecoModel() {
        return this.enderecoModel;
    }
}