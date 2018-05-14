package br.com.dextra.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.domain.Order;
import br.com.dextra.desafio.dto.request.OrderRequest;
import br.com.dextra.desafio.dto.response.BurgerResponse;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.dto.response.OrderResponse;
import br.com.dextra.desafio.repository.OrderRepository;

@Service
public class OrderService {

	final OrderRepository repository;
	
	@Inject
	public OrderService(final OrderRepository repository) {
		this.repository = repository;
	}

	public List<OrderResponse> fetchAll() {
        return repository.findAll().stream().map(c -> buildResponse(c)).collect(Collectors.toList());
    }

	private Order buildFromRequest(OrderRequest orderRequest) {
        return null;
    }

    private OrderResponse buildResponse(Order order) {
    	List<BurgerResponse> burgers = new ArrayList<>();
    	List<IngredientResponse> ingredients;
    	
    	for (Burger burger : order.getBurgers()) {
    		ingredients = new ArrayList<>();
    		for (Ingredient ingredient : burger.getIngredients())
    			ingredients.add(IngredientResponse.builder()
    						.id(ingredient.getId())
    						.name(ingredient.getName())
    						.price(ingredient.getPrice())
    						.build());
    		
    		burgers.add(BurgerResponse.builder()
					.id(burger.getId())
					.name(burger.getName())
					.ingredients(ingredients)
					.build());
    	}	
    	
    	return OrderResponse.builder()
				.id(order.getId())
				.creationDate(order.getCreationDate())
				.burgers(burgers)
				.build();
    }
}
