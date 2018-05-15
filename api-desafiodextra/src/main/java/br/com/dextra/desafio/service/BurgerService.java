package br.com.dextra.desafio.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.BurgerPartialRequest;
import br.com.dextra.desafio.dto.request.BurgerRequest;
import br.com.dextra.desafio.dto.response.BurgerResponse;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.promotion.BurgerPrice;
import br.com.dextra.desafio.promotion.LightPromotion;
import br.com.dextra.desafio.promotion.LotOfPromotion;
import br.com.dextra.desafio.promotion.SimpleBurgerPrice;
import br.com.dextra.desafio.repository.BurgerRepository;

@Service
public class BurgerService {

	final BurgerRepository repository;

	final IngredientService ingredientService;

	final BurgerPrice priceWithoutDiscount;
	final BurgerPrice priceWithLightPromotion;
	final BurgerPrice priceWithLotOfMeatPromotion;
	final BurgerPrice priceWithLotOfCheesePromotion;

	@Inject
	public BurgerService(final BurgerRepository repository, final IngredientService ingredientService) {
		this.repository = repository;
		this.ingredientService = ingredientService;

		this.priceWithoutDiscount = new SimpleBurgerPrice();
		this.priceWithLightPromotion = new LightPromotion();
		this.priceWithLotOfMeatPromotion = new LotOfPromotion("carne");
		this.priceWithLotOfCheesePromotion = new LotOfPromotion("queijo");

		priceWithoutDiscount.setSuccessor(priceWithLightPromotion);
		priceWithLightPromotion.setSuccessor(priceWithLotOfMeatPromotion);
		priceWithLotOfMeatPromotion.setSuccessor(priceWithLotOfCheesePromotion);
	}

	public List<BigDecimal> getPrices(final Burger burger) {
		return priceWithoutDiscount.getPrice(burger);
	}

	public List<BurgerResponse> fetchAll() {
		return repository.findAll().stream().map(c -> buildResponse(c)).collect(Collectors.toList());
	}

	public List<Burger> fetchAll(final List<String> ids) {
		List<Burger> burgers = new ArrayList<>();
		repository.findAll(ids).forEach(burgers::add);

		return burgers;
	}

	public BurgerResponse save(final BurgerRequest burgerRequest) throws NotFoundException {
		final Burger savedBurger = repository.save(buildFromRequest(burgerRequest));
		return buildResponse(savedBurger);
	}

	public BurgerResponse update(final BurgerRequest burgerRequest, final String id) throws NotFoundException {
		final BurgerResponse burgerResponse = fetch(id);

		Burger burger = buildFromRequest(burgerRequest);
		burger.setId(burgerResponse.getId());

		return buildResponse(repository.save(burger));
	}

	public BurgerResponse partialUpdate(final BurgerPartialRequest burgerRequest, final String id)
			throws NotFoundException {
		final BurgerResponse burgerResponse = fetch(id);

		Burger.BurgerBuilder builder = Burger.builder().id(burgerResponse.getId());

		if (!StringUtils.isEmpty(burgerRequest.getName()))
			builder.name(burgerRequest.getName());
		else
			builder.name(burgerResponse.getName());

		final List<String> ingredientsId = burgerRequest.getIngredientsId();

		if (ingredientsId != null && !ingredientsId.isEmpty()) {
			builder.ingredients(ingredientService.fetchAll(ingredientsId));
		}

		Burger burgerToUpdate = builder.build();
		repository.save(burgerToUpdate);

		return buildResponse(burgerToUpdate);
	}

	public BurgerResponse fetch(final String id) throws NotFoundException {
		final Burger burger = repository.findOne(id);
		if (burger == null) {
			throw new NotFoundException("Burger not found");
		}
		return buildResponse(burger);
	}

	public void delete(final String id) throws NotFoundException {
		fetch(id);
		repository.delete(id);
	}

	public Burger buildFromRequest(final BurgerRequest burgerRequest) throws NotFoundException {
		Burger.BurgerBuilder builder = Burger.builder().name(burgerRequest.getName());

		final List<String> ids = burgerRequest.getIngredientsId();

		if (ids != null && !ids.isEmpty()) {
			final List<Ingredient> ingredients = ingredientService.fetchAll(ids);

			if (ingredients == null || ingredients.size() != ids.size())
				throw new NotFoundException("Ingredient not found");
			else
				builder.ingredients(ingredients);
		} else
			builder.ingredients(null);

		return builder.build();
	}

	private BurgerResponse buildResponse(Burger burger) {
		List<IngredientResponse> ingredientsResponse = null;
		final List<Ingredient> ingredients = burger.getIngredients();

		if (ingredients != null) {
			ingredientsResponse = new ArrayList<>();
			for (Ingredient ingredient : ingredients)
				ingredientsResponse.add(IngredientResponse.builder().id(ingredient.getId()).name(ingredient.getName())
						.price(ingredient.getPrice()).build());
		}

		return BurgerResponse.builder().id(burger.getId()).name(burger.getName()).ingredients(ingredientsResponse)
				.build();
	}

}
