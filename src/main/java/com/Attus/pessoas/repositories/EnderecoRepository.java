package com.Attus.pessoas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.models.PessoaModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {
	 Optional<EnderecoModel> findByPessoaAndPrincipal(PessoaModel pessoa, boolean principal);
	 List<EnderecoModel> findByPrincipal(boolean principal);
	 List<EnderecoModel> findByPessoa(PessoaModel pessoa);
	 Optional<EnderecoModel> findByIdAndPessoaId(Long id, Long pessoaId);

}
