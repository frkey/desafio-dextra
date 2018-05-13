package br.com.dextra.desafio.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.dextra.desafio.domain.Ingredient;

@RepositoryRestResource()
@Lazy
public interface IngredientRepository extends MongoRepository<Ingredient, String>  {

}
