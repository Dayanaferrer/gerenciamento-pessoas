package com.Attus.pessoas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Attus.pessoas.models.PessoaModel;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
	List<PessoaModel> findByNomeCompleto(String nomeCompleto);
    Optional<PessoaModel> findById(Long id);

}
