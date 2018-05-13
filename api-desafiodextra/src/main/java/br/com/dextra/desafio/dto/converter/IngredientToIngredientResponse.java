package br.com.dextra.desafio.dto.converter;

import org.springframework.stereotype.Component;

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.response.IngredientResponse;

@Component
public class IngredientToIngredientResponse implements GenericConverter<Ingredient, IngredientResponse> {

	@Override
	public IngredientResponse apply(Ingredient input) {
		return IngredientResponse.builder()
				.id(input.getId())
				.name(input.getName())
				.price(input.getPrice())
				.build();
	}

}
