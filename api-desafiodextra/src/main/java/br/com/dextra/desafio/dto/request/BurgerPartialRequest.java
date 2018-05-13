package br.com.dextra.desafio.dto.request;

import java.util.List;

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
@ApiModel(value = "BurgerPartialRequest")
public class BurgerPartialRequest {

	/**
	 * Burger name.
	 * @return name
	 **/
	@ApiModelProperty(example = "X-Burger", value = "Burger name.")
	@JsonProperty("name")
	private String name;

	/**
	 * List with the ingredients IDs.
	 * @return ingredientsId
	 **/
	@ApiModelProperty(value = "List with the ingredients IDs.")
	private List<String> ingredientsId;
}
