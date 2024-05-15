package com.Attus.pessoas.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRecordDto(
	    Long id,
	    @NotBlank String logradouro,
	    @NotBlank String cep,
	    @NotBlank String numero,
	    @NotBlank String cidade,
	    @NotBlank String estado,
	    Boolean principal
) {}