package io.platformbuilders.clientes.entity;

public class ClienteNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8611917612543915911L;

	public ClienteNaoEncontradoException(final Long id) {
		super("O cliente não foi encontrado. O identificador " + id + " é desconhecido.");
	}
	
}
