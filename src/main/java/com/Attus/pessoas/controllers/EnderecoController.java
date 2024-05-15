package com.Attus.pessoas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.services.EnderecoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/pessoas/{pessoaId}/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novos endereços vinculados a id de pessoa")
    public List<EnderecoRecordDto> criarEnderecos(@PathVariable Long pessoaId, @RequestBody List<EnderecoRecordDto> enderecoDtos) {
        return enderecoService.criarEnderecos(enderecoDtos, pessoaId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Editar endereços")
    public List<EnderecoRecordDto> editarEndereco(@PathVariable Long pessoaId, @RequestBody List<EnderecoRecordDto> enderecoDtos) {
        return enderecoService.editarEndereco(enderecoDtos, pessoaId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar todos os endereços")
    public List<EnderecoRecordDto> consultarTodosEnderecos() {
        return enderecoService.consultarTodosEnderecos();
    }


    @GetMapping("/principal")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar endereço principal de uma pessoa")
    public EnderecoRecordDto consultarEnderecoPrincipal(@PathVariable Long pessoaId) {
        return enderecoService.consultarEnderecoPrincipal(pessoaId);
    }
}