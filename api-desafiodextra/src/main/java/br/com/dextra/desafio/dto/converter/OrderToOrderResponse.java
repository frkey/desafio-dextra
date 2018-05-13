package br.com.dextra.desafio.dto.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.dextra.desafio.domain.Order;
import br.com.dextra.desafio.dto.response.OrderResponse;

@Component
public class OrderToOrderResponse implements GenericConverter<Order, OrderResponse> {

	final BurgerToBurgerResponse burgerToResponse;

	@Inject
	public OrderToOrderResponse(BurgerToBurgerResponse burgerToResponse) {
		this.burgerToResponse = burgerToResponse;
	}

	@Override
	public OrderResponse apply(Order input) {
		return OrderResponse.builder()
				.id(input.getId())
				.creationDate(input.getCreationDate())
				.burgers(burgerToResponse.convert(input.getBurgers()))
				.build();
	}

}
