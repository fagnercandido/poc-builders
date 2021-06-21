package io.platformbuilders.clientes.utils;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.platformbuilders.clientes.entity.Cliente;


@Relation(collectionRelation = "clientes", itemRelation = "cliente")
@JsonInclude(Include.NON_NULL)
public class ClienteModel extends RepresentationModel<ClienteModel> {

	
	private Long id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private Integer idade;

	public ClienteModel(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.dataNascimento = cliente.getIdade();
		this.idade = cliente.obterIdade();
	}

	public ClienteModel() {
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClienteModel)) return false;
		if (!super.equals(o)) return false;
		ClienteModel that = (ClienteModel) o;
		return getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getId());
	}
}
