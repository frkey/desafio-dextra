package br.com.dextra.desafio.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.dextra.desafio.dto.converter.OrderRequestToOrder;
import br.com.dextra.desafio.dto.converter.OrderToOrderResponse;
import br.com.dextra.desafio.dto.response.OrderResponse;
import br.com.dextra.desafio.repository.OrderRepository;

@Service
public class OrderService {

	final OrderRepository repository;
	
	final OrderRequestToOrder requestToOrder;
	
	final OrderToOrderResponse orderToResponse;
	
	@Inject
	public OrderService(final OrderRepository repository,
			final OrderRequestToOrder requestToOrder,
			final OrderToOrderResponse orderToResponse) {
		this.repository = repository;
		this.requestToOrder = requestToOrder;
		this.orderToResponse = orderToResponse;
	}

	public List<OrderResponse> fetchAll() {
        return orderToResponse.convert(repository.findAll());
    }

}
