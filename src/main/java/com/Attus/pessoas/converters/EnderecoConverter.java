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
            endereco.getPrincipal() != null ? endereco.getPrincipal() : false
        );
    }

    public EnderecoModel dtoToEntity(EnderecoRecordDto dto) {
        EnderecoModel endereco = new EnderecoModel();
        updateEntityFromDto(dto, endereco);
        return endereco;
    }
    
    public void updateEntityFromDto(EnderecoRecordDto dto, EnderecoModel entity) {
        if(dto.logradouro() != null) {
            entity.setLogradouro(dto.logradouro());
        }
        if(dto.cep() != null) {
            entity.setCep(dto.cep());
        }
        if(dto.numero() != null) {
            entity.setNumero(dto.numero());
        }
        if(dto.cidade() != null) {
            entity.setCidade(dto.cidade());
        }
        if(dto.estado() != null) {
            entity.setEstado(dto.estado());
        }
        entity.setPrincipal(dto.principal());
    }
}