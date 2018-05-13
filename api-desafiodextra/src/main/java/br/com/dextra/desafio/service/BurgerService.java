package br.com.dextra.desafio.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.dto.converter.BurgerToBurgerResponse;
import br.com.dextra.desafio.dto.request.BurgerPartialRequest;
import br.com.dextra.desafio.dto.request.BurgerRequest;
import br.com.dextra.desafio.dto.response.BurgerResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.repository.BurgerRepository;

@Service
public class BurgerService {

	final BurgerRepository repository;

	final IngredientService ingredientService;

	final BurgerToBurgerResponse burgerToResponse;

	@Inject
	public BurgerService(final BurgerRepository repository,
			final IngredientService ingredientService,
			final BurgerToBurgerResponse burgerToResponse) {
		this.repository = repository;
		this.ingredientService = ingredientService;
		this.burgerToResponse = burgerToResponse;
	}

	public List<BurgerResponse> fetchAll() {
        return burgerToResponse.convert(repository.findAll());
    }

	public BurgerResponse save(final BurgerRequest burgerRequest) {
        final Burger savedBurger = repository.save(buildFromRequest(burgerRequest));
        return burgerToResponse.convert(savedBurger);
    }

	public BurgerResponse update(final BurgerRequest burgerRequest, final String id) throws NotFoundException {
        final BurgerResponse burgerResponse = fetch(id);

        Burger burger = buildFromRequest(burgerRequest);
        burger.setId(burgerResponse.getId());

        return burgerToResponse.convert(repository.save(burger));
    }

    public BurgerResponse partialUpdate(final BurgerPartialRequest burgerRequest, final String id) throws NotFoundException {
        final BurgerResponse burgerResponse = fetch(id);

        Burger.BurgerBuilder builder = Burger.builder()
                .id(burgerResponse.getId());

        if (!StringUtils.isEmpty(burgerRequest.getName()))
            builder.name(burgerRequest.getName());
        else
            builder.name(burgerResponse.getName());
        
        final List<String> ingredientsId = burgerRequest.getIngredientsId();
        
        if (ingredientsId != null) {
        	builder.ingredients(ingredientService.fetchAll(ingredientsId));
        }

        Burger burgerToUpdate = builder.build();
        repository.save(burgerToUpdate);

        return burgerToResponse.convert(burgerToUpdate);
    }

    public BurgerResponse fetch(final String id) throws NotFoundException {
        final Burger burger = repository.findOne(id);
        if (burger == null) {
            throw new NotFoundException("Burger not found");
        }
        return burgerToResponse.convert(burger);
    }

    public void delete(final String id) throws NotFoundException {
        fetch(id);
        repository.delete(id);
    }	

	public Burger buildFromRequest(final BurgerRequest burgerRequest) {
		Burger.BurgerBuilder builder = Burger.builder().name(burgerRequest.getName());
		
		final List<String> ids = burgerRequest.getIngredientsId();
		
		if (ids != null)
			builder.ingredients(ingredientService.fetchAll(ids));
		else
			builder.ingredients(null);
		
		return builder.build();		
	}

}
