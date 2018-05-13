package br.com.dextra.desafio.promotion;

import java.math.BigDecimal;
import java.util.List;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;

public class SimpleBurgerPrice extends BurgerPrice {

	@Override
	public BigDecimal getPrice(final Burger burger) {
		/* Preço total sem descontos */
		BigDecimal price = BigDecimal.ZERO;
		List<Ingredient> ingredients = burger.getIngredients();		
		
		for (Ingredient ingredient: ingredients){
			price = price.add(ingredient.getPrice());
		}
		
		return price;
	}

}
