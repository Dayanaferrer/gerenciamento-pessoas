package com.Attus.pessoas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaConverter pessoaConverter;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova pessoa")
    public PessoaRecordDto createPessoa(@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
        PessoaModel pessoaModel = pessoaConverter.dtoToEntity(pessoaRecordDto);
        PessoaModel createdPessoa = pessoaService.createPessoa(pessoaModel);
        return pessoaConverter.entityToDto(createdPessoa);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar todas as pessoas")
    public List<PessoaRecordDto> buscarTodas() {
        return pessoaService.buscarTodas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar uma pessoa por ID")
    public PessoaRecordDto getPessoaById(@PathVariable Long id) {
        PessoaModel pessoa = pessoaService.getPessoaById(id);    
        return pessoaConverter.entityToDto(pessoa);
    }

    @GetMapping("/nome/{nomeCompleto}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar pessoas pelo nome")
    public List<PessoaRecordDto> getPessoasByNomeCompleto(@PathVariable String nomeCompleto) {
        return pessoaService.getPessoasByNomeCompleto(nomeCompleto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Editar uma pessoa")
    public PessoaRecordDto updatePessoa(@PathVariable Long id, @RequestBody @Valid PessoaRecordDto pessoaDto) {
        PessoaModel pessoaToUpdate = pessoaConverter.dtoToEntity(pessoaDto);
        pessoaToUpdate.setId(id);
        PessoaModel updatedPessoa = pessoaService.updatePessoa(pessoaToUpdate);
        return pessoaConverter.entityToDto(updatedPessoa);
    }

    @GetMapping("/ids")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consulta mais de uma pessoa pelos seus IDs")
    public List<PessoaRecordDto> getPessoasByIds(@RequestParam List<Long> ids) {
        return pessoaService.getPessoasByIds(ids);
    }
}