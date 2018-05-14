package br.com.dextra.desafio.services;

import java.math.BigDecimal;

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.IngredientRequest;
import br.com.dextra.desafio.dto.response.IngredientResponse;

public class IngredientsTestFactory {

	public static Ingredient defaultIngredient() {
		return Ingredient.builder()
                .id("1")
                .name("Ovo")
                .price(new BigDecimal("0.80"))
                .build();
    }

    public static IngredientRequest defaultIngredientRequest() {
    	Ingredient ingredient = defaultIngredient();
        return IngredientRequest.builder()
                .name(ingredient.getName())
                .price(ingredient.getPrice())
                .build();
    }

    public static IngredientResponse defaultIngredientResponse() {
    	Ingredient ingredient = defaultIngredient();
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price(ingredient.getPrice())
                .build();
    }
}
