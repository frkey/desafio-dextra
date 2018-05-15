package br.com.dextra.desafio.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;

public class SimpleBurgerPrice extends BurgerPrice {

	@Override
	public List<BigDecimal> getPrice(final Burger burger) {
		/* Preço total sem descontos */
		BigDecimal price = BigDecimal.ZERO;
		List<Ingredient> ingredients = burger.getIngredients();		
		
		for (Ingredient ingredient: ingredients){
			price = price.add(ingredient.getPrice());
		}
		
		List<BigDecimal> prices = null;
		if (successor != null) {
			prices = successor.getPrice(burger);
			
			if (prices != null) {
				prices.add(price);
				return prices;
			}
		}
		prices = new ArrayList<>();
		prices.add(price);
		return prices;
	}

}
