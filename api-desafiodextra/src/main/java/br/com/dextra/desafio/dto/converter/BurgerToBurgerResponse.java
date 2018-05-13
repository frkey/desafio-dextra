package br.com.dextra.desafio.dto.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.dto.response.BurgerResponse;

@Component
public class BurgerToBurgerResponse implements GenericConverter<Burger, BurgerResponse> {

	final IngredientToIngredientResponse ingredientToResponse;

	@Inject
	public BurgerToBurgerResponse(final IngredientToIngredientResponse ingredientToResponse) {
		this.ingredientToResponse = ingredientToResponse;
	}

	@Override
	public BurgerResponse apply(Burger input) {
		return BurgerResponse.builder()
				.id(input.getId())
				.name(input.getName())
				.ingredients(ingredientToResponse.convert(input.getIngredients()))
				.build();
	}

}
