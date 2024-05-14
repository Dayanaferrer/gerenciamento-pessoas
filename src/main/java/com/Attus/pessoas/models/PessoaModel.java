package com.Attus.pessoas.models;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoas")
public class PessoaModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private LocalDate dataNascimento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_principal_id", referencedColumnName = "id")
    private EnderecoModel enderecoPrincipal;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Set<EnderecoModel> enderecos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EnderecoModel getEnderecoPrincipal() {
		return enderecoPrincipal;
	}

	public void setEnderecoPrincipal(EnderecoModel enderecoPrincipal) {
		this.enderecoPrincipal = enderecoPrincipal;
	}

	public Set<EnderecoModel> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<EnderecoModel> enderecos) {
		this.enderecos = enderecos;
	}
    
    

}
