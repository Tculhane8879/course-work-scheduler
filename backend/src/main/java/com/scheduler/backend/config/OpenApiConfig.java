package com.scheduler.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI courseWorkSchedulerOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Course + Work Schedule Optimizer API")
                        .description("REST API for managing courses, sections, availability blocks, schedule preferences, generated schedules, and saved schedule snapshots.")
                        .version("0.0.1")
                        .license(new License().name("Portfolio project")))
                .servers(List.of(new Server()
                        .url("/")
                        .description("Current host")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project repository")
                        .url("https://github.com/Tculhane8879/course-work-scheduler"));
    }

    @Bean
    public GroupedOpenApi schedulerApiGroup() {
        return GroupedOpenApi.builder()
                .group("scheduler-api")
                .pathsToMatch("/api/**")
                .build();
    }
}
