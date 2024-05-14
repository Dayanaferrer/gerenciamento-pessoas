package com.Attus.pessoas.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.repositories.EnderecoRepository;
import com.Attus.pessoas.services.PessoaService;
import com.Attus.pessoas.models.EnderecoModel;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    
    @Autowired
    private PessoaConverter pessoaConverter;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    public ResponseEntity<PessoaModel> createPessoa(@RequestBody PessoaRecordDto pessoaDto) {
        PessoaModel pessoa = pessoaConverter.toModel(pessoaDto);
        PessoaModel createdPessoa = pessoaService.createPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPessoa);
    }

}