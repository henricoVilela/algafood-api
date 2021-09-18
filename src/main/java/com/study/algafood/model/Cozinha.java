package com.study.algafood.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.study.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;


/*
 * A anotação @Data empacota as tres anotações abaixo:
 * @Getter
 * @Setter
 * @EqualsAndHashCode
*/

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //para dizer que so gera o equals para os atributos marcados
@Entity
@Table(name="Cozinha")
public class Cozinha {
	
	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name="nome", length = 50)
	private String nome;
	
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();

}
