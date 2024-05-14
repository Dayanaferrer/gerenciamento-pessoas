package com.Attus.pessoas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EnderecoBadRequestException extends ApiBaseException {

    private static final long serialVersionUID = 1L;
    private String detalhes;

    public EnderecoBadRequestException(String message, String detalhes) {
        super(HttpStatus.BAD_REQUEST, message);
        this.detalhes = detalhes;
    }

    public String getDetalhes() {
        return this.detalhes;
    }
}