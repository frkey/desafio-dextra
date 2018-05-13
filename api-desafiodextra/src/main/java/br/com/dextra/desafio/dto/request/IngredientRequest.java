package br.com.dextra.desafio.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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
@ApiModel(value = "IngredientRequest")
public class IngredientRequest {
	
	/**
	 * Ingredient name.
	 * @return name
	 **/
	@ApiModelProperty(example = "Bacon", value = "Ingredient name.")
	@JsonProperty("name")
	@NotBlank
	private String name;

    
	/**
	 * Ingredient price.
	 * @return name
	 **/
	@ApiModelProperty(value = "Ingredient price.")
	@JsonProperty("price")
	@NotNull
    private BigDecimal price;

}
