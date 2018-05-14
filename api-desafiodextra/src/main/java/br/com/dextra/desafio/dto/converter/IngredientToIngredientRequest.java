package br.com.dextra.desafio.dto.converter;

import org.springframework.stereotype.Component;

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.IngredientRequest;

@Component
public class IngredientToIngredientRequest implements GenericConverter<Ingredient, IngredientRequest> {

	@Override
	public IngredientRequest apply(Ingredient input) {
		return IngredientRequest.builder()
					.name(input.getName())
					.price(input.getPrice())
					.build();
	}

}
