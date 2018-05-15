package br.com.dextra.desafio.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.dextra.desafio.domain.Burger;
import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.request.BurgerPartialRequest;
import br.com.dextra.desafio.dto.request.BurgerRequest;
import br.com.dextra.desafio.dto.response.BurgerResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.repository.BurgerRepository;
import br.com.dextra.desafio.service.BurgerService;
import br.com.dextra.desafio.service.IngredientService;

@RunWith(MockitoJUnitRunner.class)
public class BurgerServiceTest {

	@Mock
    private IngredientService ingredientService;

	@Mock
    private BurgerRepository repository;

    @InjectMocks
    private BurgerService service;	

    @Test
    public void fetchAll() {

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(BurgersTestFactory.defaultBurger()));

        List<BurgerResponse> burgers = service.fetchAll();

        assertFalse("Burgers could not be empty", burgers.isEmpty());
        assertEquals(burgers.get(0).getName(), "X-Bacon");

    }

    @Test
    public void fetchAllWhitEmptyResult() {

        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<BurgerResponse> burgers = service.fetchAll();

        assertTrue("Burgers should be empty", burgers.isEmpty());

    }

    @Test
    public void testSave() throws NotFoundException {
    	
    	Burger burger = BurgersTestFactory.defaultBurger();
    	List<Ingredient> ingredients = burger.getIngredients();

    	Mockito.when(ingredientService.fetchAll(anyListOf(String.class))).thenReturn(ingredients);
        Mockito.when(repository.save(any(Burger.class))).thenReturn(burger);

        BurgerResponse response = service.save(BurgersTestFactory.defaultBurgerRequest());

        assertEquals(burger.getId(), response.getId());
        assertEquals(burger.getName(), response.getName());
        assertEquals(burger.getIngredients().get(0).getName(), response.getIngredients().get(0).getName());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWhenBurgerAlreadyExists() throws NotFoundException {

    	Mockito.when(ingredientService.fetchAll(anyListOf(String.class))).thenReturn(BurgersTestFactory.defaultBurger().getIngredients());
        Mockito.when(repository.save(any(Burger.class))).thenThrow(new DataIntegrityViolationException("duplicated key"));

        service.save(BurgersTestFactory.defaultBurgerRequest());
    }

    @Test
    public void testPartialUpdate() throws NotFoundException {
    	
    	Burger burger = BurgersTestFactory.defaultBurger();

        Mockito.when(repository.findOne(anyString())).thenReturn(burger);

        BurgerPartialRequest partialRequest = BurgerPartialRequest.builder()
                .name("X-Egg")
                .build();
        BurgerResponse response = service.partialUpdate(partialRequest, burger.getId());

        assertNotEquals(burger.getName(), response.getName());
        assertNull(response.getIngredients());


    }

    @Test(expected = NotFoundException.class)
    public void testPartialUpdateWhenNotFoundBurger() throws NotFoundException {

        BurgerPartialRequest partialRequest = BurgerPartialRequest.builder()
                .name("X-Egg")
                .build();

        Mockito.when(repository.findOne(anyString())).thenReturn(null);

        service.partialUpdate(partialRequest, "2");

    }

    @Test
    public void testUpdate() throws NotFoundException {

        Burger savedBurger = Burger.builder()
            .name("X-Burger")
            .build();

        Mockito.when(repository.findOne(anyString())).thenReturn(BurgersTestFactory.defaultBurger());
        Mockito.when(repository.save(any(Burger.class))).thenReturn(savedBurger);

        BurgerRequest request = BurgerRequest.builder()
                .name("X-Burger")
                .build();
        BurgerResponse response = service.update(request, "2");

        assertNull(response.getIngredients());
        assertEquals(request.getName(), response.getName());

    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWhenNotFoundBurger() throws NotFoundException {

        BurgerRequest request = BurgerRequest.builder()
                .name("X-Burger")
                .build();

        Mockito.when(repository.findOne(anyString())).thenReturn(null);

        service.update(request, "2");

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWhenNotFoundBurger() throws NotFoundException {

        Mockito.when(repository.findOne(anyString())).thenReturn(null);

        service.delete("2");

    }

}
