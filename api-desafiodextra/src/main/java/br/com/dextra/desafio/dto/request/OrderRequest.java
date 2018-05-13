package br.com.dextra.desafio.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.dextra.desafio.dto.response.BurgerResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"uuid"})
@ApiModel(value = "OrderRequest")
public class OrderRequest {
	
	/**
	 * List with the burgers.
	 * @return burgers
	 **/
	@ApiModelProperty(value = "List with the burgers.")
	@NotNull
	private List<BurgerResponse> burgers;

}
