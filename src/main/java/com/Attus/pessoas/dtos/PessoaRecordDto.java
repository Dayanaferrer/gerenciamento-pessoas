package com.Attus.pessoas.dtos;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PessoaRecordDto(
        Long id,
        @NotBlank String nomeCompleto,
        @NotNull LocalDate dataNascimento,
        @NotNull EnderecoRecordDto enderecoPrincipal,
        List<EnderecoRecordDto> enderecosSecundarios
) {}