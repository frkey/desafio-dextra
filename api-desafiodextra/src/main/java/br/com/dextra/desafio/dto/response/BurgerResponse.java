package br.com.dextra.desafio.dto.response;

import java.util.List;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "BurgerResponse")
public class BurgerResponse {
	
	/**
	 * Internal Burger ID, uniquely identifying this burger in the menu.
	 * @return id
	 **/
	@ApiModelProperty(example = "5af71925cca98113e0647044", value = "Internal Burger ID, uniquely identifying this burger in the menu.")
	@JsonProperty("id")
    private String id;
	
	/**
	 * Burger name.
	 * @return name
	 **/
	@ApiModelProperty(example = "X-Burger", value = "Burger name.")
	@JsonProperty("name")
	private String name;

	/**
	 * List with the ingredients.
	 * @return ingredients
	 **/
	@ApiModelProperty(value = "List with the ingredients.")
	@NotNull
	private List<IngredientResponse> ingredients;

}
