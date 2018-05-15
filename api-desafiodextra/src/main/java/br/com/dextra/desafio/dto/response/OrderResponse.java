package br.com.dextra.desafio.dto.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value = "OrderResponse")
public class OrderResponse {

	/**
	 * Internal Order ID.
	 * @return id
	 **/
	@ApiModelProperty(example = "5af71925cca98113e0647044", value = "Internal Order ID.")
	@JsonProperty("id")
    private String id;

	/**
	 * Order creation date.
	 * @return burgers
	 **/
	@ApiModelProperty(value = "Order creation date.")
	@JsonProperty("dateCreated")
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date dateCreated;

	/**
	 * List with the burgers.
	 * @return burgers
	 **/
	@ApiModelProperty(value = "List with the burgers.")
	@JsonProperty("burgers")
	private List<BurgerResponse> burgers;

}
