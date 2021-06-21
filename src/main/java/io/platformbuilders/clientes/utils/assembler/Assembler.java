package io.platformbuilders.clientes.utils.assembler;

import io.platformbuilders.clientes.boundary.ClienteBoundary;
import io.platformbuilders.clientes.entity.Cliente;
import io.platformbuilders.clientes.utils.ClienteModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class Assembler extends RepresentationModelAssemblerSupport<Cliente, ClienteModel> {

	public Assembler() {
		super(ClienteBoundary.class, ClienteModel.class);
	}
	
	@Override
	public ClienteModel toModel(Cliente cliente) {
		ClienteModel model = instantiateModel(cliente);
		model.add(linkTo(
				methodOn(ClienteBoundary.class)
				.recuperarClientePorIdentificador(cliente.getId()))
				.withRel("cliente"));
		model.setId(cliente.getId());
		model.setNome(cliente.getNome());
		model.setCpf(cliente.getCpf());
		model.setDataNascimento(cliente.getIdade());
		model.setIdade(cliente.obterIdade());
		return model;
	}

}
