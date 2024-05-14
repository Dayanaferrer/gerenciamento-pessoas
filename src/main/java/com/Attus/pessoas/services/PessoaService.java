package com.Attus.pessoas.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.PessoaRecordDto;
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

    @Transactional
    public PessoaModel createPessoa(PessoaModel pessoa) {
        // Salva a pessoa no banco de dados para gerar um ID
        PessoaModel savedPessoa = pessoaRepository.save(pessoa);

        // Associa cada endereço à pessoa e define o endereço principal
        for (EnderecoModel endereco : pessoa.getEnderecos()) {
            endereco.setPessoa(savedPessoa); // Associa o endereço à pessoa
            if (endereco.getPrincipal()) {
                savedPessoa.setEnderecoPrincipal(endereco); // Define o endereço principal da pessoa
            }
        }

        // Salva novamente a pessoa para garantir que o ID gerado esteja associado aos endereços
        savedPessoa = pessoaRepository.save(savedPessoa);

        // Salva todos os endereços associados à pessoa no banco de dados
        for (EnderecoModel endereco : pessoa.getEnderecos()) {
            enderecoRepository.save(endereco);
        }

        return savedPessoa;
    }
}