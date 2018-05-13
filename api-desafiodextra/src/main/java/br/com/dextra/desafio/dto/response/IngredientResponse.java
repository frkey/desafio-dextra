package br.com.dextra.desafio.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"uuid"})
@ApiModel(value = "IngredientResponse")
public class IngredientResponse {

	/**
	 * Internal Ingredient ID, uniquely identifying this ingredient in the menu.
	 * @return id
	 **/
	@ApiModelProperty(example = "5af6f48d3be5d83d14b6119f", value = "Internal Ingredient ID, uniquely identifying this ingredient in the menu.")
	@JsonProperty("id")
    private String id;
	
	/**
	 * Ingredient name.
	 * @return name
	 **/
	@ApiModelProperty(example = "Bacon", value = "Ingredient name.")
	@JsonProperty("name")
	private String name;

    
	/**
	 * Ingredient price.
	 * @return name
	 **/
	@ApiModelProperty(value = "Ingredient price.")
	@JsonProperty("price")
    private BigDecimal price;

}
