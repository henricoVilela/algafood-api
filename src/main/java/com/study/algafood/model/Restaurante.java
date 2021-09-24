package com.study.algafood.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.study.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;


/*@ValorZeroIncluiDescricao(valorField = "taxaFrete", 
	descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")*/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull - valida se nao pode ser nulo
	//@NotEmpty - valida se nao poder ser string vazia
	@NotBlank
	private String nome;
	
	//@DecimalMin("0")
	@NotNull
	@PositiveOrZero
	//@Multiplo(numero = 5)
	@Column(name="taxa_frete")
	private BigDecimal taxaFrete;
	
	//@JsonIgnore
	//@JsonIgnoreProperties({"hibernateLazyInitializer"}) //descomentar caso queria que cozinha apareca na representacao
	//@JsonIgnoreProperties(value = {"nome"}, allowGetters = true)
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name="cozinha_id", nullable=false)
	private Cozinha cozinha;
	
	//@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	//@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;  
	
	//@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;  
	
	//@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
			   joinColumns = @JoinColumn(name="restaurante_id"),
			   inverseJoinColumns = @JoinColumn(name="formaPagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	//@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
}
