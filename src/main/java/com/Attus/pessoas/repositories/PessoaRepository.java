package com.Attus.pessoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Attus.pessoas.models.PessoaModel;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {

}
