package br.com.dextra.desafio.controllers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dextra.desafio.controller.IngredientController;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.exception.GlobalExceptionHandler;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.service.IngredientService;
import br.com.dextra.desafio.services.IngredientsTestFactory;

@RunWith(MockitoJUnitRunner.class)
public class IngredientControllerTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IngredientControllerTest.class);

	@Mock
	private IngredientService ingredientService;

	@InjectMocks
	private IngredientController ingredientController;

	private MockMvc mvc;

	private ObjectMapper mapper;

	@Before
	public void init() {
		mvc = MockMvcBuilders.standaloneSetup(ingredientController)
				.setControllerAdvice(new GlobalExceptionHandler(Mockito.mock(MessageSource.class))).build();
		mapper = new ObjectMapper();
	}

	/* FETCH ALL */

	@Test
	public void testFetchAll() throws Exception {
		IngredientResponse ingredientResponse = IngredientsTestFactory.defaultIngredientResponse();
		
		when(ingredientService.fetchAll()).thenReturn(Arrays.asList(ingredientResponse));

		mvc.perform(MockMvcRequestBuilders.get(IngredientController.INGREDIENTS_URI))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", equalTo("Ovo")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", equalTo(0.80)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testFetchAllWhenResultIsEmpty() throws Exception {
		when(ingredientService.fetchAll()).thenReturn(new ArrayList<>());

		mvc.perform(MockMvcRequestBuilders.get(IngredientController.INGREDIENTS_URI))
				.andExpect(MockMvcResultMatchers.jsonPath("$", empty()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/* SAVE */

	@Test
	public void testSave() throws Exception {
		IngredientResponse ingredientResponse = IngredientsTestFactory.defaultIngredientResponse();
		
		when(ingredientService.save(IngredientsTestFactory.defaultIngredientRequest()))
				.thenReturn(ingredientResponse);

		mvc.perform(MockMvcRequestBuilders.post(IngredientController.INGREDIENTS_URI).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(IngredientsTestFactory.defaultIngredientRequest()))).andDo(res -> {
					String location = res.getResponse().getHeader("location");
					LOGGER.info("Location: {}", location);
					Assert.assertNotNull(location);
				});

	}

	@Test
	public void testSaveWithSomeError() throws Exception {
		when(ingredientService.save(IngredientsTestFactory.defaultIngredientRequest()))
				.thenThrow(new RuntimeException("Any error"));

		mvc.perform(MockMvcRequestBuilders.post(IngredientController.INGREDIENTS_URI).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(IngredientsTestFactory.defaultIngredientRequest())))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}

	/* FETCH BY ID */

	@Test
	public void testFetch() throws Exception {
		String id = "1";
		IngredientResponse ingredientResponse = IngredientsTestFactory.defaultIngredientResponse();
		when(ingredientService.fetch(id)).thenReturn(ingredientResponse);

		mvc.perform(MockMvcRequestBuilders.get(IngredientController.INGREDIENTS_URI + "/{ingredientId}", id))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(id)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Ovo")))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testFetchWhenNotFoundIngredient() throws Exception {
		String id = "1";
		when(ingredientService.fetch(id)).thenThrow(new NotFoundException("Burger with this ID not found"));

		mvc.perform(MockMvcRequestBuilders.get(IngredientController.INGREDIENTS_URI + "/{ingredientId}", id))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testDelete() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(IngredientController.INGREDIENTS_URI + "/{ingredientId}", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
