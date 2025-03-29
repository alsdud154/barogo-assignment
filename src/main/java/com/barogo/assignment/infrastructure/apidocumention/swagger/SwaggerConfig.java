package com.barogo.assignment.infrastructure.apidocumention.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("바로고 과제 API").version("v1").description("인증/배달 API 명세서"))
				.addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
				.components(
						new Components()
								.addSecuritySchemes(
										"BearerAuth",
										new SecurityScheme()
												.name("Authorization")
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")
												.in(SecurityScheme.In.HEADER)));
	}
}
