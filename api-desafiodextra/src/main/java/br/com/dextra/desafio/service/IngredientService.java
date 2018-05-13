package br.com.dextra.desafio.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.converter.IngredientRequestToIngredient;
import br.com.dextra.desafio.dto.converter.IngredientToIngredientResponse;
import br.com.dextra.desafio.dto.request.IngredientRequest;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.repository.IngredientRepository;

@Service
public class IngredientService {

	final IngredientRepository repository;
	
	final IngredientRequestToIngredient requestToIngredient;
	
	final IngredientToIngredientResponse ingredientToResponse;
	
	@Inject
	public IngredientService(final IngredientRepository repository,
			final IngredientRequestToIngredient requestToIngredient,
			final IngredientToIngredientResponse ingredientToResponse) {
		this.repository = repository;
		this.requestToIngredient = requestToIngredient;
		this.ingredientToResponse = ingredientToResponse;

	}
	
	public List<IngredientResponse> fetchAll() {
        return ingredientToResponse.convert(repository.findAll());
    }
	
	public List<Ingredient> fetchAll(final List<String> ids) {
    	List<Ingredient> ingredients = new ArrayList<>();    	
    	repository.findAll(ids).forEach(ingredients::add);
    	
    	return ingredients;
    }
	
	public IngredientResponse save(final IngredientRequest ingredientRequest) {
        final Ingredient savedIngredient = repository.save(requestToIngredient.convert(ingredientRequest));
        return ingredientToResponse.convert(savedIngredient);
    }

    public IngredientResponse fetch(final String id) throws NotFoundException {
        final Ingredient ingredient = repository.findOne(id);
        if (ingredient == null) {
            throw new NotFoundException("Ingredient not found");
        }
        return ingredientToResponse.convert(ingredient);
    }

    public void delete(final String id) throws NotFoundException {
        fetch(id);
        repository.delete(id);
    }

}
