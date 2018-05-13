package br.com.dextra.desafio.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="ingredient")
public class Ingredient {
	
	@Id
    private String id;
	
    private String name;
    
    private BigDecimal price;

}
