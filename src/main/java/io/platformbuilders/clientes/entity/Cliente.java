package io.platformbuilders.clientes.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;


@Entity
@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(name = "cpf", columnNames = {"cpf"})})
public class Cliente extends RepresentationModel<Cliente> implements Serializable {

	private static final long serialVersionUID = 842010666755925711L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "{nome.obrigatorio}")
	@NotNull(message = "{nome.obrigatorio}")
	@Size(min = 5, max = 100, message = "{nome.tamanho}")
	private String nome;

	@NotEmpty(message = "{cpf.obrigatorio}")
	@NotNull(message = "{cpf.obrigatorio}")
	@CPF(message = "{cpf.invalido}")
	private String cpf;

	@NotNull(message = "{idade.obrigatorio}")
	private LocalDate idade;

	@Transient
	public Integer obterIdade() {
		return Period.between(this.idade, LocalDate.now()).getYears();
	}

	public void atualizarCliente(Cliente cliente) {
		Optional<Cliente> clienteOptional = Optional.of(cliente);
		clienteOptional.map(Cliente::getNome).map(p -> {
			this.nome = cliente.nome;
			return this;
		});
		clienteOptional.map(Cliente::getCpf).map(p -> {
			this.cpf = cliente.cpf;
			return this;
		});
		clienteOptional.map(Cliente::getIdade).map(p -> {
			this.idade = cliente.idade;
			return this;
		});
	}

	public Cliente(Long id, String nome, String cpf, LocalDate idade) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.idade = idade;
	}

	public Cliente() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getIdade() {
		return idade;
	}

	public void setIdade(LocalDate dataNascimento) {
		this.idade = dataNascimento;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Cliente)) return false;
		if (!super.equals(o)) return false;
		Cliente cliente = (Cliente) o;
		return getId().equals(cliente.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getId());
	}

}