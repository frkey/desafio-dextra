package br.com.dextra.desafio.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import br.com.dextra.desafio.dto.request.IngredientRequest;
import br.com.dextra.desafio.dto.response.IngredientResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.service.IngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Operations available to ingredients")
public class IngredientController {
	
	public static final String INGREDIENTS_URI = "/ingredients";
	
	final private IngredientService ingredientService;
	
	@Inject
	public IngredientController(final IngredientService ingredientService) {
		this.ingredientService = ingredientService;		
	}

	@ApiOperation(value = "List all ingredients",
			httpMethod = "GET", nickname = "listIngredients", response = IngredientResponse.class, responseContainer = "List", tags = "ingredients")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of ingredients", response = IngredientResponse.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Error in request parameters"),
			@ApiResponse(code = 422, message = "Business error"),
			@ApiResponse(code = 500, message = "Internal system error")})
	@RequestMapping(value = INGREDIENTS_URI, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<IngredientResponse>> fetchIngredients() {
		return ResponseEntity.ok(ingredientService.fetchAll());
	}
	
	@ApiOperation(value = "Creates new ingredient",
			httpMethod = "POST", nickname = "createIngredient", tags = "ingredients")
	@ApiResponses(value = {
	    	@ApiResponse(code = 201, message = "Item created"),
	    	@ApiResponse(code = 400, message = "Invalid input, object invalid"),
	    	@ApiResponse(code = 500, message = "Internal system error")})
	@RequestMapping(value = INGREDIENTS_URI, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON,
	produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Void> create(
			@ApiParam(value = "Ingredient to create") @Valid @RequestBody IngredientRequest ingredient) {
		IngredientResponse saved = ingredientService.save(ingredient);

	    UriComponents resourceUri = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/{ingredientId}")
	            .buildAndExpand(saved.getId());

	    return ResponseEntity.created(resourceUri.toUri()).build();
	}	
	
	@ApiOperation(value = "List all data about a ingredient",
			httpMethod = "GET", nickname = "getIngredient", response = IngredientResponse.class, tags = "ingredients")
	@ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "A single ingredient", response = IngredientResponse.class),
	        @ApiResponse(code = 404, message = "Ingredient with this ID not found") })
	@RequestMapping(value = INGREDIENTS_URI + "/{ingredientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<IngredientResponse> fetchIngredient(
			@ApiParam(value = "ID of ingredient that needs to be fetched", required=true) @PathVariable("ingredientId") String id) throws NotFoundException {
	    return ResponseEntity.ok(ingredientService.fetch(id));
	}
	
	@ApiOperation(value = "Deletes a ingredient", httpMethod = "DELETE", nickname = "deleteIngredient", tags = "ingredients")
    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Item deleted"),
	        @ApiResponse(code = 404, message = "The ingredient is not found") })	
	@RequestMapping(value = INGREDIENTS_URI + "/{ingredientId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteIngredient(
    		@ApiParam(value = "ID of ingredient that needs to be deleted", required=true) @PathVariable("ingredientId") String id) throws NotFoundException {
		ingredientService.delete(id);
	    return ResponseEntity.ok().build();
	}

}
