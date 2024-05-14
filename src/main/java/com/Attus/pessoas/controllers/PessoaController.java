package com.Attus.pessoas.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.exceptions.PessoaNotFoundException;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.repositories.EnderecoRepository;
import com.Attus.pessoas.repositories.PessoaRepository;
import com.Attus.pessoas.services.PessoaService;
import com.Attus.pessoas.models.EnderecoModel;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
    private PessoaService pessoaService;
	
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private PessoaConverter pessoaConverter;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @PostMapping
    public ResponseEntity<PessoaRecordDto> createPessoa(@RequestBody PessoaRecordDto pessoaRecordDto) {
        try {
            PessoaModel pessoaModel = pessoaConverter.dtoToEntity(pessoaRecordDto);
            PessoaModel createdPessoa = pessoaService.createPessoa(pessoaModel);
            return new ResponseEntity<>(pessoaConverter.entityToDto(createdPessoa), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<PessoaRecordDto>> buscarTodas() {
        try {
            List<PessoaRecordDto> todasPessoas = pessoaService.buscarTodas();
            return ResponseEntity.ok(todasPessoas);
        } catch (Exception e) {
            System.out.println("Erro ao buscar todas as pessoas: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PessoaRecordDto> getPessoaById(@PathVariable Long id) {
        try {
            PessoaModel pessoa = pessoaService.getPessoaById(id);
            if (pessoa == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            PessoaRecordDto pessoaDto = pessoaConverter.entityToDto(pessoa);
            return new ResponseEntity<>(pessoaDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PessoaRecordDto> updatePessoa(@PathVariable Long id, @RequestBody PessoaRecordDto pessoaDto) {
        try {
            PessoaModel existingPessoa = pessoaService.getPessoaById(id);
            if (existingPessoa == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            PessoaModel pessoaToUpdate = pessoaConverter.dtoToEntity(pessoaDto);
            pessoaToUpdate.setId(id);
            PessoaModel updatedPessoa = pessoaService.updatePessoa(pessoaToUpdate);
            PessoaRecordDto updatedPessoaDto = pessoaConverter.entityToDto(updatedPessoa);
            return new ResponseEntity<>(updatedPessoaDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}