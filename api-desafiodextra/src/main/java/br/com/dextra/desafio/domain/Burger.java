package br.com.dextra.desafio.domain;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="burger")
public class Burger {
	
	@Id
    private String id;
	
	private String name;
	
	private List<Ingredient> ingredients;

}
