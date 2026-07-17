package com.margaretnjoki.expense_tracker_api.config;

import io.swagger.v3.oas.models.info.Info;import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Expense Tracker API")
                .version("v1")
                .description("Track expenses by category,with monthly and per-category reports."));

    }
}
