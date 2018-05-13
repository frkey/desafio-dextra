package br.com.dextra.desafio.dto.request;


import java.util.List;

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
@ApiModel(value = "BurgerRequest")
public class BurgerRequest {

	/**
	 * Burger name.
	 * @return name
	 **/
	@ApiModelProperty(example = "X-Burger", value = "Burger name.")
	@JsonProperty("name")
	@NotBlank
	private String name;

	/**
	 * List with the ingredients IDs.
	 * @return ingredientsId
	 **/
	@ApiModelProperty(value = "List with the ingredients IDs.")
	@NotNull
	private List<String> ingredientsId;

}
