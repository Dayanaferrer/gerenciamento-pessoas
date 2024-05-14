package com.Attus.pessoas.controllers;

import java.util.List;
import java.util.Optional;

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
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaConverter pessoaConverter;
    
    
    @Operation(summary = "Cria uma nova pessoa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<PessoaRecordDto> createPessoa(@RequestBody PessoaRecordDto pessoaRecordDto) {
        try {
            PessoaModel pessoaModel = pessoaConverter.dtoToEntity(pessoaRecordDto);
            PessoaModel createdPessoa = pessoaService.createPessoa(pessoaModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(pessoaConverter.entityToDto(createdPessoa));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar pessoa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            System.out.println("Erro ao criar pessoa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Operation(summary = "Consultar todas as pessoas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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

    @Operation(summary = "Consultar uma pessoa por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaRecordDto> getPessoaById(@PathVariable Long id) {
        try {
            Optional<PessoaModel> optionalPessoa = pessoaService.getPessoaById(id);
            if (!optionalPessoa.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            PessoaModel pessoa = optionalPessoa.get();
            PessoaRecordDto pessoaDto = pessoaConverter.entityToDto(pessoa);
            return ResponseEntity.ok(pessoaDto);
        } catch (Exception e) {
            System.out.println("Erro ao buscar pessoa por id: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Editar uma pessoa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PessoaRecordDto> updatePessoa(@PathVariable Long id, @RequestBody PessoaRecordDto pessoaDto) {
        try {
            Optional<PessoaModel> optionalPessoa = pessoaService.getPessoaById(id);
            if (!optionalPessoa.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            PessoaModel pessoaToUpdate = pessoaConverter.dtoToEntity(pessoaDto);
            pessoaToUpdate.setId(id);
            PessoaModel updatedPessoa = pessoaService.updatePessoa(pessoaToUpdate);
            PessoaRecordDto updatedPessoaDto = pessoaConverter.entityToDto(updatedPessoa);
            return ResponseEntity.ok(updatedPessoaDto);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar pessoa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}