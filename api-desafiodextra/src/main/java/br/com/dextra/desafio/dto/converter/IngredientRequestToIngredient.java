package br.com.dextra.desafio.dto.converter;

import org.springframework.stereotype.Component;

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.IngredientRequest;

@Component
public class IngredientRequestToIngredient implements GenericConverter<IngredientRequest, Ingredient> {

	@Override
	public Ingredient apply(IngredientRequest input) {
		return Ingredient.builder()
                .name(input.getName())
                .price(input.getPrice())
                .build();
	}

}
