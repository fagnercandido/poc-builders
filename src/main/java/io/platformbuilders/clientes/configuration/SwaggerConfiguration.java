package io.platformbuilders.clientes.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Value("${swagger.api.version}")
	private String apiVersion;

	@Value("${swagger.release.version}")
	private String releaseVersion;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("io.platformbuilders.clientes.boundary"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API Clientes")
				.description("Clientes - Documentação da API de Cliente.")
				.version(releaseVersion.concat("_").concat(apiVersion))
				.contact(new Contact("Fagner Candido", "fagnercandido.com", "fsouzacandido@gmail.com"))
				.build();
	}

}
