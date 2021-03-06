package br.com.dextra.desafio.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;

public class LotOfPromotion extends BurgerPrice {
	
	private final String ingredientName;
	
	public LotOfPromotion(final String ingredientName) {
		this.ingredientName = ingredientName;
	}

	@Override
	public List<BigDecimal> getPrice(final Burger burger) {
		/* A cada 3 porções do ingrediente o cliente só paga 2. Se o lanche tiver 6 porções, o cliente pagará 4. Assim por diante... */
		BigDecimal price = BigDecimal.ZERO;
		final List<Ingredient> ingredients = burger.getIngredients();		
		List<Ingredient> discountIngredients = new ArrayList<>();
		
		
		for (Ingredient ingredient: ingredients){
			if (StringUtils.containsIgnoreCase(ingredient.getName(), ingredientName))
				discountIngredients.add(ingredient);
			else
				price = price.add(ingredient.getPrice());
		}
		
		final int quantity = discountIngredients.size();
		if (quantity % 3 != 0) {
			final int discountUnits = (quantity / 3) * 2;
			
			for(int i = 0; i < discountUnits; i++)
				price = price.add(discountIngredients.get(i).getPrice());
		} else
			for (Ingredient ingredient: discountIngredients)
				price = price.add(ingredient.getPrice());
		
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
