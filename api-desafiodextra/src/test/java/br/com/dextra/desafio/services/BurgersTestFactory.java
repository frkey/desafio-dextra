package br.com.dextra.desafio.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.BurgerRequest;
import br.com.dextra.desafio.dto.response.BurgerResponse;
import br.com.dextra.desafio.dto.response.IngredientResponse;

public class BurgersTestFactory {

	public static Burger defaultBurger() {
		List<Ingredient> ingredients = new ArrayList<>();
    	
    	ingredients.add(Ingredient.builder().id("1").name("Bacon").price(new BigDecimal("2")).build());
    	ingredients.add(Ingredient.builder().id("2").name("Hambúrguer de carne").price(new BigDecimal("3")).build());
    	ingredients.add(Ingredient.builder().id("3").name("Queijo").price(new BigDecimal("1.50")).build());
    	
        return Burger.builder()
                .id("2")
                .name("X-Bacon")
                .ingredients(ingredients)
                .build();
    }

    public static BurgerRequest defaultBurgerRequest() {
        Burger burger = defaultBurger();
        
        List<String> ids = new ArrayList<>();
        
        for(Ingredient ingredient : burger.getIngredients())
        	ids.add(ingredient.getId());
        
        return BurgerRequest.builder()
                .name(burger.getName())
                .ingredientsId(ids)
                .build();
    }

    public static BurgerResponse defaultBurgerResponse() {
        Burger burger = defaultBurger();
        
        List<IngredientResponse> ingredients = new ArrayList<>();
        
        for(Ingredient ingredient : burger.getIngredients())
        	ingredients.add(IngredientResponse.builder()
        			.id(ingredient.getId())
        			.name(ingredient.getName())
        			.price(ingredient.getPrice())
        			.build());
        
        return BurgerResponse.builder()
                .id(burger.getId())
                .name(burger.getName())
                .ingredients(ingredients)
                .build();
    }
}
