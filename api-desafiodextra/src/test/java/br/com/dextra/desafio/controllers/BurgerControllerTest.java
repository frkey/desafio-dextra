package br.com.dextra.desafio.controllers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
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

import br.com.dextra.desafio.controller.BurgerController;
import br.com.dextra.desafio.dto.request.BurgerPartialRequest;
import br.com.dextra.desafio.dto.request.BurgerRequest;
import br.com.dextra.desafio.exception.GlobalExceptionHandler;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.service.BurgerService;
import br.com.dextra.desafio.services.BurgersTestFactory;

@RunWith(MockitoJUnitRunner.class)
public class BurgerControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BurgerControllerTest.class);

    @Mock
    private BurgerService burgerService;

    @InjectMocks
    private BurgerController burgerController;

    private MockMvc mvc;

    private ObjectMapper mapper;

    @Before
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(burgerController)
                .setControllerAdvice(new GlobalExceptionHandler(Mockito.mock(MessageSource.class)))
                .build();
        mapper = new ObjectMapper();
    }

    /* FETCH ALL */

    @Test
    public void testFetchAll() throws Exception {
        when(burgerService.fetchAll()).thenReturn(Arrays.asList(BurgersTestFactory.defaultBurgerResponse()));

        mvc.perform(MockMvcRequestBuilders
                .get(BurgerController.BURGERS_URI))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", equalTo("X-Bacon")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", equalTo("2")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFetchAllWhenResultIsEmpty() throws Exception {
        when(burgerService.fetchAll()).thenReturn(new ArrayList<>());

        mvc.perform(MockMvcRequestBuilders
                .get(BurgerController.BURGERS_URI))
                .andExpect(MockMvcResultMatchers.jsonPath("$", empty()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /* SAVE */

    @Test
    public void testSave() throws Exception {
        when(burgerService.save(BurgersTestFactory.defaultBurgerRequest()))
                .thenReturn(BurgersTestFactory.defaultBurgerResponse());

        mvc.perform(MockMvcRequestBuilders
                .post(BurgerController.BURGERS_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(BurgersTestFactory.defaultBurgerRequest()))
                )
                .andDo(res -> {
                    String location = res.getResponse().getHeader("location");
                    LOGGER.info("Location: {}", location);
                    Assert.assertNotNull(location);
                });
    }

    @Test
    public void testSaveWithSomeError() throws Exception {
        when(burgerService.save(BurgersTestFactory.defaultBurgerRequest()))
                .thenThrow(new RuntimeException("Any error"));

        mvc.perform(MockMvcRequestBuilders
                .post(BurgerController.BURGERS_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(BurgersTestFactory.defaultBurgerRequest()))
                ).andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void testSaveWithEmptyNameField() throws Exception {
        BurgerRequest request = BurgersTestFactory.defaultBurgerRequest();
        request.setName("");

        mvc.perform(MockMvcRequestBuilders
                .post(BurgerController.BURGERS_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /* FETCH BY ID */

    @Test
    public void testFetch() throws Exception {
        String id = "2";
        when(burgerService.fetch(id)).thenReturn(BurgersTestFactory.defaultBurgerResponse());

        mvc.perform(MockMvcRequestBuilders
                .get(BurgerController.BURGERS_URI + "/{burgerId}", id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("X-Bacon")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFetchWhenNotFoundBurger() throws Exception {
        String id = "2";
        when(burgerService.fetch(id)).thenThrow(new NotFoundException("Burger not found"));

        mvc.perform(MockMvcRequestBuilders
                .get(BurgerController.BURGERS_URI + "/{burgerId}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /* UPDATE */

    @Test
    public void testUpdate() throws Exception {
        String id = "2";

        BurgerRequest request = BurgersTestFactory.defaultBurgerRequest();

        mvc.perform(MockMvcRequestBuilders.put(BurgerController.BURGERS_URI + "/{burgerId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
                )
                .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateWithInvalidEmptyNameField() throws Exception {
        String id = "2";
        BurgerRequest request = BurgersTestFactory.defaultBurgerRequest();
        request.setName("");

        mvc.perform(MockMvcRequestBuilders.put(BurgerController.BURGERS_URI + "/{burgerId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateWhenNotFoundBurger() throws Exception {
        String id = "2";
        doThrow(new NotFoundException("Burger not found"))
                .when(burgerService).update(Matchers.any(BurgerRequest.class), Matchers.eq(id));

        mvc.perform(MockMvcRequestBuilders
                .put(BurgerController.BURGERS_URI + "/{burgerId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(BurgersTestFactory.defaultBurgerRequest()))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /* PATCH */

    @Test
    public void testPartialModification() throws Exception {
        String id = "2";
        BurgerPartialRequest request = BurgerPartialRequest.builder()
                .name("New Name")
                .build();

        mvc.perform(MockMvcRequestBuilders.patch(BurgerController.BURGERS_URI + "/{burgerId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
                )
                .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPartialUpdateWhenNotFoundBurger() throws Exception {
        String id = "2";
        doThrow(new NotFoundException("Burger not found"))
                .when(burgerService).partialUpdate(Matchers.any(BurgerPartialRequest.class), Matchers.eq(id));

        mvc.perform(MockMvcRequestBuilders
                .patch(BurgerController.BURGERS_URI + "/{burgerId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(BurgersTestFactory.defaultBurgerRequest()))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete(BurgerController.BURGERS_URI + "/{burgerId}", "111"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
