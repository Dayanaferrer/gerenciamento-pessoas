package com.Attus.pessoas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Attus.pessoas.converters.EnderecoConverter;
import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.exceptions.PessoaInvalidDataException;
import com.Attus.pessoas.exceptions.PessoaNotFoundException;
import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.repositories.EnderecoRepository;
import com.Attus.pessoas.repositories.PessoaRepository;

@Service
public class EnderecoService {
	
	 @Autowired
	 private EnderecoRepository enderecoRepository;	
	 
	 @Autowired
	 private PessoaRepository pessoaRepository;

	 @Autowired
	 private EnderecoConverter enderecoConverter;
	 
	   public List<EnderecoRecordDto> criarEnderecos(List<EnderecoRecordDto> enderecoDtos, Long pessoaId) {
	        long countPrincipal = enderecoDtos.stream().filter(EnderecoRecordDto::isPrincipal).count();
	        if (countPrincipal > 1) {
	        	throw new PessoaInvalidDataException("Apenas um endereço pode ser marcado como principal", "Mais de um endereço foi marcado como principal");
            }

	        List<EnderecoModel> enderecos = new ArrayList<>();
	        for (EnderecoRecordDto enderecoDto : enderecoDtos) {
	            EnderecoModel endereco = enderecoConverter.dtoToEntity(enderecoDto);
	            endereco.setPessoa(pessoaRepository.findById(pessoaId)
	                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada")));
	            enderecos.add(endereco);
	        }

	        enderecos = enderecoRepository.saveAll(enderecos);

	        return enderecos.stream().map(enderecoConverter::entityToDto).collect(Collectors.toList());
	    }
	
	 
	   public List<EnderecoRecordDto> editarEndereco(List<EnderecoRecordDto> enderecoDtos, Long pessoaId) {
		    long countPrincipal = enderecoDtos.stream().filter(EnderecoRecordDto::isPrincipal).count();
		    if (countPrincipal > 1) {
		    	throw new PessoaInvalidDataException("Apenas um endereço pode ser marcado como principal", "Mais de um endereço foi marcado como principal");
            }
		    List<EnderecoModel> enderecos = new ArrayList<>();
		    for (EnderecoRecordDto enderecoDto : enderecoDtos) {
		        EnderecoModel endereco = enderecoRepository.findById(enderecoDto.id())
		                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));
		        enderecoConverter.updateEntityFromDto(enderecoDto, endereco);
		        enderecos.add(endereco);
		    }

		    enderecos = enderecoRepository.saveAll(enderecos);

		    return enderecos.stream().map(enderecoConverter::entityToDto).collect(Collectors.toList());
		}

	   public List<EnderecoRecordDto> consultarTodosEnderecos() {
		    List<EnderecoModel> enderecos = enderecoRepository.findAll();
		    return enderecos.stream()
		            .map(enderecoConverter::entityToDto)
		            .collect(Collectors.toList());
		}

	   public EnderecoRecordDto consultarEnderecoPrincipal(Long pessoaId) {
		    PessoaModel pessoa = pessoaRepository.findById(pessoaId)
		    		.orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada", pessoaId.toString(), "A pessoa com o ID " + pessoaId + " não foi encontrada"));

		    EnderecoModel enderecoPrincipal = enderecoRepository.findByPessoaAndPrincipal(pessoa, true)
		            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço principal não encontrado"));

		    return enderecoConverter.entityToDto(enderecoPrincipal);
		}
	}