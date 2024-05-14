package com.Attus.pessoas.converters;

import java.util.List;
import java.util.stream.Collectors;

import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.models.PessoaModel;

public class PessoaConverter {

	   public PessoaRecordDto toDto(PessoaModel pessoa) {
	        if (pessoa == null) {
	            return null;
	        }

	        EnderecoRecordDto enderecoPrincipalDto = new EnderecoConverter().toDto(pessoa.getEnderecoPrincipal());

	        List<EnderecoRecordDto> enderecosSecundariosDto = pessoa.getEnderecos().stream()
	            .map(new EnderecoConverter()::toDto)
	            .collect(Collectors.toList());

	        return new PessoaRecordDto(
	            pessoa.getId(),
	            pessoa.getNomeCompleto(),
	            pessoa.getDataNascimento(),
	            enderecoPrincipalDto,
	            enderecosSecundariosDto
	        );
	    }

	    public PessoaModel toModel(PessoaRecordDto dto) {
	        if (dto == null) {
	            return null;
	        }

	        PessoaModel pessoa = new PessoaModel();
	        pessoa.setId(dto.idPessoa());
	        pessoa.setNomeCompleto(dto.nomeCompleto());
	        pessoa.setDataNascimento(dto.dataNascimento());
	        pessoa.setEnderecoPrincipal(new EnderecoConverter().toModel(dto.enderecoPrincipal()));
	        pessoa.setEnderecos(dto.enderecosSecundarios().stream()
	            .map(new EnderecoConverter()::toModel)
	            .collect(Collectors.toSet()));

	        return pessoa;
	    }
	}