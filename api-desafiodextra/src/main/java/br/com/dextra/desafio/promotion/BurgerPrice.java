package br.com.dextra.desafio.promotion;

import java.math.BigDecimal;

import br.com.dextra.desafio.domain.Burger;

public abstract class BurgerPrice {

	protected BurgerPrice successor;
	
	public void SetSuccessor(BurgerPrice successor) {
		this.successor = successor;
    }
	
	public abstract BigDecimal getPrice(final Burger burger);

}
