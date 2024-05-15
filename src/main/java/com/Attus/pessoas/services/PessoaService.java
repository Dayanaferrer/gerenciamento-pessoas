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
        return pessoaRepository.save(pessoaModel);
    }

    public List<PessoaRecordDto> buscarTodas() {
        return pessoaRepository.findAll().stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());
    }

    public List<PessoaRecordDto> getPessoasByNomeCompleto(String nomeCompleto) {
        return pessoaRepository.findByNomeCompletoContaining(nomeCompleto).stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());
    }

    public Optional<PessoaModel> getPessoaById(Long id) {
        return pessoaRepository.findById(id);
    }

    public List<PessoaRecordDto> getPessoasByIds(List<Long> ids) {
        return pessoaRepository.findByIdIn(ids).stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());
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