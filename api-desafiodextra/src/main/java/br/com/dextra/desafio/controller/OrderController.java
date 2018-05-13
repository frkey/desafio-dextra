package br.com.dextra.desafio.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dextra.desafio.dto.response.OrderResponse;
import br.com.dextra.desafio.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
}
