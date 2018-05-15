package br.com.dextra.desafio.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="order")
public class Order {

	@Id
    private String id;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date dateCreated;
	
	private List<Burger> burgers;

}
