package com.Attus.pessoas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.exceptions.PessoaInvalidDataException;
import com.Attus.pessoas.exceptions.PessoaNotFoundException;
import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.repositories.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaConverter pessoaConverter;
    
    public PessoaModel createPessoa(PessoaModel pessoaModel) {
        validateEnderecoPrincipal(pessoaModel);
        if (pessoaModel.getEnderecos().stream().noneMatch(EnderecoModel::getPrincipal)) {
            throw new PessoaInvalidDataException("Uma pessoa deve ter pelo menos um endereço principal.", "Nenhum endereço foi marcado como principal para a pessoa com o ID " + pessoaModel.getId());
        }
        return pessoaRepository.save(pessoaModel);
    }

    public List<PessoaRecordDto> buscarTodas() {
        return pessoaRepository.findAll().stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());
    }

    public List<PessoaRecordDto> getPessoasByNomeCompleto(String nomeCompleto) {
        List<PessoaRecordDto> pessoas = pessoaRepository.findByNomeCompletoContaining(nomeCompleto).stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());

        if (pessoas.isEmpty()) {
            throw new PessoaNotFoundException("Nenhuma pessoa encontrada com o nome: " + nomeCompleto, nomeCompleto, "Nenhuma pessoa com o nome " + nomeCompleto + " foi encontrada.");
        }

        return pessoas;
    }

    public PessoaModel getPessoaById(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada", id.toString(), "A pessoa com o ID " + id + " não existe."));
    }

    public List<PessoaRecordDto> getPessoasByIds(List<Long> ids) {
        List<PessoaRecordDto> pessoas = pessoaRepository.findByIdIn(ids).stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());

        List<Long> foundIds = pessoas.stream()
            .map(PessoaRecordDto::id)
            .collect(Collectors.toList());

        List<Long> notFoundIds = ids.stream()
            .filter(id -> !foundIds.contains(id))
            .collect(Collectors.toList());

        if (!notFoundIds.isEmpty()) {
            throw new PessoaNotFoundException("Pessoas com os seguintes IDs não foram encontradas: " + notFoundIds, "", "Pessoas com os IDs " + notFoundIds + " não foram encontradas.");
        }

        return pessoas;
    }
    public PessoaModel updatePessoa(PessoaModel pessoaModel) {
        if (!pessoaRepository.existsById(pessoaModel.getId())) {
        	throw new PessoaNotFoundException("Pessoa não encontrada", pessoaModel.getId().toString(), "A pessoa com o ID " + pessoaModel.getId() + " não existe.");
        }
        validateEnderecoPrincipal(pessoaModel);
        return pessoaRepository.save(pessoaModel);
    }

    private void validateEnderecoPrincipal(PessoaModel pessoaModel) {
        long count = pessoaModel.getEnderecos().stream().filter(EnderecoModel::getPrincipal).count();
        if (count > 1) {
        	throw new PessoaInvalidDataException("Uma pessoa não pode ter mais de um endereço principal.", "Mais de um endereço foi marcado como principal para a pessoa com o ID " + pessoaModel.getId());
        }
    }
}