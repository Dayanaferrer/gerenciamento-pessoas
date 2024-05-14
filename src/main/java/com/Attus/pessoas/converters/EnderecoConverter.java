package com.Attus.pessoas.converters;

import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.models.EnderecoModel;

public class EnderecoConverter {
 
    public EnderecoRecordDto toDto(EnderecoModel endereco) {
        if (endereco == null) {
            return null;
        }

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

    public EnderecoModel toModel(EnderecoRecordDto dto) {
        if (dto == null) {
            return null;
        }

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
}