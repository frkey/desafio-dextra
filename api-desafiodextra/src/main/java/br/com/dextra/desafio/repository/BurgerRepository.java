package br.com.dextra.desafio.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.dextra.desafio.domain.Burger;

@RepositoryRestResource()
@Lazy
public interface BurgerRepository extends MongoRepository<Burger, String> {

}
