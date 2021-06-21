package io.platformbuilders.clientes.boundary.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import io.platformbuilders.clientes.entity.Cliente;

public class ClienteSpecification implements Specification<Cliente> {
	
	private static final long serialVersionUID = -3712714154813166058L;

	private ClienteFilter filter;

    public ClienteSpecification(ClienteFilter filter) {
        super();
        this.filter = filter;
    }
	
	@Override
	public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		Predicate predicate = criteriaBuilder.disjunction();

		if (filter.getNome() != null) {
			predicate.getExpressions()
				.add(criteriaBuilder.like(root.get("nome"), filter.getNome()));
		}
		
		if (filter.getCpf() != null) {
			predicate.getExpressions()
			.add(criteriaBuilder.equal(root.get("cpf"), filter.getCpf()));
		}
		
		return predicate;
	}

}
