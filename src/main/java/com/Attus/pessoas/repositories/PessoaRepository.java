package com.Attus.pessoas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Attus.pessoas.models.PessoaModel;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
	List<PessoaModel> findByNomeCompleto(String nomeCompleto);
    Optional<PessoaModel> findById(Long id);
    
    @Query("SELECT p FROM PessoaModel p JOIN p.enderecos e WHERE e.principal = :principal")
    List<PessoaModel> findByEnderecoPrincipal(boolean principal);

}
