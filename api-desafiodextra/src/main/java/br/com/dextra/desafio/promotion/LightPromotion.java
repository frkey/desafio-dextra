package br.com.dextra.desafio.promotion;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;

public class LightPromotion extends BurgerPrice {

	@Override
	public BigDecimal getPrice(final Burger burger) {
		/* Se o lanche tem alface e não tem bacon, ganha 10% de desconto. */
		BigDecimal price = BigDecimal.ZERO;
		List<Ingredient> ingredients = burger.getIngredients();
		boolean hasBacon = false;
		boolean hasLettuce = false;		
		
		for (Ingredient ingredient: ingredients){
			price = price.add(ingredient.getPrice());
			if (StringUtils.containsIgnoreCase(ingredient.getName(), "alface")) {
				hasLettuce = true;				
			} else if (StringUtils.containsIgnoreCase(ingredient.getName(), "bacon")) {
				hasBacon = true;
				break;
			}
		}
		
		if (!hasBacon && hasLettuce)
			price = price.multiply(new BigDecimal("0.9"));
		
		return price;
	}

}
