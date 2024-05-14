package com.Attus.pessoas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Attus.pessoas.converters.EnderecoConverter;
import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.exceptions.PessoaNotFoundException;
import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.repositories.EnderecoRepository;
import com.Attus.pessoas.repositories.PessoaRepository;

import jakarta.transaction.Transactional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaConverter pessoaConverter;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private EnderecoConverter enderecoConverter;
    
       
    public PessoaModel createPessoa(PessoaModel pessoaModel) {
        long count = pessoaModel.getEnderecos().stream().filter(EnderecoModel::getPrincipal).count();
        if (count > 1) {
            throw new IllegalArgumentException("Uma pessoa não pode ter mais de um endereço principal.");
        }
        PessoaModel savedPessoa = pessoaRepository.save(pessoaModel);
        for (EnderecoModel endereco : pessoaModel.getEnderecos()) {
            endereco.setPessoa(savedPessoa);
            enderecoRepository.save(endereco);
        }
        return savedPessoa;
    }
    
    public List<PessoaRecordDto> buscarTodas() {
        List<PessoaModel> todasPessoas = pessoaRepository.findAll();
        return todasPessoas.stream()
            .map(pessoaConverter::entityToDto)
            .collect(Collectors.toList());
    }
    
    public PessoaModel getPessoaById(Long id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    public PessoaModel updatePessoa(PessoaModel pessoaModel) {
        if (pessoaRepository.existsById(pessoaModel.getId())) {
            return pessoaRepository.save(pessoaModel);
        } else {
            throw new IllegalArgumentException("A pessoa com o ID especificado não existe.");
        }
    }


}