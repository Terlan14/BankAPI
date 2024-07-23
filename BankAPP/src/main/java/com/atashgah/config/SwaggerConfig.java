package com.atashgah.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {
	@Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("custom-group")
                .pathsToMatch("/api/**")  // Adjust the path pattern to match your API paths
                .packagesToScan("com.atashgah.controller","com.atashgah.auth")  // Change to your package name
                .build();
    }

}
