package br.com.dextra.desafio.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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

import br.com.dextra.desafio.domain.Ingredient;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.repository.IngredientRepository;
import br.com.dextra.desafio.service.IngredientService;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceTest {

	@Mock
    private IngredientRepository repository;

    @InjectMocks
    private IngredientService service;

    @Test
    public void fetchAll() {

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(IngredientsTestFactory.defaultIngredient()));

        List<IngredientResponse> ingredients = service.fetchAll();

        assertFalse("Ingredients could not be empty", ingredients.isEmpty());
        assertEquals(ingredients.get(0).getName(), "Ovo");

    }

    @Test
    public void fetchAllWhitEmptyResult() {

        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<IngredientResponse> ingredients = service.fetchAll();

        assertTrue("Ingredients should be empty", ingredients.isEmpty());

    }

    @Test
    public void testSave() {

        Ingredient ingredient = IngredientsTestFactory.defaultIngredient();

        Mockito.when(repository.save(any(Ingredient.class))).thenReturn(ingredient);

        IngredientResponse response = service.save(IngredientsTestFactory.defaultIngredientRequest());

        assertEquals(ingredient.getId(), response.getId());
        assertEquals(ingredient.getName(), response.getName());
        assertEquals(ingredient.getPrice(), response.getPrice());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWhenIngredientAlreadyExists() {

        Mockito.when(repository.save(any(Ingredient.class))).thenThrow(new DataIntegrityViolationException("duplicated key"));

        service.save(IngredientsTestFactory.defaultIngredientRequest());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWhenNotFoundIngredient() throws NotFoundException {

        Mockito.when(repository.findOne(anyString())).thenReturn(null);

        service.delete("1");
    }

}
