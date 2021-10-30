package com.feyon.codeset.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * home: /swagger-ui.html
 *
 * @author Feng Yong
 */
@Configuration
public class SpringDocConfig {


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("code-set-public")
                .pathsToMatch("/api/**")
                .packagesToScan("com.feyon.codeset.controller.api")
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("Code Set API")
                        .description("code set application that like LeetCode")
                        .version("v1.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
