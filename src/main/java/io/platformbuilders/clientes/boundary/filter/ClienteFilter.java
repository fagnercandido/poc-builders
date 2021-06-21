package io.platformbuilders.clientes.boundary.filter;

import java.util.Objects;

public class ClienteFilter extends Filter {
	
	private String nome;
	private String cpf;

	public ClienteFilter(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public ClienteFilter() {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClienteFilter)) return false;
		ClienteFilter that = (ClienteFilter) o;
		return Objects.equals(getCpf(), that.getCpf());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCpf());
	}
}
