package br.com.dextra.desafio.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.dextra.desafio.domain.Order;

@RepositoryRestResource()
@Lazy
public interface OrderRepository extends MongoRepository<Order, String> {

}
