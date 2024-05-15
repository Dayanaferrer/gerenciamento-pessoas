package com.Attus.pessoas.converters;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.models.PessoaModel;

@Component
public class PessoaConverter {
	
	  public PessoaRecordDto entityToDto(PessoaModel pessoa) {
	        EnderecoModel enderecoPrincipal = pessoa.getEnderecos().stream()
	            .filter(EnderecoModel::getPrincipal)
	            .findFirst()
	            .orElse(null);

	        EnderecoRecordDto enderecoPrincipalDto = null;
	        if (enderecoPrincipal != null) {
	            enderecoPrincipalDto = enderecoToDto(enderecoPrincipal);
	        }

	        List<EnderecoRecordDto> enderecosSecundariosDto = pessoa.getEnderecos().stream()
	            .filter(endereco -> !endereco.getPrincipal())
	            .map(this::enderecoToDto)
	            .collect(Collectors.toList());

	        return new PessoaRecordDto(
	            pessoa.getId(),
	            pessoa.getNomeCompleto(),
	            pessoa.getDataNascimento(),
	            enderecoPrincipalDto,
	            enderecosSecundariosDto
	        );
	    }

	    public PessoaModel dtoToEntity(PessoaRecordDto dto) {
	        PessoaModel pessoa = new PessoaModel();
	        pessoa.setId(dto.id());
	        pessoa.setNomeCompleto(dto.nomeCompleto());
	        pessoa.setDataNascimento(dto.dataNascimento());

	        EnderecoModel enderecoPrincipal = dtoToEndereco(dto.enderecoPrincipal());
	        Set<EnderecoModel> enderecosSecundarios = dto.enderecosSecundarios().stream()
	            .map(this::dtoToEndereco)
	            .collect(Collectors.toSet());

	        enderecosSecundarios.add(enderecoPrincipal);

	        pessoa.setEnderecos(enderecosSecundarios);
	        return pessoa;
	    }

	    private EnderecoRecordDto enderecoToDto(EnderecoModel endereco) {
	        return new EnderecoRecordDto(
	            endereco.getId(),
	            endereco.getLogradouro(),
	            endereco.getCep(),
	            endereco.getNumero(),
	            endereco.getCidade(),
	            endereco.getEstado(),
	            endereco.getPrincipal()
	        );
	    }

	    private EnderecoModel dtoToEndereco(EnderecoRecordDto dto) {
	        EnderecoModel endereco = new EnderecoModel();
	        endereco.setId(dto.id());
	        endereco.setLogradouro(dto.logradouro());
	        endereco.setCep(dto.cep());
	        endereco.setNumero(dto.numero());
	        endereco.setCidade(dto.cidade());
	        endereco.setEstado(dto.estado());
	        endereco.setPrincipal(dto.principal());
	        return endereco;
	    }
	}