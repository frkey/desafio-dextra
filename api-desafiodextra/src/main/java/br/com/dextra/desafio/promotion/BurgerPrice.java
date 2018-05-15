package br.com.dextra.desafio.promotion;

import java.math.BigDecimal;
import java.util.List;

import br.com.dextra.desafio.domain.Burger;

public abstract class BurgerPrice {

	protected BurgerPrice successor;
	
	public void setSuccessor(BurgerPrice successor) {
		this.successor = successor;
    }
	
	public abstract List<BigDecimal> getPrice(final Burger burger);

}
