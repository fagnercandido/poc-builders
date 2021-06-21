package io.platformbuilders.clientes.entity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    Optional<Cliente> findByCpf(String cpf);

}
