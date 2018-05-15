package br.com.dextra.desafio.service;

import java.util.ArrayList;
import java.util.Date;
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
import br.com.dextra.desafio.exception.NotFoundException;
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

	public OrderResponse save(final OrderRequest orderRequest) {
		Order order = buildFromRequest(orderRequest);
		order.setCreationDate(new Date());
		
        Order savedOrder = repository.save(order);
        return buildResponse(savedOrder);
    }

	public OrderResponse fetch(String id) throws NotFoundException {
        Order order = repository.findOne(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return buildResponse(order);
    }

    public OrderResponse update(OrderRequest orderRequest, String id) throws NotFoundException {
        OrderResponse orderResponse = fetch(id);

        Order order = buildFromRequest(orderRequest);
        order.setId(orderResponse.getId());

        return buildResponse(repository.save(order));
    }

    public void delete(String id) throws NotFoundException {
        fetch(id);
        repository.delete(id);
    }

	private Order buildFromRequest(OrderRequest orderRequest) {
		List<Burger> burgers = null;
		List<Ingredient> ingredients;
		List<IngredientResponse> ingredientsResponse = null;
		final List<BurgerResponse> burgersResponse = orderRequest.getBurgers();
		
		if (burgersResponse != null) {
			burgers = new ArrayList<>();
			for (BurgerResponse burgerResponse : burgersResponse) {
				ingredientsResponse = burgerResponse.getIngredients();
				ingredients = null;
				
				if (ingredientsResponse != null) {
					ingredients = new ArrayList<>();
					for (IngredientResponse ingredientResponse : ingredientsResponse)
						ingredients.add(Ingredient.builder()
									.id(ingredientResponse.getId())
									.name(ingredientResponse.getName())
									.price(ingredientResponse.getPrice())
									.build());
				}				
				burgers.add(Burger.builder()
						.id(burgerResponse.getId())
						.name(burgerResponse.getName())
						.ingredients(ingredients)
						.build());
			}
		}
		
        return Order.builder()
        		.burgers(burgers)
        		.build();
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
