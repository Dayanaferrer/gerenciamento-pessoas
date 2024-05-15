package com.Attus.pessoas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.services.EnderecoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um novo endereço")
    public ResponseEntity<EnderecoRecordDto> createEndereco(@RequestBody EnderecoRecordDto enderecoDto) {
        EnderecoRecordDto savedEndereco = enderecoService.saveEndereco(enderecoDto);
        return new ResponseEntity<>(savedEndereco, HttpStatus.CREATED);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar todos os endereços")
    public List<EnderecoRecordDto> consultarTodosEnderecos() {
        return enderecoService.consultarTodosEnderecos();
    }

    @GetMapping("/{pessoaId}/principal")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar endereço principal de uma pessoa")
    public EnderecoRecordDto consultarEnderecoPrincipal(@PathVariable Long pessoaId) {
        return enderecoService.consultarEnderecoPrincipal(pessoaId);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar um endereço por id")
    public EnderecoRecordDto consultarEnderecoPorId(@PathVariable Long id) {
        return enderecoService.consultarEnderecoPorId(id);
    }

}