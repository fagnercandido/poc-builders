package io.platformbuilders.clientes.boundary;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import javax.validation.Valid;

import io.platformbuilders.clientes.boundary.filter.ClienteFilter;
import io.platformbuilders.clientes.boundary.filter.Filter;
import io.platformbuilders.clientes.boundary.filter.ClienteSpecification;
import io.platformbuilders.clientes.entity.Cliente;
import io.platformbuilders.clientes.entity.ClienteNaoEncontradoException;
import io.platformbuilders.clientes.utils.ClienteModel;
import io.platformbuilders.clientes.control.ClienteControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.platformbuilders.clientes.utils.assembler.Assembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"API para o Cliente"})
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteBoundary {

	private static final Logger LOG = LoggerFactory.getLogger(ClienteBoundary.class);

	private final ClienteControl clienteControl;

	private final Assembler assembler;

	private final PagedResourcesAssembler<Cliente> pagedResourcesAssembler;

	public ClienteBoundary(ClienteControl clienteControl, Assembler assembler, PagedResourcesAssembler<Cliente> pagedResourcesAssembler) {
		this.clienteControl = clienteControl;
		this.assembler = assembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}

	@PostMapping
	@ApiOperation(value = "Adicionar um Cliente")
	public ResponseEntity<EntityModel<ClienteModel>> adicionar(@Valid @RequestBody Cliente cliente) {
		LOG.debug("Método Adicionar Inĩ́cio - Cliente {}", cliente);
		clienteControl.adicionar(cliente);
		
		EntityModel<ClienteModel> model = EntityModel.of(new ClienteModel(cliente));
			model.add(
					linkTo(ClienteBoundary.class)
						.slash(model.getContent().getId())
						.withRel("cliente")
					);
		
		LOG.debug("Método Adicionar Fim - Cliente {}", model);
		return new ResponseEntity<>(model, HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "Recuperar clientes com paginação")
	public ResponseEntity<PagedModel<ClienteModel>> recuperarTodosOsClientes(Filter filtro) {
		LOG.debug("Método Recuperar Todos os Clientes Inĩ́cio - Cliente {}", filtro);

		PageRequest pageRequest = PageRequest.of(filtro.getPage(), filtro.getSize(), Sort.Direction.ASC, "nome");
		Page<Cliente> clientes = clienteControl.findAll(pageRequest);
		PagedModel<ClienteModel> clienteModels = pagedResourcesAssembler.toModel(clientes, assembler);

		LOG.debug("Método Recuperar Todos os Clientes Fim - Cliente {}", filtro);
		return new ResponseEntity<>(clienteModels, HttpStatus.OK);
	}

	@GetMapping("/query")
	@ApiOperation(value = "Recuperar cliente por filtros")
	public ResponseEntity<PagedModel<ClienteModel>> recuperarClientePorFiltros(final ClienteFilter filtro) {
		LOG.debug("Método Recuperar Cliente Por Filtros Inĩ́cio - Cliente {}", filtro);

		PageRequest pageRequest = PageRequest.of(filtro.getPage(), filtro.getSize(), Sort.Direction.ASC, "nome");
		Specification<Cliente> specification = new ClienteSpecification(filtro);
		Page<Cliente> clientes = clienteControl.findAll(specification, pageRequest);
		PagedModel<ClienteModel> clienteModels = pagedResourcesAssembler.toModel(clientes, assembler);

		LOG.debug("Método Recuperar Cliente Por Filtros Fim - Cliente {}", clienteModels);
		return new ResponseEntity<>(clienteModels, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Recuperar cliente pelo identificador")
	public ResponseEntity<ClienteModel> recuperarClientePorIdentificador(@PathVariable("id") final Long id) {
		LOG.debug("Método Recuperar Cliente Por Identificador Inĩ́cio - Cliente {}", id);
		return clienteControl.findById(id)
			.map(assembler::toModel)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar um Cliente como um todo")
	public ResponseEntity<EntityModel<ClienteModel>> atualizar(@PathVariable("id") final Long id, @Valid @RequestBody final Cliente cliente) {
		LOG.debug("Método Atualizar Início - Cliente {}", cliente);
		Optional<Cliente> clienteFromBase = clienteControl.findById(id);
		clienteFromBase.orElseThrow(() -> new ClienteNaoEncontradoException(id));
		
		clienteFromBase.get().atualizarCliente(cliente);

		clienteControl.adicionar(clienteFromBase.get());

		EntityModel<ClienteModel> model = EntityModel.of(new ClienteModel(clienteFromBase.get()));
		model.add(
			linkTo(ClienteBoundary.class)
				.slash(model.getContent().getId())
				.withRel("cliente")
		);
		LOG.debug("Método Atualizar Fim - Cliente {}", model);
		return new ResponseEntity<>(model, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	@ApiOperation(value = "Atualizar um Cliente de forma parcial")
	public ResponseEntity<EntityModel<ClienteModel>> atualizarParcial(@PathVariable("id") final Long id, @RequestBody final Cliente cliente) {
		LOG.debug("Método Atualizar Parte Inicio - Cliente {} {}", id, cliente);
		Optional<Cliente> clienteFromBase = clienteControl.findById(id);
		clienteFromBase.orElseThrow(() -> new ClienteNaoEncontradoException(id));

		clienteFromBase.get().atualizarCliente(cliente);

		clienteControl.adicionar(clienteFromBase.get());

		EntityModel<ClienteModel> model = EntityModel.of(new ClienteModel(clienteFromBase.get()));
		model.add(
				linkTo(ClienteBoundary.class)
					.slash(model.getContent().getId())
					.withRel("cliente")
				);

		LOG.debug("Método Atualizar Parte Fim - Parcialmente {}", model);
		return new ResponseEntity<>(model, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remover um Cliente")
	public ResponseEntity<?> remover(@PathVariable("id") final Long id) {
		LOG.debug("Método Remover Inicio - Cliente {}", id);
		return clienteControl
				.findById(id)
				.map(p -> {
						clienteControl.deleteById(id);
						return ResponseEntity.noContent().build();
					})
				.orElse(ResponseEntity.notFound().build());
	}

}