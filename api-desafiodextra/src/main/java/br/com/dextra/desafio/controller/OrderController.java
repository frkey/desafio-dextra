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

import br.com.dextra.desafio.dto.request.OrderRequest;
import br.com.dextra.desafio.dto.response.OrderResponse;
import br.com.dextra.desafio.exception.BadRequestAPIException;
import br.com.dextra.desafio.exception.NotFoundException;
import br.com.dextra.desafio.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Operations available to orders")
public class OrderController {

	public static final String ORDERS_URI = "/orders";
	
	final private OrderService orderService;

	@Inject
	public OrderController(final OrderService orderService) {
		this.orderService = orderService;
	}

	@ApiOperation(value = "List all orders",
			httpMethod = "GET", nickname = "listOrders", response = OrderResponse.class, responseContainer = "List", tags = "orders")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of orders", response = OrderResponse.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Error in request parameters"),
			@ApiResponse(code = 422, message = "Business error"),
			@ApiResponse(code = 500, message = "Internal system error")})
	@RequestMapping(value = ORDERS_URI, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<OrderResponse>> fetchOrders() {
		return ResponseEntity.ok(orderService.fetchAll());
	}
	
	@ApiOperation(value = "Creates new order",
			httpMethod = "POST", nickname = "createOrder", tags = "orders")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Item created"),
			@ApiResponse(code = 400, message = "Invalid input, object invalid"),
			@ApiResponse(code = 404, message = "Burger ID not found"),
			@ApiResponse(code = 500, message = "Internal system error")})
	@RequestMapping(value = ORDERS_URI, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON,
	produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Void> create(
			@ApiParam(value = "Order to create") @Valid @RequestBody OrderRequest order) throws NotFoundException, BadRequestAPIException {
		OrderResponse saved = orderService.save(order);

	    UriComponents resourceUri = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/{orderId}")
	            .buildAndExpand(saved.getId());

	    return ResponseEntity.created(resourceUri.toUri()).build();
	}

	@ApiOperation(value = "List all data about a order",
			httpMethod = "GET", nickname = "getOrder", response = OrderResponse.class, tags = "orders")
	@ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A single order", response = OrderResponse.class),
        @ApiResponse(code = 404, message = "Order with this ID not found") })
	@RequestMapping(value = ORDERS_URI + "/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<OrderResponse> fetchOrder(
			@ApiParam(value = "ID of order that needs to be fetched", required=true) @PathVariable("orderId") String id) throws NotFoundException {
	    return ResponseEntity.ok(orderService.fetch(id));
	}

	@ApiOperation(value = "Replaces a order",
			httpMethod = "PUT", nickname = "replaceOrder", tags = "orders")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Item replaced"),
			@ApiResponse(code = 400, message = "Invalid input, object invalid"),
			@ApiResponse(code = 404, message = "The order (or any of supplied IDs) is not found"),
			@ApiResponse(code = 500, message = "Internal system error")})
	@RequestMapping(value = ORDERS_URI + "/{orderId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)	
    public ResponseEntity<Void> replace(
    		@ApiParam(value = "ID of order that needs to be replaced", required=true) @PathVariable("orderId") String id,
    		@ApiParam(value = "Order to replace") @Valid @RequestBody OrderRequest orderRequest) throws NotFoundException {
		orderService.update(orderRequest, id);
	    return ResponseEntity.ok().build();    	
    }

	@ApiOperation(value = "Deletes a order", httpMethod = "DELETE", nickname = "deleteIngredient", tags = "orders")
    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Item deleted"),
	        @ApiResponse(code = 404, message = "The order is not found") })	
	@RequestMapping(value = ORDERS_URI + "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteIngredient(
    		@ApiParam(value = "ID of order that needs to be deleted", required=true) @PathVariable("orderId") String id) throws NotFoundException {
		orderService.delete(id);
	    return ResponseEntity.ok().build();
	}
}
