package br.com.dextra.desafio.config;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.dextra.desafio"))
				.paths(PathSelectors.any()).build().directModelSubstitute(DateTime.class, String.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Desafio Dextra API",
				"Desafio API",
				"1.0.0",
				"",
				new Contact("Felipe Carvalho", "", "frkey@outlook.com"),
				"MIT",
				"https://github.com/frkey/desafio-dextra/blob/master/LICENSE");
	}
}
