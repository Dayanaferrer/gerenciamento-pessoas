package com.Attus.pessoas.converters;

import org.springframework.stereotype.Component;

import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.models.EnderecoModel;

@Component
public class EnderecoConverter {

    public EnderecoRecordDto entityToDto(EnderecoModel endereco) {
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

    public EnderecoModel dtoToEntity(EnderecoRecordDto dto) {
        EnderecoModel endereco = new EnderecoModel();
        endereco.setId(dto.id());
        endereco.setLogradouro(dto.logradouro());
        endereco.setCep(dto.cep());
        endereco.setNumero(dto.numero());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setPrincipal(dto.isPrincipal());
        return endereco;
    }
    public void updateEntityFromDto(EnderecoRecordDto dto, EnderecoModel entity) {
        entity.setLogradouro(dto.logradouro());
        entity.setCep(dto.cep());
        entity.setNumero(dto.numero());
        entity.setCidade(dto.cidade());
        entity.setEstado(dto.estado());
        entity.setPrincipal(dto.isPrincipal());
    }
}