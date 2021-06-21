package io.platformbuilders.clientes.control;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.platformbuilders.clientes.entity.Cliente;
import io.platformbuilders.clientes.entity.ClienteRepository;

@Service
public class ClienteControl {
	
	private ClienteRepository repository;

	public ClienteControl(ClienteRepository repository) {
		this.repository = repository;
	}

	public Cliente adicionar(Cliente cliente) {
		return repository.save(cliente);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Optional<Cliente> findById(Long id) {
		return repository.findById(id);
	}

	public Page<Cliente> findAll(Specification<Cliente> spec, Pageable pageable) {
		return repository.findAll(spec, pageable);
	}


	public Page<Cliente> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}


	public Optional<Cliente> findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

}
