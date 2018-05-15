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

import br.com.dextra.desafio.dto.request.BurgerPartialRequest;
import br.com.dextra.desafio.dto.request.BurgerRequest;
import br.com.dextra.desafio.dto.response.BurgerResponse;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.service.BurgerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Operations available to burgers")
public class BurgerController {
	
	public static final String BURGERS_URI = "/burgers";
	
	final private BurgerService burgerService;
	
	@Inject
	public BurgerController(final BurgerService burgerService) {
		this.burgerService = burgerService;
	}
	
	@ApiOperation(value = "List all burgers",
			httpMethod = "GET", nickname = "listBurgers", response = BurgerResponse.class, responseContainer = "List", tags = "burgers")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of burgers", response = BurgerResponse.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Error in request parameters"),
			@ApiResponse(code = 422, message = "Business error"),
			@ApiResponse(code = 500, message = "Internal system error")})
	@RequestMapping(value = BURGERS_URI, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<BurgerResponse>> fetchBurgers() {
		return ResponseEntity.ok(burgerService.fetchAll());
	}

	@ApiOperation(value = "Creates new burger", httpMethod = "POST", nickname = "createBurger", tags = "burgers")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Item created"),
			@ApiResponse(code = 400, message = "Invalid input, object invalid"),
			@ApiResponse(code = 404, message = "Ingredient ID not found"),
			@ApiResponse(code = 500, message = "Internal system error") })
	@RequestMapping(value = BURGERS_URI, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Void> create(@ApiParam(value = "Burger to create") @Valid @RequestBody BurgerRequest burger)
			throws NotFoundException {
		BurgerResponse saved = burgerService.save(burger);

		UriComponents resourceUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{burgerId}")
				.buildAndExpand(saved.getId());

		return ResponseEntity.created(resourceUri.toUri()).build();
	}

	@ApiOperation(value = "List all data about a burger", httpMethod = "GET", nickname = "getBurger", response = BurgerResponse.class, tags = "burgers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A single burger", response = BurgerResponse.class),
			@ApiResponse(code = 404, message = "Burger with this ID not found") })
	@RequestMapping(value = BURGERS_URI
			+ "/{burgerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<BurgerResponse> fetchBurger(
			@ApiParam(value = "ID of burger that needs to be fetched", required = true) @PathVariable("burgerId") String id)
			throws NotFoundException {
		return ResponseEntity.ok(burgerService.fetch(id));
	}

	@ApiOperation(value = "Replaces a burger", httpMethod = "PUT", nickname = "replaceBurger", tags = "burgers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item replaced"),
			@ApiResponse(code = 400, message = "Invalid input, object invalid"),
			@ApiResponse(code = 404, message = "The burger (or any of supplied IDs) is not found"),
			@ApiResponse(code = 500, message = "Internal system error") })
	@RequestMapping(value = BURGERS_URI
			+ "/{burgerId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<Void> replace(
			@ApiParam(value = "ID of burger that needs to be replaced", required = true) @PathVariable("burgerId") String id,
			@ApiParam(value = "Burger to replace") @Valid @RequestBody BurgerRequest burgerRequest)
			throws NotFoundException {
		burgerService.update(burgerRequest, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Modifies a burger", httpMethod = "PATCH", nickname = "modifyBurger", tags = "burgers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item modified"),
			@ApiResponse(code = 400, message = "Invalid input, object invalid"),
			@ApiResponse(code = 404, message = "The burger (or any of supplied IDs) is not found"),
			@ApiResponse(code = 500, message = "Internal system error") })
	@RequestMapping(value = BURGERS_URI
			+ "/{burgerId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<Void> partialUpdate(
			@ApiParam(value = "ID of burger that needs to be modified", required = true) @PathVariable("burgerId") String id,
			@ApiParam(value = "Burger data with one or more fields filled") @Valid @RequestBody BurgerPartialRequest burgerRequest)
			throws NotFoundException {
		burgerService.partialUpdate(burgerRequest, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Deletes a burger", httpMethod = "DELETE", nickname = "deleteIngredient", tags = "burgers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item deleted"),
			@ApiResponse(code = 404, message = "The burger is not found") })
	@RequestMapping(value = BURGERS_URI + "/{burgerId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteIngredient(
			@ApiParam(value = "ID of burger that needs to be deleted", required = true) @PathVariable("burgerId") String id)
			throws NotFoundException {
		burgerService.delete(id);
		return ResponseEntity.ok().build();
	}

}
