package br.com.dextra.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.IngredientRequest;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.repository.IngredientRepository;

@Service
public class IngredientService {

	final IngredientRepository repository;

	@Inject
	public IngredientService(final IngredientRepository repository) {
		this.repository = repository;
	}

	public List<IngredientResponse> fetchAll() {
		return repository.findAll().stream().map(c -> buildResponse(c)).collect(Collectors.toList());
	}

	public List<Ingredient> fetchAll(final List<String> ids) {
		List<Ingredient> ingredients = new ArrayList<>();
		repository.findAll(ids).forEach(ingredients::add);

		return ingredients;
	}

	public IngredientResponse save(final IngredientRequest ingredientRequest) {
		final Ingredient savedIngredient = repository.save(buildFromRequest(ingredientRequest));
		return buildResponse(savedIngredient);
	}

	public IngredientResponse fetch(final String id) throws NotFoundException {
		final Ingredient ingredient = repository.findOne(id);
		if (ingredient == null) {
			throw new NotFoundException("Ingredient not found");
		}
		return buildResponse(ingredient);
	}

	public void delete(final String id) throws NotFoundException {
		fetch(id);
		repository.delete(id);
	}

	public Ingredient buildFromRequest(IngredientRequest ingredientRequest) {
		return Ingredient.builder().name(ingredientRequest.getName()).price(ingredientRequest.getPrice()).build();
	}

	public IngredientResponse buildResponse(Ingredient ingredient) {
		return IngredientResponse.builder().id(ingredient.getId()).name(ingredient.getName())
				.price(ingredient.getPrice()).build();
	}

}
