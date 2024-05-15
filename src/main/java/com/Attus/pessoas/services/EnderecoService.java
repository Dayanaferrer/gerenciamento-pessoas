package com.Attus.pessoas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Attus.pessoas.converters.EnderecoConverter;
import com.Attus.pessoas.dtos.EnderecoRecordDto;
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
	 
	    public EnderecoRecordDto saveEndereco(EnderecoRecordDto enderecoDto) {
	        EnderecoModel enderecoEntity = enderecoConverter.dtoToEntity(enderecoDto);
	        enderecoEntity = enderecoRepository.save(enderecoEntity);
	        return enderecoConverter.entityToDto(enderecoEntity);
	    }
	   
	   public List<EnderecoRecordDto> consultarTodosEnderecos() {
		    List<EnderecoModel> enderecos = enderecoRepository.findAll();
		    return enderecos.stream()
		            .map(enderecoConverter::entityToDto)
		            .collect(Collectors.toList());
		}
	   
	   public EnderecoRecordDto consultarEnderecoPorId(Long id) {
		    EnderecoModel endereco = enderecoRepository.findById(id)
		            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));
		    return enderecoConverter.entityToDto(endereco);
		}

	   public EnderecoRecordDto consultarEnderecoPrincipal(Long pessoaId) {
		    PessoaModel pessoa = pessoaRepository.findById(pessoaId)
		    		.orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada", pessoaId.toString(), "A pessoa com o ID " + pessoaId + " não foi encontrada"));

		    EnderecoModel enderecoPrincipal = enderecoRepository.findByPessoaAndPrincipal(pessoa, true)
		            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço principal não encontrado"));

		    return enderecoConverter.entityToDto(enderecoPrincipal);
		}
	   

}
  
	   
	