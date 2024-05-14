package com.Attus.pessoas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Attus.pessoas.models.EnderecoModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {
	 List<EnderecoModel> findByPrincipal(boolean principal);

}
